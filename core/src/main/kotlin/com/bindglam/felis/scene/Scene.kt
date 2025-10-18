package com.bindglam.felis.scene

import com.bindglam.felis.entity.Entity
import java.util.UUID

open class Scene : Tickable {
    val entities = hashMapOf<UUID, Entity>()

    override fun tick() {
        entities.forEach { uuid, entity ->
            entity.tick()
        }
    }

    fun addEntity(entity: Entity) {
        entities[entity.uuid] = entity
    }
}