package com.thuan.vietlott535sniper
object PredictionEngine {
    fun predict(history: List<Draw>): List<PredictedSet> {
        if (history.isEmpty()) return listOf(PredictedSet(listOf(9,14,20,31,35),12,99.0))
        val freq = history.flatMap { it.main }.groupingBy { it }.eachCount()
        val gaps = mutableMapOf<Int,Int>()
        for (n in 1..35) {
            var gap=0
            for (d in history) { if (n in d.main) break else gap++ }
            gaps[n]=gap
        }
        val hot = freq.entries.sortedByDescending { it.value }.take(15).map { it.key }
        val overdue = gaps.entries.sortedByDescending { it.value }.take(15).map { it.key }
        val poolBase = (hot+overdue).distinct()
        val candidates = mutableListOf<PredictedSet>()
        repeat(200) {
            val pick = poolBase.shuffled().take(5).sorted()
            val sum = pick.sum()
            if (sum !in 70..130) return@repeat
            val odd = pick.count { it%2==1 }
            if (odd !in 2..3) return@repeat
            val special = (1..12).random()
            var score = 0.0
            score += pick.sumOf { freq[it]?:0 } * 0.6
            score += pick.sumOf { gaps[it]?:0 } * 1.2
            if (odd==3) score+=5
            candidates.add(PredictedSet(pick, special, score))
        }
        return candidates.sortedByDescending { it.score }.distinctBy { it.main to it.special }.take(10)
    }
}