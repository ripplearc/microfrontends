package com.ripplearc.heavy.toolbelt.constants

interface Directory {
    companion object {
        val ERROR_FILE_EXTENSION
            get() = ".err"

        val LOG_FILE_EXTENSIOIN
            get() = ".log"

        val IMAGE_FILE_EXTENSION
            get() = ".jpg"

        val ZIP_FILE_EXTENSION
            get() = ".zip"

        val SD_CARD_STORAGE_NOT_MOUNTED
            get() = "SDCard Storage Not Found"

        val SAF_PRIMARY_VOLUME_NAME
            get() = "primary"

        val CREDENTIAL_DIRECTORY
            get() = "/credentials"

        val APP_DIRECTORY
            get() = "/Ground_Visual"

        val ACTIVITY_TRACK_FILE
            get() = "activity.txt"

        val LOG_FILE
            get() = "log.txt"

        val PHOTO_LOG_FILE
            get() = "activity.txt"


        val ACCELERATION_TRACK_FILE
            get() = "acceleration.txt"
    }
}