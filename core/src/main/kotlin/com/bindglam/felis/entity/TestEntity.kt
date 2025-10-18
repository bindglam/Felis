package com.bindglam.felis.entity

import com.bindglam.felis.utils.Identifier

class TestEntity : LivingEntity(EntityType.TEST) {
    companion object {
        private val identifier = Identifier.of("felis", "test")
    }

    override fun identifier(): Identifier = identifier
}