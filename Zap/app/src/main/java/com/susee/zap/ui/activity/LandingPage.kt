package com.susee.zap.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.susee.zap.data.model.LandingItems
import com.susee.zap.utils.ConnectionState
import com.susee.zap.utils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun LandingPage() {
    val viewModel = hiltViewModel<MainViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val landingData by viewModel.landingData.collectAsState()
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (isConnected) viewModel.fetchLandingData()
    }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) },topBar = {
        CenterAlignedTopAppBar(
            title = { Text("ZappKart") }
        )
    }) { innerPadding ->
        if (isLoading) {
            CircularProgressIndicator()
        }
        if (isConnected){
            viewModel.fetchLandingData()
        }else{
            scope.launch {
                snackbarHostState.showSnackbar(
                    "Please check your internet connection!"
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            items(landingData) { item ->
                if (item.sectionType == "banner") {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(item.items) {
                            BannerCard(it, screenWidth)
                        }
                    }
                } else if (item.sectionType == "splitBanner") {
                    LazyRow {
                        items(item.items) {
                            BannerCard(it, screenWidth / 2)
                        }
                    }
                } else {
                    LazyRow {
                        items(item.items) {
                            FreeScrollCard(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BannerCard(item: LandingItems, screenWidth: Dp) {
    Card(
        modifier = Modifier
            .width(screenWidth)
            .padding(4.dp)
            .height(240.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = item.title ?: "",
                    style = TextStyle(color = Color.White, fontSize = 14.sp)
                )
            }
        }
    }
}

@Composable
fun FreeScrollCard(item: LandingItems) {
    Card(
        modifier = Modifier
            .width(124.dp)
            .padding(4.dp)
            .height(124.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = item.title ?: "",
                    style = TextStyle(color = Color.White, fontSize = 14.sp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    LandingPage()
}