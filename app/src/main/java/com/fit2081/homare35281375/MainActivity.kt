// This activity is for welcome screen

package com.fit2081.homare35281375

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.homare35281375.ui.theme.Monashapp1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Monashapp1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WelcomeScreeen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun WelcomeScreeen(modifier: Modifier = Modifier) {
//  these two are for ClickableText
    val context = LocalContext.current

    val annotatedText = buildAnnotatedString {
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("The Clinic Website Here")
        }
        pop()
    }
//    Column layout for the welcome screen
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /**
         * Big Space
         * Text - NutriTrack (big texts like a title)
         * Space
         * image - NutriTrack logo
         * Space
         * Text - Disclaimer Text
         * ClickableText - External URL to Monash Nutrition Clinic
         * Space
         * Button - Login (navigate to the login screen)
         * Space
         * Text - Designed by Homare Watanabe (35281375)
         */

        Text(
            text = "NutriTrack",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add image
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.nutritrack_logo),
            contentDescription = "NutriTrack Logo",
            modifier = Modifier.size(200.dp) // Adjust size as needed
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "This app provides general health and nutrition information for educational purposes only. It is not intended as medical advice, diagnosis, or treatment. Always consult a qualified healthcare professional before making any changes to your diet, exercise, or health regimen." +
                    "Use this app at your own risk." +
                    "If you’d like to an Accredited Practicing Dietitian (APD), please visit the Monash Nutrition/Dietetics Clinic (discounted rates for students):",
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )

//      ClickableText is deprecated but other options caused an error so I stick with this one
        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                        context.startActivity(browserIntent)
                    }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                context.startActivity(Intent(context, Loginscreen::class.java))
            }
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Designed by Homare Watanabe (35281375)",
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}