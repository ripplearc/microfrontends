package com.ripplearc.heavy.toolbelt.contract

import com.ripplearc.heavy.toolbelt.model.MotionSensorModel
import io.reactivex.Flowable


interface Motion {
    val rotationFlowable: Flowable<MotionSensorModel>
}