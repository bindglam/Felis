package com.bindglam.felis.client.rendering.model

import com.bindglam.felis.utils.Destroyable
import org.lwjgl.opengl.GL15

class EBO(indices: IntArray) : Destroyable {
    val id = GL15.glGenBuffers()

    init {
        bind()
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW)
    }

    fun bind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id)
    }

    fun unbind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    override fun destroy() {
        GL15.glDeleteBuffers(id)
    }
}