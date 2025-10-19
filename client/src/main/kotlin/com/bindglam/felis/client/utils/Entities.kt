package com.bindglam.felis.client.utils

import com.bindglam.felis.client.rendering.scene.ICamera
import com.bindglam.felis.entity.Entity
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f

fun Entity.createTransformationMatrix(): Matrix4f {
    return Matrix4f().identity()
        .translate(position.negate(Vector3f()))
        .rotate(rotation)
        .scale(scale)
}

fun ICamera.createViewMatrix(): Matrix4f = Matrix4f().identity()
    .rotate(cameraRotation.invert(Quaternionf()))
    .translate(cameraPosition)