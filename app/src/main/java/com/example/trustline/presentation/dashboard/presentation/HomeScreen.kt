package com.example.trustline.presentation.dashboard.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.trustline.MainViewModel
import com.example.trustline.R
import com.example.trustline.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun HomeScreen(navController: NavHostController, globalViewModel: MainViewModel) {
    Scaffold(
        containerColor = Color(0xFFFBFBFB),
        bottomBar = { CustomBottomNavigation() }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 2. Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello,",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Thank you for your courage", fontSize = 14.sp
                    )
                }
                Row {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Outlined.Search, contentDescription = "Search", tint = Color.Black
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.Black
                        )
                    }
                }
            }
            // 1. Hero Banner Image Slider (Infinite Loop)
            val bannerImages = listOf(
                R.drawable.stop_gbv_4,
                R.drawable.stop_gbv_2,
                R.drawable.stop_gbv_3,
                R.drawable.stop_gender_based_violence
            )
            val virtualCount = Int.MAX_VALUE
            val pagerState = rememberPagerState(
                initialPage = virtualCount / 2 - (virtualCount / 2 % bannerImages.size),
                pageCount = { virtualCount })

            LaunchedEffect(Unit) {
                while (true) {
                    yield()
                    delay(4000)
                    if (!pagerState.isScrollInProgress) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
            ) {
                HorizontalPager(
                    state = pagerState, modifier = Modifier.fillMaxSize()
                ) { page ->
                    val actualPage = page % bannerImages.size
                    Image(
                        painter = painterResource(id = bannerImages[actualPage]),
                        contentDescription = "Banner Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Quick actions",
                fontSize = 22.sp,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold))
                )
            )


            Spacer(modifier = Modifier.height(18.dp))

            // 3. Grid Menu
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillWeight(1f)
            ) {
                item {
                    DashboardCard(
                        "Report a Case",
                        colorResource(R.color.light_secondary),
                        R.drawable.icon_park_twotone_report,
                        handleNavigation = {
                            navController.navigate(Routes.REPORT_CASE_INFO.name)
                        })
                }
                item {
                    DashboardCard(
                        "Seek Help",
                        Color(0xFFFFB5D4),
                        R.drawable.hugeicons_wellness,
                        handleNavigation = { navController.navigate(Routes.SEEK_HELP_INFO.name) })
                }
                item {
                    DashboardCard(
                        "Track a Case",
                        Color(0xFF87CBF9),
                        R.drawable.gis_route,
                        handleNavigation = { navController.navigate(Routes.TRACK_CASE_INFO.name) })
                }
                item {
                    DashboardCard(
                        "GBV Resources",
                        Color(0xFFFEDB4E),
                        R.drawable.grommet_icons_resources,
                        handleNavigation = {})
                }
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, bgColor: Color, icon: Int, handleNavigation: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(
                onClick = { handleNavigation() })
            .fillMaxWidth()
            .aspectRatio(1.1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    contentDescription = title,
                    painter = painterResource(icon),
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.BottomEnd)
                        .padding(end = 20.dp),
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun CustomBottomNavigation() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp), color = Color.White
    ) {
        Column {
            // Active Tab Indicator line
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.20f)
                    .padding(start = 4.dp)
                    .height(4.dp)
                    .background(Color(0xFF837AEF))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home
                NavigationMenu("Home", R.drawable.home_icon, true)
                // Case
                NavigationMenu("Cases", R.drawable.bar_icon, false)
                //Zen
                NavigationMenu("Zen", R.drawable.hugeicons_wellness, false)
//                Chat
                NavigationMenu("Chat", R.drawable.chat_icon, false)
                // Profile
                NavigationMenu("Profile", R.drawable.profile_icon, false)
            }
        }
    }
}

private fun Modifier.fillWeight(weight: Float): Modifier = this.then(Modifier.fillMaxHeight(weight))

@Composable
fun NavigationMenu(title: String, icon: Int, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(icon),
            modifier = Modifier.size(23.dp),
            contentDescription = null,
            tint = if (active) Color(0xFF837AEF) else Color.Black
        )
        Text(
            title, color = if (active) Color(0xFF837AEF) else Color.Black, fontSize = 12.sp
        )
    }
}