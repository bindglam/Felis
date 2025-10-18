package com.bindglam.felis.client.rendering.scene

import org.joml.Vector3f

interface ICamera {
    val position: Vector3f
    val rotation: Vector3f

    fun updateCamera()
}