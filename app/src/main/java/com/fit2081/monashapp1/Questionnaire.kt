package com.fit2081.monashapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.monashapp1.ui.theme.Monashapp1Theme

class Questionnaire : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Monashapp1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun QuestionnaireContent(modifier: Modifier = Modifier) {
//    values
    val context = LocalContext.current
    // Mutable state variables to track checkbox states
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }
    var checked3 by remember { mutableStateOf(false) }
    var checked4 by remember { mutableStateOf(false) }
    var checked5 by remember { mutableStateOf(false) }
    var checked6 by remember { mutableStateOf(false) }
    var checked7 by remember { mutableStateOf(false) }
    var checked8 by remember { mutableStateOf(false) }
    var checked9 by remember { mutableStateOf(false) }


// Column layout for the LoginscreenContent
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /**
         * Text - Food Intake Questionnaire (biggest text)
         * Space
         * Text - Tick all the food categories you can eat
         * 9 checkboxes
         * Text - Your Persona (big text like title)
         * Text - “People can be broadly classified into 6 different types based on their eating preferences. Click on each button below to find out the different types, and select the type that best fits you!”
         * 6 buttons to present modals for the personas
         * Dropdown - select your persona
         * Space (relatively big)
         * Text - Timings (big text like title)
         * TimePicker - What time do you eat your biggest meal?
         * TimePicker - What time do you go to sleep?
         * TimePicker - What time do you wake up?
         * Space
         * Button - save data locally in SharedPreferences
         */

        Text(
            text = "Food Intake Questionnaire",
            fontSize = 48.sp,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tick all the food categories you can eat",
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )

        // Checkboxes with labels
        // Row for the first three checkboxes and labels
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked1, onCheckedChange = { checked1 = it })
            Text("Fruits")
            Checkbox(checked = checked2, onCheckedChange = { checked2 = it })
            Text("Vegetables")
            Checkbox(checked = checked3, onCheckedChange = { checked3 = it })
            Text("Grains")
        }
        // Row for the second three checkboxes and labels
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked4, onCheckedChange = { checked4 = it })
            Text("Red Meat")
            Checkbox(checked = checked5, onCheckedChange = { checked5 = it })
            Text("Seafood")
            Checkbox(checked = checked6, onCheckedChange = { checked6 = it })
            Text("Q1: The Earth is Flat.")
        }
        // Row for the last three checkboxes and labels
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked7, onCheckedChange = { checked7 = it })
            Text("Q3: The speed of sound is 1,236 km/h.")
            Checkbox(checked = checked8, onCheckedChange = { checked8 = it })
            Text("Q1: The Earth is Flat.")
            Checkbox(checked = checked9, onCheckedChange = { checked9 = it })
            Text("Q1: The Earth is Flat.")
        }


    }