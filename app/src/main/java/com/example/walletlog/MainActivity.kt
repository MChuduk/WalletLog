package com.example.walletlog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletlog.dialogs.AddSpendingDialog
import com.example.walletlog.dialogs.BudgetManageDialog
import com.example.walletlog.services.CurrencyService
import com.example.walletlog.services.SignInService
import com.example.walletlog.services.SpendingService
import com.example.walletlog.services.UserService
import com.google.android.material.circularreveal.cardview.CircularRevealCardView

class MainActivity : AppCompatActivity() {

    private lateinit var authorizedUser : User;

    private lateinit var calendarView : CalendarView;
    private lateinit var recyclerView: RecyclerView;

    private lateinit var spendingDatePicker : SpendingDatePicker;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews();
        initComponents();
        setAuthorizedUser();
        calculateBudget();

        val viewModel = ViewModelProvider(this).get(CurrencyService::class.java);
        viewModel.getCurrency();
        viewModel.currencyList.observe(this, {list ->
            Log.d("we: ", list.body()?.size.toString());
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_item_add_spending -> addSpending();
            //R.id.menu_item_settings -> showSettingsActivity();
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
        return authorizedUser.budget.toString();
    }





    fun showUserSpendingAtDate() {
        val date = spendingDatePicker.getSelectedDate();
        val spendingList = SpendingService.getUserSpending(this, authorizedUser!!.id, date);
        showUserSpending(spendingList);
    }

    fun showUserSpending(spending : MutableList<Spending>) {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = SpendingAdapter(spending)
    }

    fun addSpending(value : Int, note : String) {
        val date = spendingDatePicker.getSelectedDate();
        val id = authorizedUser!!.id;

        val spending = Spending("", id, date, value, note, 0);

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

    private fun setAuthorizedUser() {
        if(intent.hasExtra("User"))
            authorizedUser = intent.extras?.getSerializable("User") as User;
    }

    private fun initComponents() {
        spendingDatePicker = SpendingDatePicker(calendarView, this);
    }

    private fun findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        calendarView = findViewById(R.id.calendarView);
    }
}