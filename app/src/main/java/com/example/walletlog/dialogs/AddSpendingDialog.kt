package com.example.walletlog.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.walletlog.MainActivity
import com.example.walletlog.R

class AddSpendingDialog(val activity: MainActivity) : AppCompatDialogFragment() {

    private lateinit var amountEditText : EditText;
    private lateinit var spendingNoteEditText : EditText;
    private lateinit var addSpendingLinkLabel : TextView;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = layoutInflater.inflate(R.layout.add_spending_dialog, null)

        findViews(view);
        setLinkLabelsListeners();

        builder.setView(view)
        return builder.create()
    }

    private fun setLinkLabelsListeners() {
        addSpendingLinkLabel.setOnClickListener {
            val value = Integer.parseInt(amountEditText.text.toString());
            val note = spendingNoteEditText.text.toString();

            activity.addSpending(value, note);
            dismiss();
        }
    }

    private fun findViews(view : View) {
        amountEditText = view.findViewById(R.id.spendingAmountEditText);
        spendingNoteEditText = view.findViewById(R.id.spendingNoteEditText);
        addSpendingLinkLabel = view.findViewById(R.id.addSpendingLinkLabel);
    }
}