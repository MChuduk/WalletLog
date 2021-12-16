package com.example.walletlog.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.walletlog.*

class FundBankAccountDialog(val activity: MainActivity) : AppCompatDialogFragment() {

    private lateinit var amountEditText : EditText;
    private lateinit var setBankAccountLinkLabel : TextView;
    private lateinit var fundBankAccountLinkLabel : TextView;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = layoutInflater.inflate(R.layout.fund_bank_account, null)

        findViews(view);
        setLinkLabelsListeners();

        builder.setView(view)
        return builder.create()
    }

    private fun setLinkLabelsListeners() {
        setBankAccountLinkLabel.setOnClickListener {
            val amount = Integer.parseInt(amountEditText.text.toString());
            activity.changeUserBudget(amount);
            dismiss();
        }
        fundBankAccountLinkLabel.setOnClickListener {
            val amount = Integer.parseInt(amountEditText.text.toString());
            activity.changeUserBudget(activity.user.budget + amount);
            dismiss();
        }
    }

    private fun findViews(view : View) {
        amountEditText = view.findViewById(R.id.spendingAmountEditText);
        setBankAccountLinkLabel = view.findViewById(R.id.setBankAccountLinkLabel);
        fundBankAccountLinkLabel = view.findViewById(R.id.addSpendingLinkLabel);
    }
}