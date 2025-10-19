package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.FelisClient
import com.bindglam.felis.client.rendering.model.EBO
import com.bindglam.felis.client.rendering.model.VAO
import com.bindglam.felis.client.rendering.model.VBO
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.client.utils.createTransformationMatrix
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.utils.math.RGBColor
import org.lwjgl.opengl.GL11

class DebugLightRenderer(val entity: Entity, val shader: Shader) : EntityRenderer {
    companion object {
        private val VERTICES = floatArrayOf(
            -0.1f, -0.1f,  0.1f,
            -0.1f, -0.1f, -0.1f,
            0.1f, -0.1f, -0.1f,
            0.1f, -0.1f,  0.1f,
            -0.1f,  0.1f,  0.1f,
            -0.1f,  0.1f, -0.1f,
            0.1f,  0.1f, -0.1f,
            0.1f,  0.1f,  0.1f
        )

        private val INDICES = intArrayOf(
            0, 1, 2,
            0, 2, 3,
            0, 4, 7,
            0, 7, 3,
            3, 7, 6,
            3, 6, 2,
            2, 6, 5,
            2, 5, 1,
            1, 5, 4,
            1, 4, 0,
            4, 5, 6,
            4, 6, 7
        )
    }

    val vao = VAO().apply { bind() }
    val vbo = VBO(VERTICES)
    val ebo = EBO(INDICES)

    init {
        vao.linkVBO(vbo, 0, 3, GL11.GL_FLOAT, 3, 0L)

        vao.unbind()
        vbo.unbind()
        ebo.unbind()
    }

    override fun render() {
        if(!FelisClient.INSTANCE.isDebugMode) return

        shader.setUniform("colour", RGBColor.of(255, 255, 255))
        shader.setUniform("model", entity.createTransformationMatrix())

        vao.bind()
        GL11.glDrawElements(GL11.GL_TRIANGLES, INDICES.size, GL11.GL_UNSIGNED_INT, 0)
    }

    override fun destroy() {
        vao.destroy()
        vbo.destroy()
        ebo.destroy()
    }
}