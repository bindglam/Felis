package com.bindglam.felis.client.rendering.model

import com.bindglam.felis.utils.Destroyable
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryUtil.NULL

class VAO : Destroyable {
    val id = GL30.glGenVertexArrays()

    fun linkVBO(vbo: VBO, layout: Int) {
        vbo.bind()
        GL20.glVertexAttribPointer(layout, 3, GL11.GL_FLOAT, false, 0, NULL)
        GL20.glEnableVertexAttribArray(layout)
        vbo.unbind()
    }

    fun bind() {
        GL30.glBindVertexArray(id)
    }

    fun unbind() {
        GL30.glBindVertexArray(0)
    }

    override fun destroy() {
        GL30.glDeleteVertexArrays(id)
    }
}