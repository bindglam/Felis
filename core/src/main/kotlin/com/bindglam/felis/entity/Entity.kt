package com.bindglam.felis.entity

import com.bindglam.felis.scene.Tickable
import com.bindglam.felis.utils.Destroyable
import com.bindglam.felis.utils.IdentifierLike
import org.joml.Vector3f
import java.util.UUID

abstract class Entity(val type: EntityType) : IdentifierLike, Destroyable, Tickable {
    val uuid: UUID = UUID.randomUUID()
    var position = Vector3f()
    var rotation = Vector3f()
    var scale = Vector3f(1f)

    override fun tick() {
    }

    override fun destroy() {
    }
}