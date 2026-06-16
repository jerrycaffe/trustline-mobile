package com.example.trustline.presentation.trackCase.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trustline.MainViewModel
import com.example.trustline.R
import com.example.trustline.data.CaseApiModel
import com.example.trustline.navigation.Routes
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

private val Purple = Color(0xFF837AEF)
private val LightGrayBg = Color(0xFFF5F5F5)

@Composable
fun CasesScreen(
    navController: NavHostController,
    globalViewModel: MainViewModel
) {
    val viewModel: CasesViewModel = viewModel(factory = CasesViewModel.Factory)
    val state = viewModel.state
    val listState = rememberLazyListState()

    // Trigger next-page when near end of list
    LaunchedEffect(
        listState,
        state.filteredCases.size,
        state.isLastPage,
        state.isLoading,
        state.isLoadingMore
    ) {
        snapshotFlow {
            val info = listState.layoutInfo
            val total = info.totalItemsCount
            val lastVisible = info.visibleItemsInfo.lastOrNull()?.index ?: -1
            total > 0 && lastVisible >= total - 3
        }
            .map { near -> near && !state.isLastPage && !state.isLoading && !state.isLoadingMore }
            .distinctUntilChanged()
            .filter { it }
            .collect { viewModel.loadNextPage() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ── Header ──────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cases",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
        }

        // ── Search bar ───────────────────────────────────────────────────────
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = state.searchQuery,
                    onValueChange = viewModel::onSearchChanged,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 15.sp, color = Color.Black),
                    cursorBrush = SolidColor(Purple),
                    decorationBox = { inner ->
                        if (state.searchQuery.isEmpty()) {
                            Text("Search", color = Color(0xFF9E9E9E), fontSize = 15.sp)
                        }
                        inner()
                    }
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF9E9E9E),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // ── Status filter tabs ───────────────────────────────────────────────
        CaseFilterTabs(
            selected = state.statusFilter,
            onSelect = viewModel::onFilterChanged
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Content ──────────────────────────────────────────────────────────
        when {
            state.isLoading && !state.hasLoadedOnce -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Purple)
                }
            }

            state.error != null && state.allCases.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error,
                        color = Color(0xFFD32F2F),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            state.hasLoadedOnce && state.filteredCases.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (state.allCases.isEmpty())
                            "You have not reported any cases yet."
                        else
                            "No cases match your search or filter.",
                        color = Color(0xFF4B4B4B),
                        fontSize = 15.sp
                    )
                }
            }

            else -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.filteredCases, key = { it.id }) { caseItem ->
                        CaseCard(
                            caseItem = caseItem,
                            onDetailsCLicked = {
                                globalViewModel.rememberSelectedCaseForTracking(caseItem)
                                navController.navigate(Routes.TRACK_CASES_DESCRIPTION.name)
                            },
                            onTrackClicked = {
                                globalViewModel.rememberSelectedCaseForTracking(caseItem)
                                navController.navigate(Routes.TRACK_CASES_DETAIL.name)
                            }
                        )
                    }

                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(28.dp),
                                    color = Purple
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CaseFilterTabs(
    selected: CaseStatusFilter,
    onSelect: (CaseStatusFilter) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFEEEEEE),
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CaseStatusFilter.entries.forEach { filter ->
                val isSelected = selected == filter
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(11.dp),
                    color = if (isSelected) Color.White else Color.Transparent,
                    shadowElevation = if (isSelected) 2.dp else 0.dp,
                    onClick = { onSelect(filter) }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = filter.name.lowercase().replaceFirstChar { it.uppercase() },
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) Color.Black else Color(0xFF666666)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CaseCard(
    caseItem: CaseApiModel,
    onDetailsCLicked: () -> Unit = {},
    onTrackClicked: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top row: icon + case info + status badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Document icon
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFFF0F0F0), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_case_document),
                        contentDescription = null,
                        tint = Color(0xFF555555),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Case number + incident type
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (caseItem.caseNumber != null) "Case No. ${caseItem.caseNumber}"
                        else "Case #${caseItem.id.takeLast(6).uppercase()}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF111111)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = caseItem.incidentType ?: "—",
                        fontSize = 13.sp,
                        color = Color(0xFF777777)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Status badge
                StatusBadge(status = caseItem.status)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onTrackClicked,
                    modifier = Modifier
                        .weight(1f)
                        .height(33.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Purple)
                ) {
                    Text(
                        "Track", color = Color.White,

                        fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedButton(
                    onClick = onDetailsCLicked,
                    modifier = Modifier
                        .weight(1f)
                        .height(33.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCCCCCC))
                ) {
                    Text(
                        "Details", color = Color(0xFF111111),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: String?) {
    val normalized = status?.uppercase() ?: "UNKNOWN"
    val (bgColor, textColor, label) = when (normalized) {
        "PENDING" -> Triple(Color(0xFFFFF3CD), Color(0xFFB87800), "Pending")
        "RESOLVED" -> Triple(Color(0xFFD4F5E9), Color(0xFF1A7A50), "Resolved")
        "IN_PROGRESS", "INPROGRESS" -> Triple(Color(0xFFD6EAFF), Color(0xFF1A5FA8), "In Progress")
        else -> Triple(
            Color(0xFFF0F0F0),
            Color(0xFF555555),
            normalized.lowercase().replaceFirstChar { it.uppercase() })
    }
    Surface(
        shape = RoundedCornerShape(5.dp),
        color = bgColor
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

