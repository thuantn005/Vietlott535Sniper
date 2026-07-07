package com.thuan.vietlott535sniper
import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast
object SmsSender {
    fun send(context: Context, sets: List<PredictedSet>) {
        try {
            val body = sets.joinToString(", ") { "${it.main.joinToString(" ") { n->"%02d".format(n) }}-${"%02d".format(it.special)}" }
            val msg = "ZLP 535 K1 S $body"
            val sms = SmsManager.getDefault()
            sms.sendTextMessage("9969", null, msg, null, null)
            Toast.makeText(context, "Da ban SNIPER: $msg", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Loi SMS: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}