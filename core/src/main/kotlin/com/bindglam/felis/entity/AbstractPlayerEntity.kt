package com.bindglam.felis.entity

import com.bindglam.felis.utils.Identifier
import com.bindglam.felis.utils.math.OBB
import com.bindglam.felis.utils.math.toQuaternionf
import org.joml.Vector3f

abstract class AbstractPlayerEntity : LivingEntity(EntityType.PLAYER) {
    companion object {
        private val identifier = Identifier.of("felis", "player")
    }

    override val hitbox: OBB
        get() = OBB(Vector3f(0.25f, 0.4f, 0.25f), position.add(0f, 0.4f, 0f, Vector3f()), rotation)

    override fun identifier(): Identifier = identifier
}