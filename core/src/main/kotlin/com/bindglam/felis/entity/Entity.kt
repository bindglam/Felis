package com.bindglam.felis.entity

import com.bindglam.felis.utils.IdentifierLike
import org.joml.Vector3f
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

abstract class Entity(val type: EntityType) : IdentifierLike {
    val uuid: UUID = UUID.randomUUID()
    var position = Vector3f()
    var rotation = Vector3f()
    var scale = Vector3f(1f)
}