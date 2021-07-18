package com.github.diabetesassistant.dosage.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ShowDiaryViewModel : ViewModel() {
    val tag: String = "ShowDiaryViewModel"

    var dataArrayList: ArrayList<GlucoseLevelDBEntry> = ArrayList<GlucoseLevelDBEntry>()
    var dataArrayList2: ArrayList<GlucoseLevelDBEntry2> = ArrayList<GlucoseLevelDBEntry2>()

    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

    fun fillDataArrayList() {
        dataArrayList.clear()
        var counter: Int = 0
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_01, time_01, bloodGlucose_01))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_02, time_02, bloodGlucose_02))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_03, time_03, bloodGlucose_03))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_04, time_04, bloodGlucose_04))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_05, time_05, bloodGlucose_05))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_06, time_06, bloodGlucose_06))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_07, time_07, bloodGlucose_07))

        dataArrayList2.clear()
        dataArrayList2.add(
            GlucoseLevelDBEntry2(
                counter++,
                LocalDateTime.parse(dateTime01, dateTimeFormatter),
                time_01,
                bloodGlucose_01
            )
        )
        dataArrayList2.add(
            GlucoseLevelDBEntry2(
                counter++,
                LocalDateTime.parse(dateTime02, dateTimeFormatter),
                time_02,
                bloodGlucose_02
            )
        )
        dataArrayList2.add(
            GlucoseLevelDBEntry2(
                counter++,
                LocalDateTime.parse(dateTime03, DateTimeFormatter.ISO_DATE_TIME),
                time_03,
                bloodGlucose_03
            )
        )
        dataArrayList2.add(
            GlucoseLevelDBEntry2(
                counter++,
                LocalDateTime.parse(dateTime04, dateTimeFormatter),
                time_04,
                bloodGlucose_04
            )
        )
        dataArrayList2.add(
            GlucoseLevelDBEntry2(
                counter++,
                LocalDateTime.parse(dateTime05, dateTimeFormatter),
                time_05,
                bloodGlucose_05
            )
        )
        dataArrayList2.add(
            GlucoseLevelDBEntry2(
                counter++,
                LocalDateTime.parse(dateTime06, dateTimeFormatter),
                time_06,
                bloodGlucose_06
            )
        )
        dataArrayList2.add(
            GlucoseLevelDBEntry2(
                counter++,
                LocalDateTime.parse(dateTime07, dateTimeFormatter),
                time_07,
                bloodGlucose_07
            )
        )

        for (i in dataArrayList2.indices) {
            Log.i(tag,"dataArrayList2 unsortiert, index="+i+": "+dataArrayList2[i].dateTime)
        }
    }

    // TODO !! Hier weitermachen, um die dataArrayList nach Datum der GlucoseLevelDBEntry zu sortieren !!
    // https://www.programiz.com/kotlin-programming/examples/sort-custom-objects-property
    // https://stackoverflow.com/questions/40046141/kotlin-sort-list-by-using-formatted-date-string-functional/40058598
    fun sortDataArrayList() {
        dataArrayList = dataArrayList.sortedWith(compareBy({ it.date })).toCollection(ArrayList())
        dataArrayList2 = dataArrayList2.sortedWith(compareBy({ it.dateTime })).toCollection(ArrayList())

        for (i in dataArrayList2.indices) {
            Log.i(tag,"dataArrayList2 sortiert, index="+i+": "+dataArrayList2[i].dateTime)
        }
    }

    // Konstanten nur für den Test
    companion object PresetDBEntries {
        // dateTime constants
        const val dateTime01: String ="2020-01-01T10:00:00.000"
        const val dateTime02: String ="2020-01-02T08:00:00.000"
        const val dateTime03: String ="2020-01-04T13:00:00.000"
        const val dateTime04: String ="2020-01-04T12:00:00.000"
        const val dateTime05: String ="2019-12-25T16:00:00.000"
        const val dateTime06: String ="2020-01-10T13:00:00.000"
        const val dateTime07: String ="2020-01-09T09:00:00.000"

        // date constants
        const val date_01: String = "01.01.2020"
        const val date_02: String = "02.01.2020"
        const val date_03: String = "04.01.2020"
        const val date_04: String = "05.01.2020"
        const val date_05: String = "25.12.2019"
        const val date_06: String = "10.01.2020"
        const val date_07: String = "09.01.2020"

        // time constants
        const val time_01: String = "10:00"
        const val time_02: String = "8:00"
        const val time_03: String = "11:00"
        const val time_04: String = "12:00"
        const val time_05: String = "16:00"
        const val time_06: String = "13:00"
        const val time_07: String = "09:00"

        // bloodGlucose constants
        const val bloodGlucose_01: Int = 100
        const val bloodGlucose_02: Int = 80
        const val bloodGlucose_03: Int = 140
        const val bloodGlucose_04: Int = 60
        const val bloodGlucose_05: Int = 200
        const val bloodGlucose_06: Int = 210
        const val bloodGlucose_07: Int = 120
    }
}
