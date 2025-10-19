package com.bindglam.felis.utils.math

import org.joml.Quaternionf
import org.joml.Vector3f

val FRONT: Vector3f
    get() = Vector3f(0f, 0f, 1f)
val UP: Vector3f
    get() = Vector3f(0f, 1f, 0f)
val RIGHT: Vector3f
    get() = Vector3f(-1f, 0f, 0f)

fun Vector3f.toQuaternionf(): Quaternionf = Quaternionf()
    .rotationZYX(deg2rad(z), deg2rad(y), deg2rad(x))

fun Quaternionf.toEulerAngles(): Vector3f = getEulerAnglesYXZ(Vector3f())

fun RGBColor.toVector3f(): Vector3f = Vector3f(r().toFloat()/255f, g().toFloat()/255f, b().toFloat()/255f)