package com.bindglam.felis.entity

import com.bindglam.felis.utils.Identifier

abstract class AbstractPlayerEntity : LivingEntity(EntityType.PLAYER) {
    companion object {
        private val identifier = Identifier.of("felis", "player")
    }

    override fun identifier(): Identifier = identifier
}