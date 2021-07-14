package com.github.diabetesassistant.dosage.presentation

import androidx.lifecycle.ViewModel
import java.util.*

class ShowDiaryViewModel : ViewModel() {
    val tag: String = "ShowDiaryViewModel"

    var dataArrayList: ArrayList<GlucoseLevelDBEntry> = ArrayList<GlucoseLevelDBEntry>()

    fun fillDataArrayList() {
        dataArrayList.clear()
        var counter: Int = 0;
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_01, time_01, bloodGlucose_01))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_02, time_02, bloodGlucose_02))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_03, time_03, bloodGlucose_03))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_04, time_04, bloodGlucose_04))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_05, time_05, bloodGlucose_05))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_06, time_06, bloodGlucose_06))
        dataArrayList.add(GlucoseLevelDBEntry(counter++, date_07, time_07, bloodGlucose_07))
    }

    // Konstanten nur für den Test
    companion object PresetDBEntries {
        // date constants
        const val date_01: String = "1.1.2020"
        const val date_02: String = "2.1.2020"
        const val date_03: String = "4.1.2020"
        const val date_04: String = "5.1.2020"
        const val date_05: String = "25.12.2019"
        const val date_06: String = "10.1.2020"
        const val date_07: String = "9.1.2020"

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
