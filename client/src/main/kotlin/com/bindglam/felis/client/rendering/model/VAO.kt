package com.bindglam.felis.client.rendering.model

import com.bindglam.felis.utils.Destroyable
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class VAO : Destroyable {
    val id = GL30.glGenVertexArrays()

    fun linkVBO(vbo: VBO, layout: Int, numComponents: Int, type: Int, stride: Int, offset: Long) {
        vbo.bind()
        GL20.glVertexAttribPointer(layout, numComponents, type, false, stride * Float.SIZE_BYTES, offset * Float.SIZE_BYTES)
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