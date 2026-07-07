package com.thuan.vietlott535sniper
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.time.LocalTime
object VietlottApi {
    fun getCurrentJackpot(): Long {
        // TODO: Cào từ vietlott-sms.vn, tạm trả 12.8 tỷ để test sniper
        return 12_800_000_000L
    }
}
class SniperWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val jackpot = VietlottApi.getCurrentJackpot()
        if (jackpot > 12_000_000_000L) {
            val now = LocalTime.now()
            if (now.isAfter(LocalTime.of(19,0)) && now.isBefore(LocalTime.of(20,30))) {
                val history = LottoScraper.fetchHistory()
                val pred = PredictionEngine.predict(history).take(3)
                // Auto send - sẽ gọi SmsSender ở MainActivity để có context
            }
        }
        return Result.success()
    }
}