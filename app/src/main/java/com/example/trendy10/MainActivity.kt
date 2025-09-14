package com.example.trendy10

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trendy10.screens.CategoryScreen
import com.example.trendy10.screens.DetailScreen
import com.example.trendy10.screens.DetailScreen1
import com.example.trendy10.ui.theme.Trendy10Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        setContent {
            Trendy10Theme {
               App()
            }

            }
        }
    }


@SuppressLint("ResourceAsColor")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Trendy10",
                fontSize = 18.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.statusBarColor))
    )
}

@Composable
fun SimpleApp() {
    Scaffold(
        topBar = { MyTopAppBar() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Hello, World!")
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "category") {
        composable(route = "category") {
            CategoryScreen { param1, param2 ->
                navController.navigate("detail/${param1}/${param2}")
            }
        }
        composable(route = "detail/{category}/{type}", arguments = listOf(
            navArgument("category") {
                type = NavType.StringType
            },
            navArgument("type") {
                type = NavType.StringType
            }
        )) {
            DetailScreen{
                navController.navigate("detail1")
            }
        }

        composable(route = "detail1") {
           DetailScreen1()
        }

    }
}






