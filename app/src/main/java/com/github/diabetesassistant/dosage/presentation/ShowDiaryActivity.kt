package com.github.diabetesassistant.dosage.presentation

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
    val dateLabelArrayList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Zurück-Button erscheint, aber funktioniert merkwürdigerweise nicht
        // trotz der folgenden Zeile
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showDiaryViewModel = ViewModelProvider(this).get(ShowDiaryViewModel::class.java)
        barChart = binding.barChartOne
        showDiaryViewModel.fillDataArrayList()
        showDiaryViewModel.sortDataArrayList()

        initBarChart()
        val dBEntryArrayList: ArrayList<BarEntry> = ArrayList()

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
            // TODO !! Hier weitermachen, die Blutzuckerwerte nach Datum sortieren !!
            val dateLabel: String = showDiaryViewModel.dataArrayList[i].date
            dateLabelArrayList.add(dateLabel)
            Log.i(tag, "dateLabelArrayList[" + i + "]=" + dateLabelArrayList[i])
        }
        Log.i(tag, "dateLabelArrayList.size=" + dateLabelArrayList.size)

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
        // xAxis.valueFormatter = MyAxisFormatter()
        xAxis.valueFormatter = indexAxisValueFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = barGraphGranularity
        xAxis.labelRotationAngle = barGraphRotationAngle
    }

    // https://stackoverflow.com/questions/47637653/how-to-set-x-axis-labels-in-mp-android-chart-bar-graph
    inner class indexAxisValueFormatter : IndexAxisValueFormatter() {

        override fun getFormattedValue(value: Float, axisBase: AxisBase): String {
            return dateLabelArrayList[value.toInt()]
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
