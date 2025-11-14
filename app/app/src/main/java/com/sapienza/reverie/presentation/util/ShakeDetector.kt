package com.sapienza.reverie.presentation.util

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.sqrt

class ShakeDetector(
    context: Context,
    private val onShake: () -> Unit
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var lastShakeTime = 0L

    companion object {
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7f // adjust 2.5–3.0 for sensitivity
        private const val SHAKE_SLOP_TIME_MS = 800L
    }

    fun start() {
        Log.d("ShakeDetector", "Started listening")
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stop() {
        Log.d("ShakeDetector", "Stopped listening")
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: android.hardware.SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_ACCELEROMETER) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            if (now - lastShakeTime > SHAKE_SLOP_TIME_MS) {
                lastShakeTime = now
                Log.d("ShakeDetector", "SHAKE DETECTED! gForce=$gForce")
                onShake()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
