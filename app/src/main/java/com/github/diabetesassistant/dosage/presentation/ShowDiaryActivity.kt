package com.github.diabetesassistant.dosage.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.*

/**
 * https://www.youtube.com/watch?v=sXo2SkX7rGk&t=55s
 */
class ShowGlucoseDiaryActivity : AppCompatActivity() {
    var barChart: BarChart? = null
    var barEntryArrayList: ArrayList<BarEntry>? = null
    var labelsNamesArrayList: ArrayList<String>? = null
    var idArrayList: ArrayList<*>? = null
    var dataArrayList: ArrayList<Data> = ArrayList<Data>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_glucose_diary)
        barChart = findViewById(R.id.barChartOne)
        barEntryArrayList = ArrayList<BarEntry>()
        labelsNamesArrayList = ArrayList()
        idArrayList = ArrayList<Any>()
        fillDataArrayList()
        for (i in dataArrayList.indices) {
            val id: Int = dataArrayList[i].getId()
            val date: String = dataArrayList[i].getDate()
            val time: String = dataArrayList[i].getTime()
            val bloodGlucose: Int = dataArrayList[i].getBloodGlucose()
            barEntryArrayList!!.add(BarEntry(i, bloodGlucose))
            labelsNamesArrayList!!.add(date)
            idArrayList.add(id)
        }
        val barDataSet = BarDataSet(barEntryArrayList, "Blood glucose level")
        // barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        val description = Description()
        description.setText("id")
        barChart.setDescription(description)
        val barData = BarData(barDataSet)
        barChart.setData(barData)
        val xAxis: XAxis = barChart.getXAxis()
        xAxis.setValueFormatter(IndexAxisValueFormatter(labelsNamesArrayList))
        xAxis.setPosition(XAxis.XAxisPosition.TOP)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.setGranularity(1f)
        xAxis.setLabelCount(labelsNamesArrayList!!.size)
        xAxis.setLabelRotationAngle(270)
        barChart.animateY(2000)
        barChart.invalidate()
    }

    private fun fillDataArrayList() {
        dataArrayList.clear()
        dataArrayList.add(Data(1, "1.1.2020", "10:00", 100))
        dataArrayList.add(Data(2, "2.1.2020", "8:00", 80))
        dataArrayList.add(Data(3, "4.1.2020", "11:00", 140))
        dataArrayList.add(Data(4, "5.1.2020", "12:00", 60))
        dataArrayList.add(Data(5, "5.1.2020", "16:00", 200))
        dataArrayList.add(Data(6, "10.1.2020", "13:00", 210))
        dataArrayList.add(Data(7, "12.1.2020", "09:00", 120))
    }
}