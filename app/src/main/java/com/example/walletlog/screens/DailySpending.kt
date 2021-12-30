package com.example.walletlog.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletlog.R
import com.example.walletlog.model.Spending

class DailySpending(val mainActivity: MainActivity) : Fragment() {

    private lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_spending, container, false);
        findViews(view);

        recyclerView.adapter = mainActivity.spendingAdapter;
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager


        return view;
    }

    private fun findViews(view : View) {
        recyclerView = view.findViewById(R.id.recyclerView)!!;
    }

}