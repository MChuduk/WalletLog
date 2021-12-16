package com.example.walletlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import com.example.walletlog.dialogs.FundBankAccountDialog
import com.example.walletlog.services.SignInService
import com.example.walletlog.services.UserService
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var user : User;

    private lateinit var calendarView : CalendarView;
    private lateinit var spendingDatePicker : SpendingDatePicker;

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews();

        spendingDatePicker = SpendingDatePicker(calendarView);

        if(intent.hasExtra("User")){
            user = intent.extras?.getSerializable("User") as User;
            setCurrentBudget(user.budget);
        }

        run("https://belarusbank.by/api/kursExchange?city=Минск")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_item_exit -> exit();
            R.id.action_fund_account -> showFundBankAccountDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    fun changeUserBudget(budget : Int) {
        UserService.changeUserBudget(this, user.id, budget);
        setCurrentBudget(budget);
    }

    private fun showFundBankAccountDialog(){
        val dialog = FundBankAccountDialog(this);
        dialog.show(supportFragmentManager, "FundBankAccount");
    }

    private fun exit() {
        SignInService.logoutUser(this);

        val intent = Intent(this, SignIn::class.java);
        startActivity(intent);
        finish();
    }

    private fun setCurrentBudget(budget : Int) {
        supportActionBar?.title = "Текущий бюджет: $budget"
    }

    private fun findViews() {
        calendarView = findViewById(R.id.calendarView);
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response){
                Log.d("api: ", response.body()?.string().toString())
            }
        })
    }
}