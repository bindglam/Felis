package com.bindglam.felis.entity

abstract class LivingEntity(type: EntityType) : Entity(type) {
    companion object {
        const val MAX_HEALTH = 100
    }

    var health: Int = 100
}