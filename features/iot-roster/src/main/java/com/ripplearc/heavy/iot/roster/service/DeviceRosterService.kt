package com.ripplearc.heavy.roster.service

import com.ripplearc.heavy.common.data.DeviceModel
import io.reactivex.Observable
import retrofit2.http.GET

interface DeviceRosterService {
    @GET("roster")
    fun getDeviceRoster(): Observable<List<DeviceModel>>
}
