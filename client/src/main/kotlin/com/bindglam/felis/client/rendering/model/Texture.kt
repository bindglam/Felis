package com.bindglam.felis.client.rendering.model

import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.utils.Destroyable
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack.stackPush
import java.io.File
import java.nio.ByteBuffer

class Texture(file: File, private val type: Int, slot: Int, format: Int, pixelType: Int) : Destroyable {
    val id = GL11.glGenTextures()

    init {
        var image: ByteBuffer? = null
        var width = 0
        var height = 0

        stackPush().use { stack ->
            val pComp = stack.mallocInt(1)
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            STBImage.stbi_set_flip_vertically_on_load(true)
            image = STBImage.stbi_load(file.path, pWidth, pHeight, pComp, 4) ?: throw RuntimeException("Couldn't load ${file.path}")
            width = pWidth.get()
            height = pHeight.get()
        }

        GL13.glActiveTexture(slot)
        bind()

        GL11.glTexParameteri(type, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR)
        GL11.glTexParameteri(type, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

        GL11.glTexParameteri(type, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT)
        GL11.glTexParameteri(type, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT)

        GL11.glTexImage2D(type, 0, GL11.GL_RGBA, width, height, 0, format, pixelType, image)
        GL30.glGenerateMipmap(type)

        STBImage.stbi_image_free(image!!)

        unbind()
    }

    fun texUnit(shader: Shader, uniform: String, unit: Int) {
        shader.activate()

        shader.setUniform(uniform, unit)
    }

    fun bind() {
        GL11.glBindTexture(type, id)
    }

    fun unbind() {
        GL11.glBindTexture(type, 0)
    }

    override fun destroy() {
        GL11.glDeleteTextures(id)
    }
}