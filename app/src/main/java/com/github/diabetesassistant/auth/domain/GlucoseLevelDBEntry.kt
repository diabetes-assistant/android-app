package com.github.diabetesassistant.auth.domain

import java.time.LocalDateTime

class GlucoseLevelDBEntry(var id: Int, var dateTime: LocalDateTime, var bloodGlucose: Int)
