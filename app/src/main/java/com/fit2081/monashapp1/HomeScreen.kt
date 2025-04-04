package com.fit2081.monashapp1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fit2081.monashapp1.ui.theme.Monashapp1Theme
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Monashapp1Theme {
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        HomeBottomAppBar(navController)
                    }
                ) { innerPadding ->
                    // Use Column to place MyNavHost correctly within Scaffold.
                    Column(modifier = Modifier.padding(innerPadding)) {
                        // Calls the MyNavHost composable to define the navigation graph.
                        HomeNavHost(innerPadding, navController)
                    }
                }
            }
        }
    }
}


fun searchRowFromCsvById(context: Context, fileName: String, id: String): List<String> {
    val inputStream = context.assets.open(fileName)
    val reader = BufferedReader(InputStreamReader(inputStream))

    reader.useLines { lines ->
        lines.drop(1).forEach { line ->
            val row = line.split(",")
            // if the id of the row matches the id given
            if (row[1] == id) {
                return row
            }
        }
    }
    // if there's no matching id, raise an exception
    throw NoSuchElementException("No row found with ID: $id")
}


@Composable
fun HomeNavHost(innerPadding: PaddingValues, navController: NavHostController) {
    // values
    val context = LocalContext.current
    val foodScores = searchRowFromCsvById(context, "sample.csv", id = AppState.selectedId.value)
    // NavHost composable to define the navigation graph
    NavHost(
        // Use the provided NavHostController
        navController = navController,
        // Set the starting destination to "home"
        startDestination = "home"
    ) {
        composable("Home") {
            HomeScreenContent(
                innerPadding,
                context = context,
                foodScores
            ) // add Content to avoid using the same name as the class
        }
        composable("Insights") {
            InsightsScreen(innerPadding)
        }
        composable("NutriCoach") {
            NutriCoachScreen(innerPadding)
        }
        composable("Settings") {
            SettingsScreen(innerPadding)
        }

    }
}

@Composable
fun HomeBottomAppBar(navController: NavHostController) {
    // State to track the currently selected item in the bottom navigation bar.
    var selectedItem by remember { mutableStateOf(0) }
    // List of navigation items.
    val items = listOf(
        "Home",
        "Insights",
        "NutriCoach",
        "Settings"
    )
    // NavigationBar composable to define the bottom navigation bar.
    NavigationBar {
        // Iterate through each item in the 'items' list along with its index.
        items.forEachIndexed { index, item ->
            // NavigationBarItem for each item in the list.
            NavigationBarItem(
                // Define the icon based on the item's name.
                icon = {
                    when (item) {

                        "Home" -> Icon(Icons.Rounded.Home, contentDescription = "Home")

                        "Insights" -> Icon(Icons.Rounded.Info, contentDescription = "Insights")

                        "NutriCoach" -> Icon(
                            Icons.Rounded.Person,
                            contentDescription = "NutriCoach"
                        )

                        "Settings" -> Icon(Icons.Rounded.Settings, contentDescription = "Settings")
                    }
                },
                // Display the item's name as the label.
                label = { Text(item) },
                // Determine if this item is currently selected.
                selected = selectedItem == index,
                // Actions to perform when this item is clicked.
                onClick = {
                    // Update the selectedItem state to the current index.
                    selectedItem = index
                    // Navigate to the corresponding screen based on the item's name.
                    navController.navigate(item)

                }
                // Close NavigationBarItem.
            )
        }
    }
}

//@Preview(showBackground = true)
@Composable
// default paddingvalues()　is to use preview
fun HomeScreenContent(
    innerPadding: PaddingValues = PaddingValues(),
    context: Context = LocalContext.current,
    foodscores: List<String> = emptyList()
) {
    // value/variables

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        /**
         * Text - "Hello, [User's ID]"
         * Row
         *      text - "You can change your questionnaire here"
         *      button - "edit" navigate to questionnaire
         * (image)
         * Row
         *      text - "Your Food Quality score"
         *      score display from csv file
         * Space
         * Text - "What is the Food Quality Score?"
         * Explanation text: What the Food Quality Score represents
         */

        Text(
            text = "Hello, ${AppState.selectedId.value}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "You can change your questionnaire here",
                modifier = Modifier
            )
            Button(
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = {
                    context.startActivity(Intent(context, Questionnaire::class.java))
                }) {
                Text("Edit")
            }
        }

        // Add image
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.image_for_home_screen),
            contentDescription = "Food Image",
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Your Food Quality score",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
            if (foodscores[2] == "Male") {
                Text(
                    "${foodscores[3]}/100",
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier
                )
            } else {
                Text(
                    "${foodscores[4]}/100",
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "What is the Food Quality Score?",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.",
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            "This personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
        )

    }
}

@Composable
fun InsightsScreen(innerPadding: PaddingValues) {

}

@Composable
fun NutriCoachScreen(innerPadding: PaddingValues) {

}

@Composable
fun SettingsScreen(innerPadding: PaddingValues) {

}