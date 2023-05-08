package com.ripplearc.heavy.toolbelt.constants

interface DateFormat {
    companion object {
        val DOCUMENT_DATE_TIME_FORMAT
            get() = "yyyy-MM-dd-HH/mm/ss"
        val DOCUMENT_DATE_HOUR_FORMAT
            get() = "yyyy-MM-dd-HH/"
        val DOCUMENT_DATE_FORMAT
            get() = "yyyy-MM-dd/"
        val DOCUMENT_TIME_FORMAT
            get() = "HH:mm:ss"
        val DISPLAY_DATE_TIME_FORMAT
            get() = "yyyy/MM/dd HH:mm:ss"
    }
}