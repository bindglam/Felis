package com.bindglam.felis.entity

abstract class LivingEntity(type: EntityType) : Entity(type) {
    companion object {
        const val MAX_HEALTH = 100f
    }

    var health: Float = MAX_HEALTH

    override fun destroy() {
        super.destroy()

        health = 0f
    }
}