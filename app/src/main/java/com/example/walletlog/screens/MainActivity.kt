package com.example.walletlog.screens

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.walletlog.*
import com.example.walletlog.dialogs.AddSpendingDialog
import com.example.walletlog.dialogs.BudgetManageDialog
import com.example.walletlog.model.Spending
import com.example.walletlog.model.User
import com.example.walletlog.services.CurrencyService
import com.example.walletlog.services.SignInService
import com.example.walletlog.services.SpendingService
import com.example.walletlog.services.UserService
import com.google.android.material.tabs.TabLayout
import okhttp3.internal.notify

class MainActivity : AppCompatActivity() {

    private lateinit var authorizedUser : User;

    private lateinit var tabLayout : TabLayout;
    private lateinit var viewPager : ViewPager2;
    private lateinit var calendarView : CalendarView;

    private lateinit var spendingDatePicker : SpendingDatePicker;
    private lateinit var currencyService : CurrencyService;

    lateinit var spendingAdapter : SpendingAdapter;
    lateinit var spendingListInView : MutableList<Spending>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews();
        initComponents();
        initCurrencyService();
        setAuthorizedUser();

        spendingAdapter = SpendingAdapter(authorizedUser, spendingListInView);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_item_add_spending -> addSpending();
            R.id.menu_item_settings -> showSettingsActivity();
            R.id.menu_item_exit -> exit();
            R.id.manage_budget_action -> showBudgetManageDialog();
        }
        return super.onOptionsItemSelected(item);
    }



    fun getActualBudget() : Float = authorizedUser.budget;

    fun changeBudget(amount : Float) {
        authorizedUser.budget = amount;
        UserService.changeBudget(this, authorizedUser.id, amount);
        updateBudgetDisplay();
    }

    private fun calculateBudget() {
        val totalAmount = SpendingService.commitUserSpending(this, authorizedUser.id);
        val budget = authorizedUser.budget - totalAmount;
        changeBudget(budget);
    }

    private fun showBudgetManageDialog() {
        val dialog = BudgetManageDialog(this);
        dialog.show(supportFragmentManager, "BudgetManageDialog");
    }

    private fun updateBudgetDisplay() {
        val budget = getBudgetFormatString();
        supportActionBar?.title = "Бюджет: $budget";
    }

    private fun getBudgetFormatString() : String {
        val currency = UserService.getUser(this, authorizedUser.login)?.currency;
        val currencyMultiplier = CurrencyService.getCurrencyMultiplier(currency!!);
        val budget = authorizedUser.budget / currencyMultiplier;
        return "${budget.format(1)} $currency";
    }

    private fun changeCurrency(currency: String) {
        UserService.changeCurrency(this, authorizedUser.id, currency);
        updateBudgetDisplay();
    }




    fun showUserSpendingAtDate() {
        val date = spendingDatePicker.getSelectedDate();
        spendingListInView = SpendingService.getUserSpending(this, authorizedUser.id, date);
        spendingAdapter.updateAdapter(spendingListInView);
    }

/*    fun showUserSpending(spending : MutableList<Spending>) {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = SpendingAdapter(authorizedUser, spending)
    }*/

    fun addSpending(value : Int, note : String, category : String) {
        val date = spendingDatePicker.getSelectedDate();
        val id = authorizedUser.id;

        val spending = Spending("", id, date, value, note, category, 0);

        SpendingService.addSpending(this, spending);
        showUserSpendingAtDate();
        calculateBudget();
    }

    fun onSpendingDeleteButtonClick(view : View) {
        val id = view.tag.toString();

        SpendingService.deleteSpending(this, id);
        showUserSpendingAtDate();
    }

    private fun addSpending() {
        val dialog = AddSpendingDialog(this);
        dialog.show(supportFragmentManager, "AddSpending");
    }

    private fun exit() {
        SignInService.logoutUser(this);

        val intent = Intent(this, SignIn::class.java);
        startActivity(intent);
        finish();
    }

    private fun showSettingsActivity() {
        val intent = Intent(this, Settings::class.java);
        val bundle = Bundle();
        bundle.putSerializable("User", authorizedUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private fun setAuthorizedUser() {
        if(intent.hasExtra("User"))
            authorizedUser = intent.extras?.getSerializable("User") as User;
    }

    private fun initCurrencyService() {
        currencyService = ViewModelProvider(this).get(CurrencyService::class.java);
        currencyService.getCurrency();
        currencyService.currencyList.observe(this, { list ->
            val currency = list.body()!![0]

            CurrencyService.currencyDictionary["BYN"] = 1.0f;
            CurrencyService.currencyDictionary["USD"] = currency.USD_out.toFloat();
            CurrencyService.currencyDictionary["CAD"] = currency.CAD_out.toFloat();
            CurrencyService.currencyDictionary["CHF"] = currency.CHF_out.toFloat();
            CurrencyService.currencyDictionary["CZK"] = currency.CZK_out.toFloat();
            CurrencyService.currencyDictionary["EUR"] = currency.EUR_out.toFloat();
            CurrencyService.currencyDictionary["GBP"] = currency.GBP_out.toFloat();
            CurrencyService.currencyDictionary["JPY"] = currency.JPY_out.toFloat();
            CurrencyService.currencyDictionary["NOK"] = currency.NOK_out.toFloat();
            CurrencyService.currencyDictionary["PLN"] = currency.PLN_out.toFloat();
            CurrencyService.currencyDictionary["SEK"] = currency.SEK_out.toFloat();
            CurrencyService.currencyDictionary["UAH"] = currency.UAH_out.toFloat();

            changeCurrency(authorizedUser.currency);
            calculateBudget();
        })
    }

    private fun initComponents() {
        spendingListInView = arrayListOf();
        spendingDatePicker = SpendingDatePicker(calendarView, this);
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


/*        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        spendingAdapter = SpendingAdapter(authorizedUser, spendingListInView);
        recyclerView.adapter = spendingAdapter;*/

        val viewPageAdapter = ViewPageAdapter(supportFragmentManager, lifecycle, this);
        viewPager.adapter = viewPageAdapter;

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager.currentItem = tab.position;
                };
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }
        });
    }

    private fun findViews() {
        tabLayout = findViewById(R.id.TabLayout);
        //recyclerView = findViewById(R.id.recyclerView);
        calendarView = findViewById(R.id.calendarView);
        viewPager = findViewById(R.id.viewPager);
    }
}