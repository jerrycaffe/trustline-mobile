package com.example.trustline.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.trustline.MainViewModel
import com.example.trustline.presentation.dashboard.presentation.CustomBottomNavigation

@Composable
fun MainTabWrapper(
    navController: NavHostController,
    globalViewModel: MainViewModel,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        bottomBar = { CustomBottomNavigation(navController, globalViewModel) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content(Modifier)
        }
    }
}
