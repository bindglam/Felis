package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.client.rendering.model.EBO
import com.bindglam.felis.client.rendering.model.VAO
import com.bindglam.felis.client.rendering.model.VBO
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.client.utils.createTransformationMatrix
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.utils.math.RGBColor
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11

class HitboxRenderer(private val entity: Entity, private val shader: Shader) : EntityRenderer {
    companion object {
        private val INDICES = intArrayOf(
            0, 1,   1, 2,   2, 3,   3, 0,
            4, 5,   5, 6,   6, 7,   7, 4,
            0, 4,   1, 5,   2, 6,   3, 7
        )
    }

    val vao = VAO().apply { bind() }
    val vbo = VBO(entity.hitbox.vertices.toFloatArray())
    val ebo = EBO(INDICES)

    init {
        vao.linkVBO(vbo, 0, 3, GL11.GL_FLOAT, 3, 0L)

        vao.unbind()
        vbo.unbind()
        ebo.unbind()
    }

    override fun render() {
        shader.setUniform("colour", RGBColor.of(0, 255, 0))
        shader.setUniform("model", entity.createTransformationMatrix())

        vao.bind()
        GL11.glDrawElements(GL11.GL_LINES, INDICES.size, GL11.GL_UNSIGNED_INT, 0)
    }

    override fun destroy() {
        vao.destroy()
        vbo.destroy()
        ebo.destroy()
    }
}