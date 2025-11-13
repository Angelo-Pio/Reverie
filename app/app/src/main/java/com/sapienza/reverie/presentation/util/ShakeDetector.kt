package com.sapienza.reverie.presentation.util

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.pow
import kotlin.math.sqrt

// A simple shake detector.
class ShakeDetector(
    context: Context,
    private val onShake: () -> Unit // Lambda to trigger when a shake is detected
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var lastTime: Long = 0
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f

    companion object {
        // Minimum force required to count as a shake
        private const val SHAKE_THRESHOLD = 800

        // Minimum time between shakes
        private const val SHAKE_TIMEOUT = 500
    }

    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_ACCELEROMETER) return

        val currentTime = System.currentTimeMillis()

        if ((currentTime - lastTime) > SHAKE_TIMEOUT) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val speed =
                sqrt((x - lastX).pow(2) + (y - lastY).pow(2) + (z - lastZ).pow(2)) / (currentTime - lastTime) * 10000

            if (speed > SHAKE_THRESHOLD) {
                lastTime = currentTime
                onShake() // A shake was detected!
            }

            lastX = x
            lastY = y
            lastZ = z
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this implementation
    }
}