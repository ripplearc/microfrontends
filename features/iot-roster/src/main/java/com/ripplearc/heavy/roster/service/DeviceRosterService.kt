package com.ripplearc.heavy.roster.service

import com.ripplearc.heavy.data.DeviceModel
import io.reactivex.Single
import retrofit2.http.GET

interface DeviceRosterService {
    @GET("roster")
    fun getDeviceRoster(): Single<List<DeviceModel>>
}