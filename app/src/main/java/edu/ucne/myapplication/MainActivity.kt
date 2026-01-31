package edu.ucne.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.myapplication.presentation.navigation.myapplicacionNavHost
import edu.ucne.myapplication.ui.theme.RegistroEstudianteTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroEstudianteTheme {
                val navHost = rememberNavController()
                myapplicacionNavHost(navHost)
            }
        }
    }
}