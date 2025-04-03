package com.fit2081.monashapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fit2081.monashapp1.ui.theme.Monashapp1Theme

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


@Composable
fun HomeNavHost(innerPadding: PaddingValues, navController: NavHostController) {
    // NavHost composable to define the navigation graph
    NavHost(
        // Use the provided NavHostController
        navController = navController,
        // Set the starting destination to "home"
        startDestination = "home"
    ) {
        composable("Home") {
            HomeScreenContent(innerPadding) // add Content to avoid using the same name as the class
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

@Composable
fun HomeScreenContent(innerPadding: PaddingValues){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
        ){
        /**
         * Text - "Hello, [User's ID]"
         * Row
         *      text - "You can change your questionnaire here"
         *      button - "edit" navigate to questionnaire
         * (image)
         * Row
         *      text - "Your Food Quality score"
         *      score display from shared preferences
         * Space
         * Text - "What is the Food Quality Score?"
         * Explanation text: What the Food Quality Score represents
         */
    }
}

@Composable
fun InsightsScreen(innerPadding: PaddingValues){

}

@Composable
fun NutriCoachScreen(innerPadding: PaddingValues){

}

@Composable
fun SettingsScreen(innerPadding: PaddingValues){

}