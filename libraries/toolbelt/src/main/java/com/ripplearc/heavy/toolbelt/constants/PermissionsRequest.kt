package com.ripplearc.heavy.toolbelt.constants

interface PermissionsRequest {
    companion object {
        val ALL_REQUEST_CODE
            get() = 100

        val CAMERA_REQUEST_CODE
            get() = 101

        val SENSOR_REQUEST_CODE
            get() = 201

        val LOCATION_REQUEST_CODE
            get() = 301

        val STORAGE_REQUEST_CODE
            get() = 401

        val SAF_REQUEST_CODE
            get() = 501
    }
}