package com.ripplearc.heavy.toolbelt.technician

import com.orhanobut.logger.FormatStrategy
import com.ripplearc.heavy.toolbelt.constants.DateFormat
import com.ripplearc.heavy.toolbelt.time.DateProvider
import com.ripplearc.heavy.toolbelt.time.LocaleProvider
import dagger.Reusable
import java.text.SimpleDateFormat
import javax.inject.Inject

/**
 * Translate the message to be timestamped and save as txt file.
 */
@Reusable
class TxtFormatStrategy @Inject constructor(
    private val dateProvider: DateProvider,
    private val localeProvider: LocaleProvider,
    private val externalStorageLogStrategy: ExternalStorageLogStrategy
) : FormatStrategy {
    private val newLine = System.getProperty("line.separator") ?: "\n"
    private val newLineReplacement = " <br> "
    private val separator = ","
    var mainTag: String? = null

    override fun log(priority: Int, subTag: String?, message: String) {
        StringBuilder().also {

            it.append(newLine)
            SimpleDateFormat(DateFormat.DOCUMENT_TIME_FORMAT, localeProvider.locale)
                .format(dateProvider.date)
                .apply {
                    it.append(this)
                }

            val tag = formatTag(subTag)
            it.append(separator)
            it.append(tag)

            if (message.contains(newLine)) {
                message.replace(
                    newLine.toRegex(),
                    newLineReplacement
                )
            } else {
                message
            }.apply {
                it.append(separator)
                it.append(this)
                it.append(newLine)
            }

            externalStorageLogStrategy.log(priority, tag, it.toString())
        }
    }

    private fun formatTag(subTag: String?): String? =
        subTag?.let {
            if (it.isNotEmpty() && it.equals(mainTag)) {
                "$mainTag - $it"
            } else {
                subTag
            }
        } ?: subTag

}