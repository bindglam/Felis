package com.bindglam.felis.client.rendering.model

import com.bindglam.felis.utils.Destroyable
import org.lwjgl.opengl.GL15


class VBO(vertices: FloatArray) : Destroyable {
    val id = GL15.glGenBuffers()

    init {
        bind()
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW)
    }

    fun bind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id)
    }

    fun unbind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    }

    override fun destroy() {
        GL15.glDeleteBuffers(id)
    }
}