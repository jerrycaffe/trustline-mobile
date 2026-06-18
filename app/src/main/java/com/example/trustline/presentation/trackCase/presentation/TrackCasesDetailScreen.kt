package com.example.trustline.presentation.trackCase.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trustline.data.CaseApiModel

private val ActivePurple = Color(0xFF837AEF)
private val InactiveGray = Color(0xFFD8D8D8)

private data class CaseProgressStep(
    val title: String,
    val timestamp: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackCasesDetailScreen(
    caseItem: CaseApiModel?,
    onBackClicked: () -> Unit
) {
    val unitsInvolved = caseItem?.allUnitsInvolved.orEmpty()
    val trackingSteps = buildList {
        add(
            CaseProgressStep(
                title = "Reporting of case",
                timestamp = caseItem?.createdAt?.replace('T', ' ')
            )
        )
        unitsInvolved.forEach { unit ->
            add(CaseProgressStep(title = unit))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Track a Case",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Case No. ${caseItem?.caseNumber}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "${caseItem?.incidentType}",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(trackingSteps) { index, step ->
                    val isCompleted = index == 0
                    ProgressTimelineRow(
                        title = step.title,
                        timestamp = step.timestamp,
                        isCompleted = isCompleted,
                        drawLine = index < trackingSteps.lastIndex,
                        isActiveLine = isCompleted
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(InactiveGray, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProgressTimelineRow(
    title: String,
    timestamp: String?,
    isCompleted: Boolean,
    drawLine: Boolean,
    isActiveLine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(if (isCompleted) ActivePurple else InactiveGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            if (drawLine) {
                Canvas(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(top = 2.dp)
                        .size(2.dp, 68.dp)
                ) {
                    drawLine(
                        color = if (isActiveLine) ActivePurple else InactiveGray,
                        start = Offset(size.width / 2f, 0f),
                        end = Offset(size.width / 2f, size.height),
                        strokeWidth = 6f
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(14.dp))

        Column(modifier = Modifier.padding(top = 0.dp)) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = if (isCompleted) ActivePurple else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isCompleted) FontWeight.Medium else FontWeight.Normal
            )
            if (!timestamp.isNullOrBlank()) {
                Text(
                    text = timestamp,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

