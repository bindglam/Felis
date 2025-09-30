package com.bindglam.felis.client.rendering.shader

import com.bindglam.felis.utils.Destroyable
import org.lwjgl.opengl.GL20
import java.io.File

class Shader(private val vertexFile: File, private val fragmentFile: File) : Destroyable {
    var id: Int = 0

    fun init() {
        fun createShader(type: Int, code: String): Int = GL20.glCreateShader(type).also {
            GL20.glShaderSource(it, code)
            GL20.glCompileShader(it)
        }

        var vertexShader = createShader(GL20.GL_VERTEX_SHADER, vertexFile.bufferedReader().readText())
        var fragmentShader = createShader(GL20.GL_FRAGMENT_SHADER, fragmentFile.bufferedReader().readText())

        id = GL20.glCreateProgram()
        GL20.glAttachShader(id, vertexShader)
        GL20.glAttachShader(id, fragmentShader)
        GL20.glLinkProgram(id)

        GL20.glDeleteShader(vertexShader)
        GL20.glDeleteShader(fragmentShader)
    }

    fun activate() {
        GL20.glUseProgram(id)
    }

    override fun destroy() {
        GL20.glDeleteProgram(id)
    }
}