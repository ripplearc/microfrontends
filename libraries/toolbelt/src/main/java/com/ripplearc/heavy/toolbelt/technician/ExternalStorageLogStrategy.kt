package com.ripplearc.heavy.toolbelt.technician

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.orhanobut.logger.LogStrategy
import java.io.File
import java.io.FileWriter
import java.io.IOException


/**
 * Abstract class that takes care of background threading the file log operation on Android.
 * implementing classes are free to directly perform I/O operations there.
 *
 * Writes all logs to the disk with TXT format.
 */
class ExternalStorageLogStrategy(private val handler: Handler) : LogStrategy {

    override fun log(level: Int, tag: String?, message: String) {
        handler.sendMessage(handler.obtainMessage(level, message))
    }

    class WriteHandler(
        looper: Looper,
        private val folder: () -> String,
        private val maxFileSize: Int
    ) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            val content = msg.obj as String
            var fileWriter: FileWriter? = null
            val logFile = getLogFile(folder(), "logs")
            try {
                FileWriter(logFile, true).let {
                    fileWriter = it
                    writeLog(it, content)
                    it.flush()
                    it.close()
                }
            } catch (e: IOException) {
                fileWriter?.run {
                    try {
                        flush()
                        close()
                    } catch (e1: IOException) { /* fail silently */
                    }
                }
            }
        }

        /**
         * This is always called on a single background thread.
         * Implementing classes must ONLY write to the fileWriter and nothing more.
         * The abstract class takes care of everything else including close the stream and catching IOException
         *
         * @param fileWriter an instance of FileWriter already initialised to the correct file
         */
        private fun writeLog(fileWriter: FileWriter, content: String) {
            fileWriter.append(content)
        }

        private fun getLogFile(folderName: String, fileName: String): File {
            val folder = File(folderName)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            var newFileCount = 0
            var newFile: File
            var existingFile: File? = null
            newFile =
                File(folder, String.format("%s_%s.txt", fileName, newFileCount))
            while (newFile.exists()) {
                existingFile = newFile
                newFileCount++
                newFile =
                    File(folder, String.format("%s_%s.txt", fileName, newFileCount))
            }
            return if (existingFile != null) {
                if (existingFile.length() >= maxFileSize) {
                    newFile
                } else existingFile
            } else newFile
        }
    }

}
