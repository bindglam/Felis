package com.bindglam.felis.client.rendering.scene

import org.joml.Quaternionf
import org.joml.Vector3f

interface ICamera {
    var cameraPosition: Vector3f
    var cameraRotation: Quaternionf

    fun updateCamera()
}