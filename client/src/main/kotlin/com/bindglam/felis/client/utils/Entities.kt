package com.bindglam.felis.client.utils

import com.bindglam.felis.client.rendering.scene.ICamera
import com.bindglam.felis.entity.Entity
import org.joml.Matrix4f

fun Entity.createTransformationMatrix(): Matrix4f {
    return Matrix4f().identity()
        .translate(-position.x, -position.y, -position.z)
        .rotateX(Math.toRadians(rotation.x.toDouble()).toFloat())
        .rotateY(Math.toRadians(rotation.y.toDouble()).toFloat())
        .rotateZ(Math.toRadians(rotation.z.toDouble()).toFloat())
        .scale(scale)
}

fun ICamera.createViewMatrix(): Matrix4f = Matrix4f().identity()
    .rotateX(Math.toRadians(-rotation.x.toDouble()).toFloat())
    .rotateY(Math.toRadians(-rotation.y.toDouble()).toFloat())
    .rotateZ(Math.toRadians(-rotation.z.toDouble()).toFloat())
    .translate(position.x, position.y, position.z)