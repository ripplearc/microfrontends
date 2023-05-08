package com.ripplearc.heavy.toolbelt.model


data class MotionSensorModel(
	val yaw: Double = 0.0,
	val pitch: Double = 0.0,
	val accuracy: Int = 0,
	val rotationVector: FloatArray = FloatArray(0)
) {

	override fun toString(): String =
		"$yaw ||| $accuracy"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MotionSensorModel

        if (yaw != other.yaw) return false
        if (pitch != other.pitch) return false
        if (accuracy != other.accuracy) return false
        if (!rotationVector.contentEquals(other.rotationVector)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = yaw.hashCode()
        result = 31 * result + pitch.hashCode()
        result = 31 * result + accuracy
        result = 31 * result + rotationVector.contentHashCode()
        return result
    }
}
