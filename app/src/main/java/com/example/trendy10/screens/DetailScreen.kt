package com.example.trendy10.screens

import android.annotation.SuppressLint
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trendy10.R
import com.example.trendy10.common.ShimmerEffect
import com.example.trendy10.models.TweetListItem
import com.example.trendy10.viewmodels.DetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(onClick: () -> Unit) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    val tweets :  State<List<TweetListItem>> =  detailViewModel.tweets.collectAsState()
    Scaffold(
        topBar = {
            MyTopAppBar(detailViewModel.type.toUpperCase()+" ("+detailViewModel.category+")")
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Image(
                painter = painterResource(id = R.drawable.bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if(tweets.value.isEmpty()){
                Box(modifier = Modifier.fillMaxSize()) {
                    ShimmerEffect("full")
                }
            }else{
            LazyColumn(content = {
                items(tweets.value) {
                    DetailListItem(tweet = it.value,onClick)
                }
            })
                }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(category : String) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    TopAppBar(
        title = {
            Text(
                text = category,
                fontSize = 18.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = {backDispatcher?.onBackPressed()}) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, // Use a default back arrow icon
                    contentDescription = "Back",
                    tint = Color.White // Customize the color of the icon
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.statusBarColor))
    )
}


@Composable
fun DetailListItem(tweet: String, onClick: () -> Unit) {
    Card(modifier = Modifier
        .clickable { onClick() }
        .padding(10.dp,8.dp,10.dp,8.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.cardColor)), // Set your desired color here
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xffeeeeee)),
        content = {
            Text(
                text = tweet,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    ) 

    
}