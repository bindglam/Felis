package com.bindglam.felis.client.io

object Timer {
    private var lastTime = System.currentTimeMillis()
    private var _deltaTime: Double = 0.0

    val deltaTime: Double
        get() = _deltaTime

    fun update() {
        val currentTime = System.currentTimeMillis()
        _deltaTime = (currentTime - lastTime) / 1000.0
        lastTime = currentTime
    }
}