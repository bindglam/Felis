package com.bindglam.felis.entity

import com.bindglam.felis.utils.Identifier
import com.bindglam.felis.utils.math.OBB
import com.bindglam.felis.utils.math.RGBAColor
import org.joml.Vector3f

class LightEntity : Entity(EntityType.LIGHT) {
    companion object {
        private val identifier = Identifier.defaultNamespace("light")
    }

    override val hitbox: OBB
        get() = OBB(Vector3f(0.05f, 0.05f, 0.05f), position, rotation)

    var color = RGBAColor.of(255, 255, 255, 255)

    override fun identifier(): Identifier = identifier
}