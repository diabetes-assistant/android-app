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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showDiaryViewModel = ViewModelProvider(this).get(ShowDiaryViewModel::class.java)

        barEntryArrayList = ArrayList()
        labelsNamesArrayList = ArrayList()
        idArrayList = ArrayList<Any>()
        showDiaryViewModel.fillDataArrayList()

        for (i in showDiaryViewModel.dataArrayList.indices) {
            val id: Int = showDiaryViewModel.dataArrayList[i].id
            val date: String = showDiaryViewModel.dataArrayList[i].date
            val time: String = showDiaryViewModel.dataArrayList[i].time
            val bloodGlucose: Int = showDiaryViewModel.dataArrayList[i].bloodGlucose
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
}
