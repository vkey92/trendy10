package com.example.trendy10.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.trendy10.R
import com.example.trendy10.common.NoInternetDialog
import com.example.trendy10.common.ShimmerEffect
import com.example.trendy10.common.Utility.checkInternetConnection
import com.example.trendy10.viewmodels.CategoryViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun CategoryScreen(onClick: (category: String, type: String) -> Unit) {
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val categories: State<List<String>> = categoryViewModel.categories.collectAsState()
    val newsCategories: State<List<String>> = categoryViewModel.newsCategories.collectAsState()
    val quatoCategories: State<List<String>> = categoryViewModel.quatoCategories.collectAsState()
    val isRefreshing = remember { mutableStateOf(false) }
    val showNoInternetDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        try {
            if (!checkInternetConnection(context)) {
                showNoInternetDialog.value = true
            }
        } catch (e: Exception) {
            e.printStackTrace() // Log or handle exception
        }
    }

    Scaffold(topBar = {
        com.example.trendy10.MyTopAppBar()
    }) {
        Box(modifier = Modifier.padding(it)) {
            Image(
                painter = painterResource(id = R.drawable.bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (showNoInternetDialog.value) {
                NoInternetDialog(
                    onRetry = {
                        try {
                            if (checkInternetConnection(context)) {
                                showNoInternetDialog.value = false
                            } else {
                                // Optionally show a message or handle retry failure
                            }
                        } catch (e: Exception) {
                            e.printStackTrace() // Log or handle exception
                        }
                    }
                )
            }else{
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing.value),
                    onRefresh = {
                        isRefreshing.value = true
                        // Trigger your data refresh here
                        categoryViewModel.refreshData() // Example refresh function
                        isRefreshing.value = false // Stop the refreshing after the data is loaded
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Text(
                                text = "Trendy10 Tweets ",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.black),
                                modifier = Modifier.padding(18.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Divider( // Horizontal Divider
                                color = colorResource(id = R.color.statusBarColor),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp) // Adds padding around the divider
                            )
                            if (categories.value.isEmpty()) {
                                ShimmerEffect("fix")
                            } else {
                                Box(
                                    modifier = Modifier
                                        .height(210.dp)
                                )
                                {
                                    LazyHorizontalGrid(
                                        rows = GridCells.Fixed(1),
                                        contentPadding = PaddingValues(20.dp),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                    ) {

                                        items(categories.value.distinct()) {
                                            categoryItem("tweets", category = it, onClick)
                                        }
                                    }
                                }
                            }
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Trendy10 News ",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.black),
                                modifier = Modifier.padding(18.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Divider( // Horizontal Divider
                                color = colorResource(id = R.color.statusBarColor),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp) // Adds padding around the divider
                            )
                            if (newsCategories.value.isEmpty()) {
                                ShimmerEffect("fix")
                            } else {
                                Box(
                                    modifier = Modifier
                                        .height(210.dp)
                                )
                                {
                                    LazyHorizontalGrid(
                                        rows = GridCells.Fixed(1),
                                        contentPadding = PaddingValues(20.dp),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                    ) {

                                        items(newsCategories.value.distinct()) {
                                            categoryItem("news", category = it, onClick)
                                        }
                                    }
                                }
                            }
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Trendy10 Quato ",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.black),
                                modifier = Modifier.padding(18.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Divider( // Horizontal Divider
                                color = colorResource(id = R.color.statusBarColor),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp) // Adds padding around the divider
                            )
                            if (quatoCategories.value.isEmpty()) {
                                ShimmerEffect("fix")
                            } else {
                                Box(
                                    modifier = Modifier
                                        .height(210.dp)
                                )
                                {
                                    LazyHorizontalGrid(
                                        rows = GridCells.Fixed(1),
                                        contentPadding = PaddingValues(20.dp),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                    ) {

                                        items(quatoCategories.value.distinct()) {
                                            categoryItem("quotes", category = it, onClick)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }


}

@Composable
fun categoryItem(
    type: String,
    category: String,
    onClick: (category: String, type: String) -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(if (type == "tweets") "tweet.json" else if (type == "news") "news.json" else "quato.json")
    )
    Card(modifier = Modifier
        .clickable { onClick(category, type) }
        .padding(10.dp)
        .size(210.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.cardColor)), // Set your desired color here
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xffeeeeee)),
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter // Centers content within the Box
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(90.dp)) {
                        LottieAnimation(
                            composition = composition,
                            modifier = Modifier.fillMaxSize(),
                            iterations = LottieConstants.IterateForever
                            // Adjust size as needed
                        )
                    }

                    Text(
                        text = category,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


            }
        })

}
