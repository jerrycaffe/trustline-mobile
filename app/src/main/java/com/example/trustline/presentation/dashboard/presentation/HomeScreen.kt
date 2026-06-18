package com.example.trustline.presentation.dashboard.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.trustline.MainViewModel
import com.example.trustline.R
import com.example.trustline.navigation.Routes
import com.example.trustline.navigation.ScreenConfiguration
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun HomeScreen(navController: NavHostController, globalViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                    text = if (globalViewModel.isUserAuthenticated) "Welcome," else "Hello,",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = if (globalViewModel.isUserAuthenticated) "You are in a safe corner, let us know how to help"
                    else "Thank you for your courage", fontSize = 14.sp
                )
            }
            Row {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface
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
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                color = MaterialTheme.colorScheme.onBackground
            )
        )


        Spacer(modifier = Modifier.height(8.dp))

        // 3. Grid Menu
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillWeight(1f)
        ) {
            item {
                DashboardCard(
                    "Report a Case",
                    colorResource(R.color.light_secondary),
                    R.drawable.icon_park_twotone_report,
                    handleNavigation = {
                        if (globalViewModel.isUserAuthenticated) {
                            navController.navigate(Routes.AUTH_REPORT_CASE.name)
                        } else {
                            globalViewModel.rememberPendingPostAuthRoute(Routes.AUTH_REPORT_CASE.name)
                            navController.navigate(Routes.REPORT_CASE_INFO.name)
                        }
                    })
            }
            item {
                DashboardCard(
                    "Seek Help",
                    Color(0xFFFFB5D4),
                    R.drawable.hugeicons_wellness,
                    handleNavigation = {
                        if (!globalViewModel.isUserAuthenticated) {
                            globalViewModel.rememberPendingPostAuthRoute(Routes.SEEK_HELP_INFO.name)
                        }
                        navController.navigate(Routes.SEEK_HELP_INFO.name)
                    })
            }
            item {
                DashboardCard(
                    "Track a Case", Color(0xFF87CBF9), R.drawable.gis_route, handleNavigation = {
                        if (globalViewModel.isUserAuthenticated) {
                            navController.navigate(Routes.CASES.name)
                        } else {
                            globalViewModel.rememberPendingPostAuthRoute(Routes.CASES.name)
                            navController.navigate(Routes.TRACK_CASE_INFO.name)
                        }
                    })
            }
            item {
                DashboardCard(
                    "GBV Resources",
                    Color(0xFFFEDB4E),
                    R.drawable.grommet_icons_resources,
                    handleNavigation = { navController.navigate(Routes.GBV_RESOURCES.name) })
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, color: Color, icon: Int, handleNavigation: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { handleNavigation() },
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = Color.Unspecified
            )
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun CustomBottomNavigation(navController: NavHostController, globalViewModel: MainViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isAuthenticated = globalViewModel.isUserAuthenticated

    val items = listOf(
        BottomNavItem(
            "Home", R.drawable.home_icon, ScreenConfiguration.UnauthenticatedScreen.Home.route
        ), BottomNavItem(
            "Cases",
            R.drawable.bar_icon,
            if (isAuthenticated) ScreenConfiguration.UnauthenticatedScreen.Cases.route
            else ScreenConfiguration.UnauthenticatedScreen.LoginAware.route
        ), BottomNavItem(
            "Zen",
            R.drawable.hugeicons_wellness,
            if (isAuthenticated) ScreenConfiguration.UnauthenticatedScreen.Zen.route
            else ScreenConfiguration.UnauthenticatedScreen.LoginAware.route
        ), BottomNavItem(
            "Chat",
            R.drawable.chat_icon,
            if (isAuthenticated) ScreenConfiguration.UnauthenticatedScreen.Chat.route
            else ScreenConfiguration.UnauthenticatedScreen.LoginAware.route
        ), BottomNavItem(
            "Profile",
            R.drawable.profile_icon,
            if (isAuthenticated) ScreenConfiguration.UnauthenticatedScreen.Profile.route
            else ScreenConfiguration.UnauthenticatedScreen.LoginAware.route
        )
    )

    val selectedIndex = items.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)
    val indicatorOffset by animateFloatAsState(
        targetValue = selectedIndex.toFloat(), label = "indicator"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            // Animated indicator
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val tabWidth = maxWidth / items.size
                Box(
                    modifier = Modifier
                        .padding(start = tabWidth * indicatorOffset)
                        .width(tabWidth)
                        .height(4.dp)
                        .background(Color(0xFF837AEF))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    NavigationMenu(
                        title = item.title,
                        icon = item.icon,
                        active = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        })
                }
            }
        }
    }
}

data class BottomNavItem(val title: String, val icon: Int, val route: String)

private fun Modifier.fillWeight(weight: Float): Modifier = this.then(Modifier.fillMaxHeight(weight))

@Composable
fun NavigationMenu(title: String, icon: Int, active: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            modifier = Modifier.size(23.dp),
            contentDescription = null,
            tint = if (active) Color(0xFF837AEF) else MaterialTheme.colorScheme.onSurface
        )
        Text(
            title,
            color = if (active) Color(0xFF837AEF) else MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp
        )
    }
}
