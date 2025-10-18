package com.bindglam.felis.utils.math

import org.joml.Intersectionf
import org.joml.Quaternionf
import org.joml.Vector3f

data class OBB(val halfExtents: Vector3f, val center: Vector3f, val rotation: Quaternionf) {
    val vertices: List<Float>
        get() = listOf(
            Vector3f(-halfExtents.x, -halfExtents.y, -halfExtents.z),
            Vector3f(halfExtents.x, -halfExtents.y, -halfExtents.z),
            Vector3f(halfExtents.x, halfExtents.y, -halfExtents.z),
            Vector3f(-halfExtents.x, halfExtents.y, -halfExtents.z),
            Vector3f(-halfExtents.x, -halfExtents.y, halfExtents.z),
            Vector3f(halfExtents.x, -halfExtents.y, halfExtents.z),
            Vector3f(halfExtents.x, halfExtents.y, halfExtents.z),
            Vector3f(-halfExtents.x, halfExtents.y, halfExtents.z)
        ).stream().collect({ arrayListOf<Float>() }, { list, vector -> list.add(vector.x); list.add(vector.y); list.add(vector.z) }, { list1, list2 -> list1.addAll(list2) })

    val axes: List<Vector3f>
        get() = listOf(
            Vector3f(1f, 0f, 0f),
            Vector3f(0f, 1f, 0f),
            Vector3f(0f, 0f, 1f)
        ).map { rotation.transform(it) }

    fun isColliding(other: OBB): Boolean {
        val axes1 = axes
        val axes2 = other.axes

        return Intersectionf.testObOb(center, axes1[0], axes1[1], axes1[2], halfExtents,
            other.center, axes2[0], axes2[1], axes2[2], other.halfExtents)
    }
}