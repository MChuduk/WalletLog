package com.example.walletlog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.walletlog.screens.DailySpending
import com.example.walletlog.screens.DiagramSpending
import com.example.walletlog.screens.MainActivity

class ViewPageAdapter : FragmentStateAdapter {

    private var mainActivity: MainActivity;

    constructor(fragmentManager : FragmentManager, lifecycle : Lifecycle, mainActivity: MainActivity)
            : super(fragmentManager, lifecycle) {
        this.mainActivity = mainActivity;
    }

    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return DailySpending(mainActivity);
            2 -> return DiagramSpending(mainActivity);
        }
        return DiagramSpending(mainActivity);
    }
}