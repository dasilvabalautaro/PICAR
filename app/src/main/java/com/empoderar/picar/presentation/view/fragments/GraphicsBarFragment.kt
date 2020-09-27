package com.empoderar.picar.presentation.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.empoderar.picar.R
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.view_bar_chart.*
import java.util.*

class GraphicsBarFragment: BaseFragment() {
    override fun layoutId() = R.layout.view_bar_chart
    override fun renderFailure(message: Int) {
        notify(message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val numberEmployee = ArrayList<BarEntry>()

        numberEmployee.add(BarEntry(945f, 0f))
        numberEmployee.add(BarEntry(1040f, 1f))
        numberEmployee.add(BarEntry(1133f, 2f))
        numberEmployee.add(BarEntry(1240f, 3f))
        numberEmployee.add(BarEntry(1369f, 4f))
        numberEmployee.add(BarEntry(1487f, 5f))
        numberEmployee.add(BarEntry(1501f, 6f))
        numberEmployee.add(BarEntry(1645f, 7f))
        numberEmployee.add(BarEntry(1578f, 8f))
        numberEmployee.add(BarEntry(1695f, 9f))

        val year = ArrayList<String>()

        year.add("2008")
        year.add("2009")
        year.add("2010")
        year.add("2011")
        year.add("2012")
        year.add("2013")
        year.add("2014")
        year.add("2015")
        year.add("2016")
        year.add("2017")

        val barDataSet =  BarDataSet(numberEmployee, "No Of Employee")
        barDataSet.axisDependency = YAxis.AxisDependency.LEFT;
        barDataSet.color = Color.YELLOW
        barDataSet.isHighlightEnabled = true
        barDataSet.highLightColor = Color.RED
        barDataSet.valueTextColor = Color.BLUE


        val data = BarData(barDataSet)
        val barChart = this.bar_chart
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(year)
        barChart.animateY(1000);

        barChart.xAxis.isGranularityEnabled = true;
        barChart.xAxis.granularity = 1.0f;
        barChart.xAxis.labelCount = barDataSet.entryCount;

        barChart.data = data;

    }
}