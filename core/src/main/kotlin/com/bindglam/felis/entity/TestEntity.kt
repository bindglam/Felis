package com.bindglam.felis.entity

import com.bindglam.felis.utils.Identifier
import com.bindglam.felis.utils.math.OBB
import com.bindglam.felis.utils.math.toQuaternionf
import org.joml.Vector3f

class TestEntity : LivingEntity(EntityType.TEST) {
    companion object {
        private val identifier = Identifier.of("felis", "test")
    }

    override val hitbox: OBB
        get() = OBB(Vector3f(0.5f, 0.4f, 0.5f), position.add(0f, 0.4f, 0f, Vector3f()), rotation)

    override fun identifier(): Identifier = identifier
}