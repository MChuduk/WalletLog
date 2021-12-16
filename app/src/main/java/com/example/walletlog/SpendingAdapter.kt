package com.example.walletlog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SpendingAdapter(private val items: List<Spending>) :
    RecyclerView.Adapter<SpendingAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var spendingNoteTextView: TextView? = null
        var spendingValueTextView: TextView? = null
        var spendingDeleteButtonClick : TextView? = null;

        init {
            spendingNoteTextView = itemView.findViewById(R.id.spendingNoteTextView);
            spendingValueTextView = itemView.findViewById(R.id.spendingValueTextView);
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
        holder.spendingValueTextView?.text = "-${items[position].value}";
        holder.spendingDeleteButtonClick?.tag = items[position].id;
    }

    override fun getItemCount() = items.size
}