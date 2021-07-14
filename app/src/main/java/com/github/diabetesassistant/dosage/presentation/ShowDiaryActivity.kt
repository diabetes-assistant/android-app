package com.github.diabetesassistant.dosage.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.diabetesassistant.databinding.ActivityShowDiaryBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

/**
 * https://intensecoder.com/bar-chart-tutorial-in-android-using-kotlin/
 */
class ShowDiaryActivity : AppCompatActivity() {

    private lateinit var showDiaryViewModel: ShowDiaryViewModel
    private lateinit var binding: ActivityShowDiaryBinding

    val tag: String = "ShowDiaryActivity"

    private lateinit var barChart: BarChart
    private var scoreList = ArrayList<Score>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(tag, "Wechsel in die ShowDiaryActivity erfolgreich (1)")

        super.onCreate(savedInstanceState)
        binding = ActivityShowDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showDiaryViewModel = ViewModelProvider(this).get(ShowDiaryViewModel::class.java)

        barChart = binding.barChartOne
        scoreList = getScoreList()
        initBarChart()

        //now draw bar chart with dynamic data
        val entries: ArrayList<BarEntry> = ArrayList()

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(BarEntry(i.toFloat(), score.score.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        barChart.data = data

        barChart.invalidate()

    }

    private fun initBarChart() {


//        hide grid lines
        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        barChart.axisRight.isEnabled = false

        //remove legend
        barChart.legend.isEnabled = false


        //remove description label
        barChart.description.isEnabled = false


        //add animation
        barChart.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d(TAG, "getAxisLabel: index $index")
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }

    private fun getScoreList(): ArrayList<Score> {
        var scoreList = ArrayList<Score>()
        scoreList.add(Score("John", 56))
        scoreList.add(Score("Rey", 75))
        scoreList.add(Score("Steve", 85))
        scoreList.add(Score("Kevin", 45))
        scoreList.add(Score("Jeff", 63))

        return scoreList
    }

    // Definition von Konstanten für den BarGraph, um
    // Magic-Number-Warnungen des static code check zu umgehen
    companion object BarGraphPresets {
        const val rotationAngle: Float = 270f
        const val animationDuration: Int = 2000
    }
}
