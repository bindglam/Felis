package com.bindglam.felis.utils.math

import org.joml.Quaternionf
import org.joml.Vector3f

fun Vector3f.toQuaternionf(): Quaternionf = Quaternionf()
    .rotateZ(Math.toRadians(y.toDouble()).toFloat())

fun RGBColor.toVector3f(): Vector3f = Vector3f(r().toFloat()/255f, g().toFloat()/255f, b().toFloat()/255f)