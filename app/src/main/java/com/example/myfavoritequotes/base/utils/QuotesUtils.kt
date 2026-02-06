package com.example.myfavoritequotes.base.utils

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.myfavoritequotes.base.module.Quotes
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object QuotesUtils {
    fun permissionsGranted(context: Context, permissions: Array<String>): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun exporting(context: Context, quotes: List<Quotes>) {
        try {
            val gson = Gson()
            val jsonData = gson.toJson(quotes)

            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "my_quotes.json")
                put(MediaStore.Downloads.MIME_TYPE, "application/json")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.write(jsonData.toByteArray())
                }
                Log.d("DEBUG", "File exported successfully to: $uri")
            } ?: run {
                Log.e("DEBUG", "Failed to create file URI")
                // Fallback for older Android if needed, but MediaStore is preferred
            }
        } catch (e: Exception) {
            Log.e("DEBUG", "Export failed: ${e.message}", e)
            // Optionally show a failure toast here, but since this is called from Fragment, handle in caller
        }
    }
}