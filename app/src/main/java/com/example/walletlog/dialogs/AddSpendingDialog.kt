package com.example.walletlog.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.walletlog.screens.MainActivity
import com.example.walletlog.R

class AddSpendingDialog(val activity: MainActivity) : AppCompatDialogFragment() {

    private lateinit var amountEditText : EditText;
    private lateinit var spendingNoteEditText : EditText;
    private lateinit var addSpendingLinkLabel : TextView;
    private lateinit var categorySpinner : Spinner;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = layoutInflater.inflate(R.layout.add_spending_dialog, null)

        findViews(view);
        setLinkLabelsListeners();

        val categories = arrayOf("Еда", "Отдых", "Жилье");
        categorySpinner.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, categories);

        builder.setView(view)
        return builder.create()
    }

    private fun setLinkLabelsListeners() {
        addSpendingLinkLabel.setOnClickListener {
            val value = Integer.parseInt(amountEditText.text.toString());
            val note = spendingNoteEditText.text.toString();
            val category = categorySpinner.selectedItemPosition.toString();

            activity.addSpending(value, note, category);
            dismiss();
        }
    }

    private fun findViews(view : View) {
        amountEditText = view.findViewById(R.id.spendingAmountEditText);
        spendingNoteEditText = view.findViewById(R.id.spendingNoteEditText);
        addSpendingLinkLabel = view.findViewById(R.id.addSpendingLinkLabel);
        categorySpinner = view.findViewById(R.id.categorySpinner);
    }
}