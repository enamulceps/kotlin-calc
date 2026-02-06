package com.example.calc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var displayText by remember { mutableStateOf("0") }
    var firstNumber by remember { mutableStateOf(0.0) }
    var operator by remember { mutableStateOf("") }
    var isNewNumber by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = displayText, fontSize = 48.sp, modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp))
        
        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", "C", "=", "+")
        )

        buttons.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { symbol ->
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            when {
                                symbol in "0".."9" -> {
                                    if (isNewNumber) { displayText = symbol; isNewNumber = false }
                                    else displayText += symbol
                                }
                                symbol == "C" -> { displayText = "0"; isNewNumber = true }
                                symbol == "=" -> {
                                    val second = displayText.toDoubleOrNull() ?: 0.0
                                    val result = when(operator) {
                                        "+" -> firstNumber + second
                                        "-" -> firstNumber - second
                                        "*" -> firstNumber * second
                                        "/" -> firstNumber / second
                                        else -> second
                                    }
                                    displayText = result.toString()
                                    isNewNumber = true
                                }
                                else -> {
                                    firstNumber = displayText.toDoubleOrNull() ?: 0.0
                                    operator = symbol
                                    isNewNumber = true
                                }
                            }
                        }
                    ) { Text(symbol) }
                }
            }
        }
    }
}
