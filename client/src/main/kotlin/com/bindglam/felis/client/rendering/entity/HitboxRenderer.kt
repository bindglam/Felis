package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.FelisClient
import com.bindglam.felis.client.manager.SceneManager
import com.bindglam.felis.client.rendering.model.EBO
import com.bindglam.felis.client.rendering.model.VAO
import com.bindglam.felis.client.rendering.model.VBO
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.utils.math.RGBColor
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL11
import kotlin.concurrent.atomics.ExperimentalAtomicApi

class HitboxRenderer(private val entity: Entity, private val shader: Shader) : EntityRenderer {
    companion object {
        private val INDICES = intArrayOf(
            0, 1,   1, 2,   2, 3,   3, 0,
            4, 5,   5, 6,   6, 7,   7, 4,
            0, 4,   1, 5,   2, 6,   3, 7
        )
    }

    var vao = VAO().apply { bind() }
    var vbo = VBO(entity.hitbox.vertices.toFloatArray())
    var ebo = EBO(INDICES)

    fun generateModel() {
        vao.destroy()
        vbo.destroy()
        ebo.destroy()

        vao = VAO().apply { bind() }
        vbo = VBO(entity.hitbox.vertices.toFloatArray())
        ebo = EBO(INDICES)

        vao.linkVBO(vbo, 0, 3, GL11.GL_FLOAT, 3, 0L)

        vao.unbind()
        vbo.unbind()
        ebo.unbind()
    }

    init {
        generateModel()
    }

    @OptIn(ExperimentalAtomicApi::class)
    override fun render() {
        if(!FelisClient.INSTANCE.isDebugMode) return

        generateModel()

        shader.setUniform("colour", if(SceneManager.currentScene.load().entities.values.any { other -> entity.uuid != other.uuid && entity.hitbox.isColliding(other.hitbox) }) RGBColor.of(255, 0, 0) else RGBColor.of(0, 255, 0))
        shader.setUniform("model", Matrix4f().translate(entity.position.negate(Vector3f())))

        vao.bind()
        GL11.glDrawElements(GL11.GL_LINES, INDICES.size, GL11.GL_UNSIGNED_INT, 0)
    }

    override fun destroy() {
        vao.destroy()
        vbo.destroy()
        ebo.destroy()
    }
}