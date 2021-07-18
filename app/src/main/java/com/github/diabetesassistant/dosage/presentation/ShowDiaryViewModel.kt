package com.github.diabetesassistant.dosage.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class ShowDiaryViewModel : ViewModel() {
    val tag: String = "ShowDiaryViewModel"
    var dataArrayList: ArrayList<GlucoseLevelDBEntry> = ArrayList<GlucoseLevelDBEntry>()
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

    fun fillDataArrayList() {
        var counter: Int = 0
        dataArrayList.clear()
        dataArrayList.add(
            GlucoseLevelDBEntry(
                counter++,
                LocalDateTime.parse(dateTime01, dateTimeFormatter),
                bloodGlucose_01
            )
        )
        dataArrayList.add(
            GlucoseLevelDBEntry(
                counter++,
                LocalDateTime.parse(dateTime02, dateTimeFormatter),
                bloodGlucose_02
            )
        )
        dataArrayList.add(
            GlucoseLevelDBEntry(
                counter++,
                LocalDateTime.parse(dateTime03, DateTimeFormatter.ISO_DATE_TIME),
                bloodGlucose_03
            )
        )
        dataArrayList.add(
            GlucoseLevelDBEntry(
                counter++,
                LocalDateTime.parse(dateTime04, dateTimeFormatter),
                bloodGlucose_04
            )
        )
        dataArrayList.add(
            GlucoseLevelDBEntry(
                counter++,
                LocalDateTime.parse(dateTime05, dateTimeFormatter),
                bloodGlucose_05
            )
        )
        dataArrayList.add(
            GlucoseLevelDBEntry(
                counter++,
                LocalDateTime.parse(dateTime06, dateTimeFormatter),
                bloodGlucose_06
            )
        )
        dataArrayList.add(
            GlucoseLevelDBEntry(
                counter++,
                LocalDateTime.parse(dateTime07, dateTimeFormatter),
                bloodGlucose_07
            )
        )

        for (i in dataArrayList.indices) {
            Log.i(tag, "dataArrayList2 unsortiert, index=" + i + ": " + dataArrayList[i].dateTime)
        }
    }

    // Hier wird die ArrayList nach Datum der GlucoseLevelDBEntry sortiert
    // https://www.programiz.com/kotlin-programming/examples/sort-custom-objects-property
    // https://stackoverflow.com/questions/40046141/kotlin-sort-list-by-using-formatted-date-string-functional/40058598
    fun sortDataArrayList() {
        dataArrayList =
            dataArrayList.sortedWith(compareBy({ it.dateTime })).toCollection(ArrayList())
        for (i in dataArrayList.indices) {
            Log.i(tag, "dataArrayList2 sortiert, index=" + i + ": " + dataArrayList[i].dateTime)
        }
    }

    // Konstanten nur für den Test
    companion object PresetDBEntries {
        // dateTime constants
        const val dateTime01: String = "2020-01-01T10:00:00.000"
        const val dateTime02: String = "2020-01-02T08:00:00.000"
        const val dateTime03: String = "2020-01-04T13:00:00.000"
        const val dateTime04: String = "2020-01-04T12:00:00.000"
        const val dateTime05: String = "2019-12-25T16:00:00.000"
        const val dateTime06: String = "2020-01-10T13:00:00.000"
        const val dateTime07: String = "2020-01-09T09:00:00.000"

        // bloodGlucose constants
        const val bloodGlucose_01: Int = 100
        const val bloodGlucose_02: Int = 180
        const val bloodGlucose_03: Int = 140
        const val bloodGlucose_04: Int = 120
        const val bloodGlucose_05: Int = 200
        const val bloodGlucose_06: Int = 210
        const val bloodGlucose_07: Int = 120
    }
}
