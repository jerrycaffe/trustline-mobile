package com.example.trustline.presentation.report.presentation.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trustline.R
import com.example.trustline.data.SubmitCasePayload
import com.example.trustline.navigation.Routes
import com.example.trustline.presentation.common.DatePickerInput
import com.example.trustline.presentation.common.DropDownSelectInput
import com.example.trustline.presentation.common.InputTextBox
import com.example.trustline.presentation.common.TextAreaInput
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthReportCaseScreen(navController: NavHostController) {
    val viewModel: AuthReportCaseViewModel = viewModel(factory = AuthReportCaseViewModel.Factory)
    val viewState = viewModel.state
    val context = LocalContext.current
    var incidentType by remember { mutableStateOf("") }
    var selectedIncidentTypeId by remember { mutableStateOf("") }
    var occurrenceDate by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileType by remember { mutableStateOf<FileType?>(null) }
    var selectedFileName by remember { mutableStateOf<String?>(null) }
    val isSubmitEnabled = incidentType.isNotBlank() &&
            selectedIncidentTypeId.isNotBlank() &&
            occurrenceDate.isNotBlank() &&
            location.isNotBlank() &&
            description.isNotBlank() &&
            selectedFileUri != null &&
            !viewState.isSubmitting

    val successMessage = viewState.submitSuccessMessage
    if (successMessage != null) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Success") },
            text = { Text(successMessage) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.consumeSubmitSuccess()
                    navController.navigate(Routes.HOME.name) {
                        popUpTo(Routes.HOME.name) { inclusive = false }
                        launchSingleTop = true
                    }
                }) {
                    Text("Continue")
                }
            }
        )
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult

        val fileSize = getFileSizeInBytes(context, uri)
        if (fileSize == null || fileSize > MAX_FILE_SIZE_BYTES) {
            Toast.makeText(context, "File size must not be more than 2MB", Toast.LENGTH_LONG).show()
            selectedFileUri = null
            selectedFileType = null
            selectedFileName = null
            return@rememberLauncherForActivityResult
        }

        val mimeType = context.contentResolver.getType(uri).orEmpty()
        val detectedType = when {
            mimeType.startsWith("image/") -> FileType.IMAGE
            mimeType.startsWith("video/") -> FileType.VIDEO
            mimeType.startsWith("audio/") -> FileType.AUDIO
            else -> null
        }

        if (detectedType == null) {
            Toast.makeText(
                context,
                "Only image, video, or audio files are allowed",
                Toast.LENGTH_LONG
            )
                .show()
            selectedFileUri = null
            selectedFileType = null
            selectedFileName = null
            return@rememberLauncherForActivityResult
        }

        selectedFileUri = uri
        selectedFileType = detectedType
        selectedFileName = getFileDisplayName(context, uri)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Report a case",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.HOME.name) {
                            popUpTo(Routes.HOME.name) { inclusive = false }
                        }
                    }) {
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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Please note that giving false and misleading information is punishable by law",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                DropDownSelectInput(
                    selectedOption = incidentType,
                    options = viewState.incidentTypes.map { it.name },
                    onOptionSelected = {
                        incidentType = it
                        selectedIncidentTypeId = viewState.incidentTypes
                            .firstOrNull { type -> type.name == it }
                            ?.id
                            ?.toString()
                            .orEmpty()
                    },
                    placeholder = "Select incident type"
                )

                if (viewState.isLoadingIncidentTypes) {
                    Text(
                        text = "Loading incident types...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                if (viewState.incidentTypeError != null) {
                    Text(
                        text = viewState.incidentTypeError,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                if (viewState.submitError != null) {
                    Text(
                        text = viewState.submitError,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                DatePickerInput(
                    placeholder = "Date of occurrence",
                    selectedDate = occurrenceDate,
                    onDateSelected = { occurrenceDate = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                InputTextBox(
                    value = location,
                    placeHolder = "Location of occurrence",
                    onValueChanged = { location = it }
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextAreaInput(
                    value = description,
                    placeHolder = "Description (May include info on witnesses or the culprit)",
                    onValueChanged = { description = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedCard(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clickable(
                            onClick = {
                                filePickerLauncher.launch("*/*")
                            }
                        ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFD7D7D7))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (selectedFileUri != null && selectedFileType != null) {
                            when (selectedFileType) {
                                FileType.IMAGE -> {
                                    val imageBitmap = remember(selectedFileUri) {
                                        selectedFileUri?.let { loadBitmapFromUri(context, it) }
                                    }
                                    if (imageBitmap != null) {
                                        Image(
                                            bitmap = imageBitmap.asImageBitmap(),
                                            contentDescription = "Selected image preview",
                                            modifier = Modifier.size(82.dp)
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_upload_cloud),
                                            contentDescription = "Selected image",
                                            tint = Color.Unspecified,
                                            modifier = Modifier.size(52.dp)
                                        )
                                    }
                                }

                                FileType.VIDEO -> {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_video_file),
                                        contentDescription = "Selected video",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(52.dp)
                                    )
                                }

                                FileType.AUDIO -> {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_audio_file),
                                        contentDescription = "Selected audio",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(52.dp)
                                    )
                                }

                                else -> Unit
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = selectedFileName ?: "File selected",
                                color = Color(0xFF4D4D4D),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tap to replace",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_upload_cloud),
                                contentDescription = "Upload files",
                                tint = Color.Unspecified,
                                modifier = Modifier.height(52.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Tap to select files",
                                    color = Color(0xFF767676),
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = ".jpg, .mp4, .png, .mp3 (max 2MB)",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    viewModel.consumeSubmitError()

                    val incidentDate = formatDateForApi(occurrenceDate)
                    if (incidentDate == null) {
                        Toast.makeText(
                            context,
                            "Please select a valid incident date",
                            Toast.LENGTH_LONG
                        ).show()
                        return@Button
                    }

                    val casePayload = SubmitCasePayload(
                        location = location.trim(),
                        dateOfIncident = incidentDate,
                        incidentTypeId = selectedIncidentTypeId,
                        description = description.trim()
                    )

                    val caseBody = Gson().toJson(casePayload)
                        .toRequestBody("application/json; charset=utf-8".toMediaType())

                    val evidenceUri = selectedFileUri
                    if (evidenceUri == null) {
                        Toast.makeText(context, "Please select an evidence file", Toast.LENGTH_LONG)
                            .show()
                        return@Button
                    }

                    val filePart = buildEvidencePart(
                        context = context,
                        fileUri = evidenceUri,
                        fileName = selectedFileName
                    )

                    if (filePart == null) {
                        Toast.makeText(context, "Unable to read selected file", Toast.LENGTH_LONG)
                            .show()
                        return@Button
                    }

                    viewModel.submitCase(
                        caseBody = caseBody,
                        files = listOf(filePart)
                    )
                },
                enabled = isSubmitEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9A94E8),
                    disabledContainerColor = Color(0xFFD2CFFA)
                )
            ) {
                if (viewState.isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Submit",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private fun formatDateForApi(dateText: String): String? {
    val regex = Regex("""^(\d{1,2})\s*-\s*(\d{1,2})\s*-\s*(\d{4})$""")
    val match = regex.find(dateText.trim()) ?: return null

    val day = match.groupValues[1].toIntOrNull() ?: return null
    val month = match.groupValues[2].toIntOrNull() ?: return null
    val year = match.groupValues[3].toIntOrNull() ?: return null

    if (month !in 1..12 || day !in 1..31) return null
    return String.format(Locale.US, "%04d-%02d-%02d", year, month, day)
}

private fun buildEvidencePart(
    context: android.content.Context,
    fileUri: Uri,
    fileName: String?
): MultipartBody.Part? {
    val bytes =
        context.contentResolver.openInputStream(fileUri)?.use { it.readBytes() } ?: return null
    val mimeType = context.contentResolver.getType(fileUri) ?: "application/octet-stream"
    val requestBody = bytes.toRequestBody(mimeType.toMediaTypeOrNull())
    val resolvedFileName = fileName ?: "evidence_${System.currentTimeMillis()}"
    return MultipartBody.Part.createFormData("files", resolvedFileName, requestBody)
}

private const val MAX_FILE_SIZE_BYTES = 2L * 1024L * 1024L

private enum class FileType {
    IMAGE,
    VIDEO,
    AUDIO
}

private fun getFileSizeInBytes(context: android.content.Context, uri: Uri): Long? {
    context.contentResolver.query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use {
        val sizeColumn = it.getColumnIndex(OpenableColumns.SIZE)
        if (sizeColumn >= 0 && it.moveToFirst()) {
            return it.getLong(sizeColumn)
        }
    }
    return null
}

private fun getFileDisplayName(context: android.content.Context, uri: Uri): String? {
    context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
        ?.use {
            val nameColumn = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameColumn >= 0 && it.moveToFirst()) {
                return it.getString(nameColumn)
            }
        }
    return null
}

private fun loadBitmapFromUri(context: android.content.Context, uri: Uri): Bitmap? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    } catch (_: Exception) {
        null
    }
}
