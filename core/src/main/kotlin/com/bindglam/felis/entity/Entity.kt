package com.bindglam.felis.entity

import com.bindglam.felis.scene.Tickable
import com.bindglam.felis.utils.Destroyable
import com.bindglam.felis.utils.IdentifierLike
import com.bindglam.felis.utils.math.OBB
import org.joml.Quaternionf
import org.joml.Vector3f
import java.util.UUID

abstract class Entity(val type: EntityType) : IdentifierLike, Destroyable, Tickable {
    val uuid: UUID = UUID.randomUUID()
    var position = Vector3f()
    var rotation = Quaternionf()
    var scale = Vector3f(1f)

    abstract val hitbox: OBB

    override fun tick() {
    }

    override fun destroy() {
    }
}