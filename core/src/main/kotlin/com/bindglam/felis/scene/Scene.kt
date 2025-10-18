package com.bindglam.felis.scene

import com.bindglam.felis.entity.Entity
import java.util.UUID

open class Scene {
    val entities = hashMapOf<UUID, Entity>()

    fun addEntity(entity: Entity) {
        entities[entity.uuid] = entity
    }
}