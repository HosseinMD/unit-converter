package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField


import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.unitconverter.ui.theme.UnitConverterTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                // A surface container using the background color
                // from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // my compose goes here (real app in phone)
                    UnitConverter()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverterTheme {
        // A surface container using the background color
        // from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // my compose goes here (Preview)
            UnitConverter()
        }
    }
}

@Composable
fun UnitConverter(){
    // state part
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    // next two variable is for selected item from DropdownMenu
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    // iExpanded is inputExpanded oExpanded is outputExpanded for DropdownMenu
    // next two variable is for checking dropdown is opened or closed
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    val iConversionFactor = remember { mutableStateOf(1.00) }
    val oConversionFactor = remember { mutableStateOf(1.00) }

    // making custom text style
    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 35.sp,
        color = Color.Blue
    )


    fun convertUnits(){
        // ?:  elvis operator --> its act like smart short if statement
        // in this case if input value is correct its go otherwise (should be null) because its null elvis operator set ot 0.0 and our app doesn't crash
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        // in next line last number if 100 result type will be Int if 100.0 result type will be Double
        val result = (inputValueDouble * iConversionFactor.value * 100 / oConversionFactor.value).roundToInt() / 100.0
        outputValue = result.toString()

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Here all the UI elements will be stacked below each other
        Text("Unit Converter", style =  MaterialTheme.typography.headlineLarge ) //also we can make custom style and pass it like this  -->  style = customTextStyle

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            {
            inputValue = it
            convertUnits()
            // Here goes what should happen, when the value of our OutlinedTextField changes
        },
            label = { Text("Enter Value") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            // This 3 lines show how to set Toast --> we can use like in button onClick
            //val context = LocalContext.current
            //Toast.makeText(context, "hi", Toast.LENGTH_LONG).show()
            //Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()

            // Here all the UI elements will be stacked next each other

            // Input Box
            Box() {
                // Input Button
                Button(onClick = {iExpanded = true},
                        // modifier = Modifier.width(130.dp) // --> if uncomment this line then button size will be fixed and when change the item its size never changed
                    ) {
                    Text(" $inputUnit ")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }

                DropdownMenu(expanded = iExpanded, onDismissRequest = {iExpanded = false}) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Centimeters"
                            iConversionFactor.value = 0.01
                            convertUnits()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Meters"
                            iConversionFactor.value = 1.0
                            convertUnits()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Feet"
                            iConversionFactor.value = 0.3048
                            convertUnits()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Milimeters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Milimeters"
                            iConversionFactor.value = 0.001
                            convertUnits()
                        }
                    )
                }

            }

            Spacer(modifier = Modifier.width(16.dp))
            // Output Box
            Box() {
                // Output Button
                Button(onClick = {oExpanded = true}) {
                    Text("${outputUnit}")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }

                DropdownMenu(expanded = oExpanded, onDismissRequest = {oExpanded = false}) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Centimeters"
                            oConversionFactor.value = 0.01
                            convertUnits()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Meters"
                            oConversionFactor.value = 1.0
                            convertUnits()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Feet"
                            oConversionFactor.value = 0.3048
                            convertUnits()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Milimeters") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Milimeters"
                            oConversionFactor.value = 0.001
                            convertUnits()
                        }
                    )
                }

            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        // Result Text
        Text("Result: ${outputValue} ${outputUnit}",
            style = MaterialTheme.typography.headlineMedium
            )

    }
}

// ToDO : working on font 