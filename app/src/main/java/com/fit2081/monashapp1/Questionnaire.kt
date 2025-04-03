package com.fit2081.monashapp1

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fit2081.monashapp1.ui.theme.Monashapp1Theme


class Questionnaire : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Monashapp1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuestionnaireContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class) // to use dropdownmenu
@Composable
fun QuestionnaireContent(modifier: Modifier = Modifier) {
    //    values
    val context = LocalContext.current
    val sectionTitleSize = 16.sp
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
    // they are for dropdown menu
    val personaList = listOf(
        "Health Devotee",
        "Mindful Eater",
        "Wellness Striver",
        "Balance Seeker",
        "Health Procrastinator",
        "Food Carefree"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedPersona by remember { mutableStateOf("") }
    // they are for timepickers
    // when val is used with remember, .value has to be used to get the value
    val mealTime = remember { mutableStateOf("") }
    val sleepTime = remember { mutableStateOf("") }
    val wakeTime = remember { mutableStateOf("") }


// Column layout for the LoginscreenContent
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
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
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tick all the food categories you can eat",
            fontSize = sectionTitleSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )

        // Checkboxes with labels
        // Row for the first three checkboxes and labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked1, onCheckedChange = { checked1 = it })
                Text("Fruits")
            }
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked2, onCheckedChange = { checked2 = it })
                Text(
                    "Vegetables", fontSize = 14.sp
                )
            }
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked3, onCheckedChange = { checked3 = it })
                Text("Grains")
            }
        }
        // Row for the second three checkboxes and labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked4, onCheckedChange = { checked4 = it })
                Text("Red Meat")
            }
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked5, onCheckedChange = { checked5 = it })
                Text("Seafood")
            }
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked6, onCheckedChange = { checked6 = it })
                Text("Poultry")
            }
        }
        // Row for the last three checkboxes and labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked7, onCheckedChange = { checked7 = it })
                Text("Fish")
            }
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked8, onCheckedChange = { checked8 = it })
                Text("Eggs")
            }
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked9, onCheckedChange = { checked9 = it })
                Text("Nuts/Seeds")
            }
        }

        Text(
            text = "Your Persona",
            fontSize = sectionTitleSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "People can be broadly classified into 6 different types based on their eating preferences. Click on each button below to find out the different types, and select the type that best fits you!",
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // to use modifier.weight(), this function must be defined within row
            @Composable
            fun ShowButtonAndModal(
                title: String, // button's name and title of the modal
                imagePath: Int, // should be id of the image inside the modal e.g. R.drawable.persona_1
                description: String, // description inside the modal
            ) {
                // values
                // State to control the visibility of the AlertDialog.
                var showDialog by remember { mutableStateOf(false) }


                // Button that, when clicked, sets the 'showDialog'
                // state to true, which opens the dialog.
                // the dialog is hidden initially
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp),
                    onClick = { showDialog = true }) {
                    Text(
                        text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp
                    )
                }
                if (showDialog) {
                    Dialog(
                        //switch the visibility of the dialog to false when the user dismisses it
                        onDismissRequest = { showDialog = false }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            // column layout inside the modal
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                // Add image
                                androidx.compose.foundation.Image(
                                    painter = painterResource(imagePath),
                                    contentDescription = title,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Text(
                                    text = description,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = { showDialog = false }) {
                                    Text("Dismiss")
                                }
                            }
                        }
                    }
                }
            }
            ShowButtonAndModal(
                title = "Health Devotee",
                imagePath = R.drawable.persona_1,
                description = "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy."
            )
            ShowButtonAndModal(
                title = "Mindful Eater",
                imagePath = R.drawable.persona_2,
                description = "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media."
            )
            ShowButtonAndModal(
                title = "Wellness Striver",
                imagePath = R.drawable.persona_3,
                description = "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go."
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            @Composable
            fun ShowButtonAndModal(
                title: String, // button's name and title of the modal
                imagePath: Int, // should be id of the image inside the modal e.g. R.drawable.persona_1
                description: String, // description inside the modal
                buttonFontSize: TextUnit // change font size according to length of title and layout nicely
            ) {
                // values
                // State to control the visibility of the AlertDialog.
                var showDialog by remember { mutableStateOf(false) }


                // Button that, when clicked, sets the 'showDialog'
                // state to true, which opens the dialog.
                // the dialog is hidden initially
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp),
                    onClick = { showDialog = true }) {
                    Text(
                        text = title, fontWeight = FontWeight.Bold, fontSize = buttonFontSize
                    )
                }
                if (showDialog) {
                    Dialog(
                        //switch the visibility of the dialog to false when the user dismisses it
                        onDismissRequest = { showDialog = false }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            // column layout inside the modal
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                // Add image
                                androidx.compose.foundation.Image(
                                    painter = painterResource(imagePath),
                                    contentDescription = title,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Text(
                                    text = description,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = { showDialog = false }) {
                                    Text("Dismiss")
                                }
                            }
                        }
                    }
                }
            }
            ShowButtonAndModal(
                title = "Balance Seeker",
                imagePath = R.drawable.persona_4,
                description = "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
                13.sp
            )
            ShowButtonAndModal(
                title = "Health Procrastinator",
                imagePath = R.drawable.persona_5,
                description = "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
                11.sp
            )
            ShowButtonAndModal(
                title = "Food Carefree",
                imagePath = R.drawable.persona_6,
                description = "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.",
                14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Which persona best fits you?",
            fontSize = sectionTitleSize,
            fontWeight = FontWeight.Bold
        )

        ExposedDropdownMenuBox(
            expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = selectedPersona,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select your persona") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable) // required for proper positioning
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false }) {
                personaList.forEach { item ->
                    DropdownMenuItem(text = { Text(item) }, onClick = {
                        selectedPersona = item
                        expanded = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "What time do you do these activities",
            fontSize = sectionTitleSize,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Eat biggest meal: ${mealTime.value}", modifier = Modifier
            )
            Button(
                modifier = Modifier.padding(horizontal = 4.dp), onClick = {
                    TimePickerDialog(
                        context, { _, hour: Int, minute: Int ->
                            mealTime.value = String.format(
                                "%02d:%02d", hour, minute
                            ) // to format them like 04:25
                        }, 12, 0, false
                    ).show() // without show(), the dialog doesn't show up
                }) {
                Text("Select time")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Go to bed: ${sleepTime.value}", modifier = Modifier
            )
            Button(
                modifier = Modifier.padding(horizontal = 4.dp), onClick = {
                    TimePickerDialog(
                        context, { _, hour: Int, minute: Int ->
                            sleepTime.value = String.format(
                                "%02d:%02d", hour, minute
                            ) // to format them like 04:25
                        }, 12, 0, false
                    ).show() // without show(), the dialog doesn't show up
                }) {
                Text("Select time")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Wake up: ${wakeTime.value}", modifier = Modifier
            )
            Button(
                modifier = Modifier.padding(horizontal = 4.dp), onClick = {
                    TimePickerDialog(
                        context, { _, hour: Int, minute: Int ->
                            wakeTime.value = String.format(
                                "%02d:%02d", hour, minute
                            ) // to format them like 04:25
                        }, 12, 0, false
                    ).show() // without show(), the dialog doesn't show up
                }) {
                Text("Select time")
            }
        }

        Spacer(Modifier.height(30.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            onClick = {
                // save values in shared preferences
                val sharedPref =
                    context.getSharedPreferences("questionnaire_sp", Context.MODE_PRIVATE).edit()
                sharedPref.putBoolean("checked1", checked1)
                sharedPref.putBoolean("checked2", checked2)
                sharedPref.putBoolean("checked3", checked3)
                sharedPref.putBoolean("checked4", checked4)
                sharedPref.putBoolean("checked5", checked5)
                sharedPref.putBoolean("checked6", checked6)
                sharedPref.putBoolean("checked7", checked7)
                sharedPref.putBoolean("checked8", checked8)
                sharedPref.putBoolean("checked9", checked9)
                sharedPref.putString("selectedPersona", selectedPersona)
                sharedPref.putString("mealtime", mealTime.value)
                sharedPref.putString("sleeptime", sleepTime.value)
                sharedPref.putString("waketime", wakeTime.value)
                sharedPref.apply()

                // proceed to the home screen
                context.startActivity(Intent(context, HomeScreen::class.java))
            }) {
            Text(
                text = "Save Values and proceed", fontSize = 18.sp
            )
        }
    }
}


