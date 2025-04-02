package com.fit2081.monashapp1

//For smooth navigation set phoneNumber val should be modified after

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.monashapp1.ui.theme.Monashapp1Theme
import java.io.BufferedReader
import java.io.InputStreamReader

class Loginscreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Monashapp1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginscreenContent(
//                        context = this@Loginscreen,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


fun getColumnFromCSV(context: Context, fileName: String, columnIndex: Int): List<String> {
    val columnData = mutableListOf<String>()
    val inputStream = context.assets.open(fileName)
    val reader = BufferedReader(InputStreamReader(inputStream))

    reader.useLines { lines ->
        lines.drop(1).forEach { line ->
            val tokens = line.split(",")
            if (tokens.size > columnIndex) {
                columnData.add(tokens[columnIndex])
            }
        }
    }

    return columnData
}

// check if the phone number entered valid or not
// return True if valid, otherwise, False
fun isNumberValid(phoneNumber: String, validList: List<String>): Boolean {
    validList.forEach { item ->
        if(item == phoneNumber){
            return true
        }
    }
//    after checking all the items in the valid list
    return false
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFromCSV(context: Context, fileName: String, columnIndex: Int) {
    val options = getColumnFromCSV(context, fileName, columnIndex)
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select your ID") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable) // required for proper positioning
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOption = selectionOption
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun LoginscreenContent(modifier: Modifier = Modifier) {
//    values
    val context = LocalContext.current
//    var phoneNumber by remember { mutableStateOf("") }
    val phoneNumber = "61436567330"
    val validPhoneNumberList = getColumnFromCSV(context, "sample.csv", 0)

// Column layout for the LoginscreenContent
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /**
         * Text - Log in
         * Space
         * Dropdown - User ID (loaded from CSV file)
         * Text field: Phone number (must match the one in the CSV)
         * Space
         * Text - This app is only for pre-registered users. Please enter a valid ID and phone number to continue.
         * Space
         * Button - Navigate to the Questionnaire screen
         */

        Text(
            text = "Log in",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownFromCSV(context, "sample.csv", 1)

//        // Text field for entering the location.
//        TextField(
//            value = phoneNumber,
//            // Update phone number when the text changes.
//            onValueChange = { phoneNumber = it },
//            label = { Text("Enter your number") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp)
//        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This app is only for pre-registered users. Please enter a valid ID and phone number to continue.",
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if(isNumberValid(phoneNumber, validPhoneNumberList)){
                    // if correct show a toast message and navigate to the questionnaire
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                    context.startActivity(Intent(context, Questionnaire::class.java))
                }
                else{
                    // if incorrect show a toast message saying phone number invalid
                    Toast.makeText(context, "Phone number invalid", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Continue")
        }
    }
}