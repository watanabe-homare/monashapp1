package com.fit2081.homare35281375

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
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
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fit2081.homare35281375.ui.theme.Monashapp1Theme
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Monashapp1Theme {
                val navController: NavHostController = rememberNavController()
                // State to track the currently selected item in the bottom navigation bar.
                var selectedItem by remember { mutableStateOf(0) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        HomeBottomAppBar(navController, selectedItem) { selectedItem = it }
                    }
                ) { innerPadding ->
                    // Use Column to place MyNavHost correctly within Scaffold.
                    Column(modifier = Modifier.padding(innerPadding)) {
                        // Calls the MyNavHost composable to define the navigation graph.
                        HomeNavHost(innerPadding, navController, selectedItem) { selectedItem = it }
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
fun HomeNavHost(innerPadding: PaddingValues,
                navController: NavHostController,
                selectedItem: Int,
                onSelectedItemChange:  (Int) -> Unit) {
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
            InsightsScreen(
                innerPadding,
                context = context,
                navController,
                foodScores,
                onSelectedItemChange
            )
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
fun HomeBottomAppBar(navController: NavHostController,
                     selectedItem: Int,
                     onItemSelected: (Int) -> Unit) {
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
                    onItemSelected(index)
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
        modifier = Modifier.fillMaxSize().padding(8.dp),
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

// this function is for InsightsScreeen
@Composable
fun CategoryRow(category: String, categoryIndex: Int, maxscore: Int = 10){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        val context = LocalContext.current
        val foodScores = searchRowFromCsvById(context, "sample.csv", id = AppState.selectedId.value)
        val categoryScore = if (foodScores[2] == "Male"){foodScores[categoryIndex]} else{foodScores[categoryIndex + 1]}

        Text(category,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(4.2f)
                .padding(horizontal = 4.dp))

        LinearProgressIndicator(
            progress = { (categoryScore.toFloat()) / (maxscore).toFloat() },
            modifier = Modifier
                .weight(4f)
                .padding(horizontal = 2.dp)
        )

        Text(
            "${categoryScore}/${maxscore}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1.8f)
                .padding(horizontal = 2.dp)
        )
    }
}

@Composable
fun InsightsScreen(
    innerPadding: PaddingValues = PaddingValues(),
    context: Context = LocalContext.current,
    navController: NavHostController,
    foodscores: List<String> = emptyList(),
    onSelectedItemChange: (Int) -> Unit
                   ) {
    // value/variables

    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        /**
         * Text - "Insights: Food Score"
         * 13 Rows
         *      text - category name
         *      progress bar
         *      (text - score/10)
         * Space
         * Text - "Total Food Quality Score"
         * progress bar
         * space
         * Row
         *      Button - Share with someone
         *      Button - Improve my diet, navigate to NutriCoach
         */

        val totalFoodScore = if (foodscores[2] == "Male"){foodscores[3]} else{foodscores[4]}

        Text(
            "Insights: Food Scores",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp)
        )

        CategoryRow("Vegetables", 8,5)
        CategoryRow("Fruits", 19,10)
        CategoryRow("Grain & Cereals", 29,5)
        CategoryRow("Whole Grains", 33,5)
        CategoryRow("Meat & Alternatives", 36)
        CategoryRow("Dairy & Alternatives", 40)
        CategoryRow("Sodium", 43)
        CategoryRow("Alcohol", 46, 5)
        CategoryRow("Water", 49, 5)
        CategoryRow("Sugar", 54)
        CategoryRow("Saturated Fat", 57, 5)
        CategoryRow("Unsatuated Fat", 60, 5)
        CategoryRow("Discretionary Foods", 10)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Total Food Quality Score",
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            LinearProgressIndicator(
                progress = { (totalFoodScore.toFloat()) / 100f },
                modifier = Modifier
                    .weight(3.5f)
                    .padding(horizontal = 8.dp)
            )

            Text(
                "${totalFoodScore}/100",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            // Create a Button to trigger the sharing action
            //When clicked, the shareIntent will be started to share the text
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                //create a intent to share the text
                val shareIntent = Intent(ACTION_SEND)
                //set the type of data to share
                shareIntent.type = "text/plain"
                //set the data to share, in this case, the text
                shareIntent.putExtra(Intent.EXTRA_TEXT, "My Total Food Score is ${totalFoodScore}")
                //start the activity to share the text, with a chooser to select the app
                startActivity(context, Intent.createChooser(shareIntent, "share your score via"), null)
            }) {
                Text("Share")
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    navController.navigate("NutriCoach")
                    onSelectedItemChange(2) // index for NutriCoach
                }) {
                Text("Improve my diet!")
            }
        }
    }
}

@Composable
fun NutriCoachScreen(innerPadding: PaddingValues) {

}

@Composable
fun SettingsScreen(innerPadding: PaddingValues) {

}