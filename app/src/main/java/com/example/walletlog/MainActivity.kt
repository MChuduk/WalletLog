package com.example.walletlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.walletlog.services.SignInService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.hasExtra("User")){
            val user = intent.extras?.getSerializable("User") as User;
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_item_exit -> exit();
        }
        return super.onOptionsItemSelected(item);
    }

    private fun exit() {
        SignInService.logoutUser(this);

        val intent = Intent(this, SignIn::class.java);
        startActivity(intent);
        finish();
    }
}