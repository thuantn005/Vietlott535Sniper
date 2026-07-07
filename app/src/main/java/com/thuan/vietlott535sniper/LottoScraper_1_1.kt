package com.thuan.vietlott535sniper
import org.jsoup.Jsoup
object LottoScraper {
    fun fetchHistory(pages: Int = 3): List<Draw> {
        val all = mutableListOf<Draw>()
        for (i in 1..pages) {
            try {
                val url = "https://www.lotto-8.com/Vietnam/listltoVM35.asp?indexpage=$i"
                val doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(15000).get()
                val rows = doc.select("table table tr")
                for (tr in rows) {
                    val tds = tr.select("td")
                    if (tds.size >= 7) {
                        val nums = mutableListOf<Int>()
                        for (k in 1..5) { tds[k].text().toIntOrNull()?.let { nums.add(it) } }
                        val sp = tds[6].text().toIntOrNull() ?: continue
                        if (nums.size==5) all.add(Draw(tds[0].text(), nums, sp))
                    }
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
        return all
    }
}