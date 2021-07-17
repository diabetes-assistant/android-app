package com.github.diabetesassistant.dosage.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.R
import com.github.diabetesassistant.databinding.ActivityShowDiaryBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

/**
 * https://intensecoder.com/bar-chart-tutorial-in-android-using-kotlin/
 */
class ShowDiaryActivity : AppCompatActivity() {

    private lateinit var showDiaryViewModel: ShowDiaryViewModel
    private lateinit var binding: ActivityShowDiaryBinding

    val tag: String = "ShowDiaryActivity"

    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(tag, "Wechsel in die ShowDiaryActivity erfolgreich (1)")

        super.onCreate(savedInstanceState)
        binding = ActivityShowDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showDiaryViewModel = ViewModelProvider(this).get(ShowDiaryViewModel::class.java)
        barChart = binding.barChartOne
        showDiaryViewModel.fillDataArrayList()

        initBarChart()
        val dBEntryArrayList: ArrayList<BarEntry> = ArrayList()
        val dateLabelArrayList: ArrayList<String> = ArrayList()

        for (i in showDiaryViewModel.dataArrayList.indices) {
            // Jetzt wird eine ArrayList mit den Daten erstellt
            // (anhand dem showDiaryViewModel)
            val glucoseLevelDBEntry = showDiaryViewModel.dataArrayList[i]
            dBEntryArrayList.add(
                BarEntry(
                    i.toFloat(),
                    glucoseLevelDBEntry.bloodGlucose.toFloat()
                )
            )
            // TODO !! Hier weitermachen mit der Datums-Beschriftung der einzelnen Säulen !!
            // Hier geschildert, aber leider in Java:
            // https://stackoverflow.com/questions/47637653/how-to-set-x-axis-labels-in-mp-android-chart-bar-graph/49953312
            // Jetzt ein Array mit den Labels/ Beschriftungen der x-Achse erstellen
            // (auch anhand showDiaryViewModel)
            val dateLabel: String = showDiaryViewModel.dataArrayList[i].date
            dateLabelArrayList.add(dateLabel)
        }

        val barDataSet = BarDataSet(dBEntryArrayList, "")
        // https://stackoverflow.com/questions/31842983/getresources-getcolor-is-deprecated
        // https://www.codegrepper.com/code-examples/kotlin/android+get+color+from+resource
        barDataSet.setColor(ContextCompat.getColor(this, R.color.secondary_light))
        val data = BarData(barDataSet)
        barChart.data = data
        // Was macht die folgende Zeile?
        barChart.invalidate()
    }

    private fun initBarChart() {
        // hide grid lines
        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        // remove right y-axis
        barChart.axisRight.isEnabled = false

        // remove legend
        barChart.legend.isEnabled = false

        // remove description label
        barChart.description.isEnabled = false

        // add animation
        barChart.animateY(barGraphAnimationDuration)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = barGraphGranularity
        xAxis.labelRotationAngle = barGraphRotationAngle
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d(TAG, "getAxisLabel: index $index")
            return if (index < showDiaryViewModel.dataArrayList.size) {
                showDiaryViewModel.dataArrayList[index].date
            } else {
                ""
            }
        }
    }

    // Definition von Konstanten für den BarGraph, um
    // Magic-Number-Warnungen des static code check zu umgehen
    companion object BarGraphPresets {
        const val barGraphRotationAngle: Float = +90f
        const val barGraphAnimationDuration: Int = 1000
        const val barGraphGranularity: Float = 1f
    }
}
