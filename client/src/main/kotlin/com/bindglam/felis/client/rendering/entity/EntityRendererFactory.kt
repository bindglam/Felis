package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.entity.EntityType

interface EntityRendererFactory {
    fun create(entity: Entity, shader: Shader): EntityRenderer

    fun type(): EntityType


    companion object {
        fun builder(): Builder = Builder()
    }

    class Builder {
        private lateinit var createSupplier: (Entity, Shader) -> EntityRenderer
        private lateinit var type: EntityType

        internal constructor()

        fun create(supplier: (Entity, Shader) -> EntityRenderer): Builder {
            this.createSupplier = supplier
            return this
        }

        fun type(type: EntityType): Builder {
            this.type = type
            return this
        }

        fun build(): EntityRendererFactory {
            return object : EntityRendererFactory {
                override fun create(entity: Entity, shader: Shader): EntityRenderer = createSupplier(entity, shader)

                override fun type(): EntityType = type
            }
        }
    }
}