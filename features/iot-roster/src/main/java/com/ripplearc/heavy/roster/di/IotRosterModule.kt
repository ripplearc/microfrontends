package com.ripplearc.heavy.roster.di

import com.ripplearc.heavy.iot.roster.di.IotRosterScope
import com.ripplearc.heavy.roster.service.DeviceRosterService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object IotRosterModule {

    @[Provides IotRosterScope]
    fun provideDeviceRosterService(retrofit: Retrofit): DeviceRosterService =
        retrofit.create(DeviceRosterService::class.java)
}