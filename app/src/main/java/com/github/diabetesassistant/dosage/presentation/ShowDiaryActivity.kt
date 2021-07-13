package com.github.diabetesassistant.dosage.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.databinding.ActivityShowDiaryBinding
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
class ShowDiaryActivity : AppCompatActivity() {

    private lateinit var showDiaryViewModel: ShowDiaryViewModel
    private lateinit var binding: ActivityShowDiaryBinding

    var barChart: BarChart? = null
    var barEntryArrayList: ArrayList<BarEntry>? = null
    var labelsNamesArrayList: ArrayList<String>? = null
    var idArrayList: ArrayList<*>? = null
    var dataArrayList: ArrayList<Data> = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showDiaryViewModel = ViewModelProvider(this).get(ShowDiaryViewModel::class.java)

        barEntryArrayList = ArrayList()
        labelsNamesArrayList = ArrayList()
        idArrayList = ArrayList<Any>()
        fillDataArrayList()

        for (i in dataArrayList.indices) {
            val id: Int = dataArrayList[i].id
            val date: String = dataArrayList[i].date
            val time: String = dataArrayList[i].time
            val bloodGlucose: Int = dataArrayList[i].bloodGlucose
            barEntryArrayList!!.add(BarEntry(i.toFloat(), bloodGlucose.toFloat()))
            labelsNamesArrayList!!.add(date)
        }
        val barDataSet = BarDataSet(barEntryArrayList, "Blood glucose level")
        // barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        val description = Description()
        description.text = "id"
        barChart!!.setDescription(description)
        val barData = BarData(barDataSet)
        barChart!!.setData(barData)
        val xAxis = barChart!!.getXAxis()
        xAxis.valueFormatter = IndexAxisValueFormatter(labelsNamesArrayList)
        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.granularity = 1f
        xAxis.labelCount = labelsNamesArrayList!!.size
        xAxis.labelRotationAngle = 270f
        barChart!!.animateY(2000)
        barChart!!.invalidate()
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