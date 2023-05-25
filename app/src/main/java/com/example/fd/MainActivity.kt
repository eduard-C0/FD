package com.example.fd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fd.home.HomeScreen
import com.example.fd.home.HomeViewModel
import com.example.fd.login.LoginScreen
import com.example.fd.navigation.Destinations
import com.example.fd.register.RegisterScreen
import com.example.fd.register.RegisterViewModel
import com.example.fd.ui.theme.FDTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val registerViewModel by viewModels<RegisterViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FDTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(Color.White)
                systemUiController.setNavigationBarColor(Color.White)
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    AppNavGraph()
                }
            }
        }
    }

    @Composable
    fun AppNavGraph(
        navController: NavHostController = rememberNavController(),
        //revert after testing
        startDestination: String = Destinations.LOGIN.name
    ) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.White
            )
        }
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(Destinations.HOME.name) {
                HomeScreen(homeViewModel)
            }
            composable(Destinations.LOGIN.name) {
                LoginScreen(
                    onLoginClicked = { navController.navigate(Destinations.HOME.name) },
                    onCreateAccountClicked = { navController.navigate(Destinations.REGISTER.name) }
                )
            }
            composable(Destinations.REGISTER.name) {
                RegisterScreen(onRegisterButtonClicked = { navController.navigate(Destinations.LOGIN.name) }, registerViewModel )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FDTheme {
        Greeting("Android")
    }
}

