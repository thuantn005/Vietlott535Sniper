package com.thuan.vietlott535sniper
data class Draw(val date: String, val main: List<Int>, val special: Int, val jackpot: Long = 0)
data class PredictedSet(val main: List<Int>, val special: Int, val score: Double)