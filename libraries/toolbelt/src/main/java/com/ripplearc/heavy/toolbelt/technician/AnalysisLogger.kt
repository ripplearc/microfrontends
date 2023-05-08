package com.ripplearc.heavy.toolbelt.technician

import android.content.Context
import android.util.Log
import com.ripplearc.heavy.toolbelt.constants.DateFormat
import com.ripplearc.heavy.toolbelt.constants.DateFormat.Companion.DOCUMENT_DATE_HOUR_FORMAT
import com.ripplearc.heavy.toolbelt.constants.Directory
import com.ripplearc.heavy.toolbelt.time.DateProvider
import com.ripplearc.heavy.toolbelt.time.toFormatString
import dagger.Reusable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import javax.inject.Inject

/**
 * Logger logs information for debugging purpose.
 */
@Reusable
class AnalysisLogger @Inject constructor(
    private val dateProvider: DateProvider,
    private val context: Context
) : Directory, DateFormat {

    /**
     * Log the `data` to the file within the app directory.
     * @param fileName The fileName dose not need to contain the file path.
     * @param data The data to be appended to the file.
     * @param csvSchema If the file is in CSV format, it needs to specify the schema of the table.
     * @return `true` if the data is saved successfully to the file.
     */
    fun log(
        fileName: String,
        data: String,
        stampTime: Boolean = false,
        csvSchema: String? = null
    ): Boolean =
        with(context.getExternalFilesDir(null).toString()) {
            val file = File(this)
            if (!file.exists())
                file.mkdirs()
            val writingData = if (stampTime) dateProvider.date.time.toString() + ";" + data else data
            saveToTimeStampFile(this, fileName, writingData, csvSchema)
        }

    private fun saveToTimeStampFile(
        directory: String,
        suffix: String,
        data: String,
        csvSchema: String?
    ): Boolean {
        val timestamp = dateProvider.date.toFormatString(DOCUMENT_DATE_HOUR_FORMAT)
        val filename = timestamp + suffix

        return saveToFile(directory, filename, data, csvSchema)
    }

    private fun saveToFile(
        directory: String,
        filename: String,
        data: String,
        csvSchema: String?
    ): Boolean {
        val out: File
        var outStreamWriter: OutputStreamWriter?
        var outStream: FileOutputStream? = null

        try {
            out = File(File(directory), filename)

            var dataToWrite = initializeFileIfNotExists(data, out, csvSchema)

            outStream = FileOutputStream(out, true)
            outStreamWriter = OutputStreamWriter(outStream)

            outStreamWriter.append(dataToWrite + "\n")
            outStreamWriter.flush()
        } catch (e: IOException) {
            Log.e("Logger", "[saveToFile]" + e.localizedMessage)
            return false
        } finally {
            try {
                outStream?.close()
            } catch (e: Exception) {
                Log.e("Logger", "Error in closing file: " + e.localizedMessage)
            }

        }
        return true
    }

    private fun initializeFileIfNotExists(data: String, out: File, csvSchema: String?): String {
        var dataToWrite = data

        if (!out.exists()) {
            out.parentFile?.mkdirs()
            out.createNewFile()
            csvSchema?.let {
                dataToWrite = it + "\n" + data
            }
        }
        return dataToWrite
    }
}
