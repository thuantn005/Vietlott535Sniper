package com.thuan.vietlott535sniper
import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1)
        setContent {
            var history by remember { mutableStateOf<List<Draw>>(emptyList()) }
            var predicted by remember { mutableStateOf<List<PredictedSet>>(emptyList()) }
            var jackpot by remember { mutableStateOf(12_800_000_000L) }
            var sniperArmed by remember { mutableStateOf(true) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) { history = LottoScraper.fetchHistory(2) }
                predicted = PredictionEngine.predict(history)
            }

            MaterialTheme {
                Scaffold { pad ->
                    Column(Modifier.padding(pad).padding(16.dp)) {
                        Text("VIETLOTT 535 SNIPER", style=MaterialTheme.typography.headlineMedium)
                        Spacer(Modifier.height(8.dp))
                        Card(colors=CardDefaults.cardColors(containerColor=Color(0xFFB71C1C))) {
                            Column(Modifier.padding(16.dp)) {
                                Text("JACKPOT: ${jackpot/1e9} tỷ", color=Color.White)
                                Text(if (jackpot>12e9) "CHIA GIẢI ĐỘC ĐẮC - Kỳ 21h mai!" else "Đang tích lũy", color=Color.White)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Row { Switch(checked=sniperArmed, onCheckedChange={sniperArmed=it}); Text("  Sniper Mode - Tự bắn khi chia giải") }
                        Spacer(Modifier.height(12.dp))
                        Text("Dự đoán AI (5 số chính + đặc biệt):", style=MaterialTheme.typography.titleMedium)
                        LazyColumn {
                            items(predicted.size) { i ->
                                val p = predicted[i]
                                Card(Modifier.fillMaxWidth().padding(vertical=4.dp)) {
                                    Row(Modifier.padding(12.dp), horizontalArrangement=Arrangement.SpaceBetween) {
                                        Text(p.main.joinToString(" ") { "%02d".format(it) } + " - ${"%02d".format(p.special)}")
                                        Text("Score ${p.score.toInt()}", color=Color.Gray)
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Button(onClick={
                            scope.launch {
                                val newHist = withContext(Dispatchers.IO){ LottoScraper.fetchHistory(3) }
                                history = newHist
                                predicted = PredictionEngine.predict(newHist)
                            }
                        }, Modifier.fillMaxWidth()) { Text("Làm mới dự đoán từ lotto-8.com") }
                        Button(onClick={
                            if (predicted.isNotEmpty()) SmsSender.send(this@MainActivity, predicted.take(3))
                        }, Modifier.fillMaxWidth(), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD32F2F))) {
                            Text("BẮN NGAY SMS: ZLP 535 K1 S ... -> 9969")
                        }
                        Text("SMS mẫu: ZLP 535 K1 S 09 14 20 31 35-12", style=MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}