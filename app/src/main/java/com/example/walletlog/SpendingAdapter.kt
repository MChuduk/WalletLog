package com.example.walletlog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.walletlog.model.Spending
import com.example.walletlog.model.User
import com.example.walletlog.services.CurrencyService

class SpendingAdapter(val authorizedUser : User, private var items: List<Spending>) :
    RecyclerView.Adapter<SpendingAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var spendingNoteTextView: TextView? = null
        var spendingValueTextView: TextView? = null
        var spendingCategoryTextView: TextView? = null
        var spendingDeleteButtonClick : TextView? = null;

        init {
            spendingNoteTextView = itemView.findViewById(R.id.spendingNoteTextView);
            spendingValueTextView = itemView.findViewById(R.id.spendingValueTextView);
            spendingCategoryTextView = itemView.findViewById(R.id.categoryTextView);
            spendingDeleteButtonClick = itemView.findViewById(R.id.removeSpendingLinkLabel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.spending_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.spendingNoteTextView?.text = items[position].note;

        val currency = authorizedUser.currency;
        val currencyMultiplier = CurrencyService.getCurrencyMultiplier(currency);
        val amount = items[position].value / currencyMultiplier

        holder.spendingValueTextView?.text = "-${amount.format(1)} $currency";
        holder.spendingDeleteButtonClick?.tag = items[position].id;

        var category = "";
        when (items[position].category) {
            "0" -> category = "Еда"
            "1" -> category = "Отдых"
            "2" -> category = "Жилье"
        }

        holder.spendingCategoryTextView?.text = "Категория: ${category}";
    }

    override fun getItemCount() = items.size

    fun updateAdapter(mDataList: MutableList<Spending>) {
        this.items = mDataList
        notifyDataSetChanged()
    }
}