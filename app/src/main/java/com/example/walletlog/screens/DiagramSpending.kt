package com.example.walletlog.screens

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walletlog.R
import com.example.walletlog.SqliteDbHelper
import com.example.walletlog.contracts.*
import com.example.walletlog.getValueInteger
import com.example.walletlog.getValueString
import com.example.walletlog.model.Spending
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo

class DiagramSpending(val mainActivity: MainActivity) : Fragment() {

    private lateinit var pie : AnimatedPieView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_diagram_spending, container, false);
        pie = view.findViewById<AnimatedPieView>(R.id.animatedPieView);
        return view;
    }

    override fun onResume() {
        drawPie();
        super.onResume()
    }

    fun drawPie() {
        val config = AnimatedPieViewConfig();
        val angle : Float = -90F;
        config.startAngle(angle)
            .duration(1000)
            .drawText(true)
            .strokeMode(false)
            .textSize(30f);

        addPieData(config);
        pie.applyConfig(config);
        pie.start();
    }

    fun addPieData(config: AnimatedPieViewConfig) {

        var sum = 0.0;
        var sum_cat0 = 0.0;
        var sum_cat1 = 0.0;
        var sum_cat2 = 0.0;

        for(spending in mainActivity.spendingListInView)
            sum += spending.value;

        println("sum: ${sum}")

        for(spending in mainActivity.spendingListInView.filter { it.category == "0" })
            sum_cat0 += spending.value;

        for(spending in mainActivity.spendingListInView.filter { it.category == "1" })
            sum_cat1 += spending.value;

        for(spending in mainActivity.spendingListInView.filter { it.category == "2" })
            sum_cat2 += spending.value;

        if(sum_cat0 != 0.0) {
            val percentage = (sum_cat0 / sum) * 100;
            config.addData(SimplePieInfo(percentage, Color.parseColor("#683bff"), "Еда"));
        }
        if(sum_cat1 != 0.0) {
            val percentage = (sum_cat1 / sum) * 100;
            config.addData(SimplePieInfo(percentage, Color.parseColor("#ff0059"), "Отдых"));
        }
        if(sum_cat2 != 0.0) {
            val percentage = (sum_cat2 / sum) * 100;
            config.addData(SimplePieInfo(percentage, Color.parseColor("#42e026"), "Жилье"));
        }
    }
}