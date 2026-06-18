package com.example.trustline.presentation.gbvResources.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.trustline.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GbvResourceDetailsScreen(onBackClicked: () -> Unit, globalViewModel: MainViewModel) {
    val gbvResource = globalViewModel.consumeGbvResourceState()
    val parsedSections = remember(gbvResource?.contents) {
        gbvResource?.contents?.let { parseGbvContentSections(it) }.orEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = gbvResource?.name ?: "GBV Resource Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }, navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                if (parsedSections.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "No content available for this resource.",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(
                        items = parsedSections,
                        key = { it.id }
                    ) { section ->
                        GbvContentAccordionItem(section = section)
                    }
                }
            }
        }
    }
}

@Composable
private fun GbvContentAccordionItem(section: GbvContentSection) {
    var expanded by rememberSaveable(section.id) { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .padding(end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = section.heading,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Collapse section" else "Expand section",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    text = section.body,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private data class GbvContentSection(
    val id: String,
    val heading: String,
    val body: String,
    val level: Int
)

private sealed interface HtmlToken {
    val order: Int
}

private data class HeadingToken(
    override val order: Int,
    val level: Int,
    val text: String
) : HtmlToken

private data class ParagraphToken(
    override val order: Int,
    val text: String
) : HtmlToken

private fun parseGbvContentSections(contents: String): List<GbvContentSection> {
    val tokens = mutableListOf<HtmlToken>()
    val headingRegex = Regex(
        pattern = """(?is)<blockquote>\s*<h([1-6])[^>]*>(.*?)</h\1>\s*</blockquote>|<h([1-6])[^>]*>(.*?)</h\3>"""
    )
    val paragraphRegex = Regex(pattern = """(?is)<p[^>]*>(.*?)</p>""")

    headingRegex.findAll(contents).forEach { match ->
        val level = match.groups[1]?.value?.toIntOrNull() ?: match.groups[3]?.value?.toIntOrNull()
        ?: return@forEach
        val rawHeading = match.groups[2]?.value ?: match.groups[4]?.value ?: return@forEach
        tokens.add(
            HeadingToken(
                order = match.range.first,
                level = level,
                text = cleanHtmlText(rawHeading)
            )
        )
    }

    paragraphRegex.findAll(contents).forEach { match ->
        val rawParagraph = match.groups[1]?.value ?: return@forEach
        tokens.add(
            ParagraphToken(
                order = match.range.first,
                text = cleanHtmlText(rawParagraph)
            )
        )
    }

    val sortedTokens = tokens.sortedBy { it.order }
    val sections = mutableListOf<GbvContentSection>()
    var currentHeading: HeadingToken? = null
    val currentBody = mutableListOf<String>()

    fun flushSection() {
        val heading = currentHeading ?: return
        val body = currentBody.joinToString(separator = "\n\n").trim()
        sections.add(
            GbvContentSection(
                id = "${heading.order}-${sections.size}",
                heading = heading.text,
                body = body,
                level = heading.level
            )
        )
        currentBody.clear()
    }

    sortedTokens.forEach { token ->
        when (token) {
            is HeadingToken -> {
                flushSection()
                currentHeading = token
            }

            is ParagraphToken -> {
                if (token.text.isNotBlank()) {
                    if (currentHeading == null) {
                        currentHeading = HeadingToken(
                            order = token.order,
                            level = 2,
                            text = "Details"
                        )
                    }
                    currentBody.add(token.text)
                }
            }
        }
    }

    flushSection()
    return sections.filter { it.heading.isNotBlank() && it.body.isNotBlank() }
}

private fun cleanHtmlText(html: String): String {
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        .toString()
        .replace(Regex("\\s+"), " ")
        .trim()
}
