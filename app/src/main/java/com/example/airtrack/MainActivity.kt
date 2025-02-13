package com.example.airtrack

import Stop
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airtrack.ui.theme.AirTrackTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirTrackTheme {
                AirTrackApp()
            }
        }
    }
}

// colors used
val DarkBackground = Color(0xFF0F1316)
val LightBackground = Color(0xFF122026)
val DarkText = Color(0xFF3B4E56)
val LightText = Color(0xFFD9EDDF)
val Green = Color(0xFF23E09C)
val Red = Color(0xFFDE5753)

@Preview
@Composable
fun AirTrackApp() {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = LightBackground,
            darkIcons = false
        )
    }

    val stops = remember {
        StopsReader.readStopsFromFile(context)
    }
    var currentStopIndex by remember { mutableIntStateOf(0) }
    var isKm by remember { mutableStateOf(true) }
    val totalDistance = stops.sumOf { it.distanceFromPrevious }
    val distanceCovered = stops.take(currentStopIndex + 1).sumOf { it.distanceFromPrevious }
    val progress = (distanceCovered / totalDistance).toFloat()
    val listState = rememberLazyListState()

    LaunchedEffect(currentStopIndex) {
        listState.animateScrollToItem(currentStopIndex)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBackground)
                .padding(16.dp)
        ) {
            Text(
                text = "Air Track ✈\uFE0F",
                color = LightText,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
                .padding(horizontal = 16.dp)
                .background(DarkText, shape = RoundedCornerShape(8.dp))
        ) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                color = Green,
                trackColor = Color.Transparent
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Distance Covered", color = DarkText, fontSize = 14.sp)
                Text(text = "Distance Left", color = DarkText, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = formatDistance(distanceCovered, isKm), color = LightText, fontSize = 24.sp)
                Text(text = formatDistance(totalDistance - distanceCovered, isKm), color = LightText, fontSize = 24.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = DarkBackground),
            border = BorderStroke(2.dp, Green),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                text = "📍${stops[currentStopIndex].name}",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = LightText,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { isKm = !isKm },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = LightText,
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(1.dp, LightText),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "SWITCH UNITS", color = LightText, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = {
                    if (currentStopIndex < stops.size - 1) {
                        currentStopIndex++
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBackground,
                    contentColor = LightText
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "NEXT STOP", color = LightText, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Journey Ahead",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = LightText,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            thickness = 2.dp,
            color = Green
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(stops) { index, stop ->
                StopItem(stop, index, currentStopIndex, isKm)
            }
        }
    }
}

@Composable
fun StopItem(stop: Stop, index: Int, currentStopIndex: Int, isKm: Boolean) {
    val (textColor, visaBoxColor) = when {
        index < currentStopIndex -> Pair(Red, Red) // visited
        index == currentStopIndex -> Pair(LightText, LightText) // current
        else -> Pair(DarkText, DarkText) // unvisited
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = DarkBackground)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stop.name,
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                if (stop.visaRequired) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = visaBoxColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "VISA",
                            color = DarkBackground,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Distance: ${formatDistance(stop.distanceFromPrevious, isKm)}",
                    color = textColor,
                    fontSize = 16.sp
                )
                Text(
                    text = "Time: ${(stop.timeFromPrevious)} hrs",
                    color = textColor,
                    fontSize = 16.sp
                )
            }
        }
    }
}


object StopsReader {
    fun readStopsFromFile(context: Context): List<Stop> {
        return try {
            context.resources.openRawResource(R.raw.stops)
                .use { inputStream ->
                    BufferedReader(InputStreamReader(inputStream))
                        .useLines { lines ->
                            lines
                                .filter { it.isNotBlank() }
                                .map { line ->
                                    val parts = line.split(",")
                                    Stop(
                                        name = parts[0].trim(),
                                        distanceFromPrevious = parts[1].trim().toDouble(),
                                        visaRequired = parts[2].trim().toBoolean(),
                                        timeFromPrevious = parts[3].trim().toInt()
                                    )
                                }
                                .toList()
                        }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            defaultStops()
        }
    }

    private fun defaultStops() = listOf(
        Stop("Delhi", 0.0, false, 0),
        Stop("Dubai", 2000.0, true, 4),
        Stop("London", 5500.0, false, 7),
        Stop("New York", 5600.0, true, 8),
        Stop("Phuket", 2000.0, false, 3),
        Stop("Beijing", 1000.0, true, 2),
        Stop("Paris", 4500.0, false, 6),
        Stop("Tokyo", 5000.0, false, 7),
        Stop("Quebec", 1500.0, false, 3)
    )
}


fun formatDistance(distance: Double, isKm: Boolean): String {
    return if (isKm) {
        "%.2f km".format(distance)
    } else {
        "%.2f mi".format(distance * 0.621371)
    }
}