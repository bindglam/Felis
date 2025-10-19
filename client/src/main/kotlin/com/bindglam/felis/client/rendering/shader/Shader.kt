package com.bindglam.felis.client.rendering.shader

import com.bindglam.felis.utils.Destroyable
import com.bindglam.felis.utils.math.RGBAColor
import com.bindglam.felis.utils.math.RGBColor
import com.bindglam.felis.utils.math.toVector3f
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.system.MemoryStack.stackPush
import java.io.File
import kotlin.system.exitProcess

class Shader(private val vertexFile: File, private val fragmentFile: File) : Destroyable {
    var id: Int = 0

    fun init() {
        fun createShader(type: Int, code: String): Int = GL20.glCreateShader(type).also { shaderId ->
            GL20.glShaderSource(shaderId, code)
            GL20.glCompileShader(shaderId)
            if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS ) == GL11.GL_FALSE){
                println(GL20.glGetShaderInfoLog(shaderId, 500))
                System.err.println("Could not compile shader!")
                exitProcess(-1)
            }
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

    fun deactivate() {
        GL20.glUseProgram(0)
    }

    override fun destroy() {
        GL20.glDeleteProgram(id)
    }

    fun setUniform(name: String, value: Matrix4f) {
        stackPush().use { stack ->
            GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(id, name), false, value.get(stack.mallocFloat(16)))
        }
    }

    fun setUniform(name: String, value: Vector3f) {
        GL20.glUniform3f(GL20.glGetUniformLocation(id, name), value.x, value.y, value.z)
    }

    fun setUniform(name: String, value: RGBAColor) {
        GL20.glUniform4f(GL20.glGetUniformLocation(id, name), value.r()/255f, value.g()/255f, value.b()/255f, value.a()/255f)
    }

    fun setUniform(name: String, value: RGBColor) {
        GL20.glUniform3f(GL20.glGetUniformLocation(id, name), value.r()/255f, value.g()/255f, value.b()/255f)
    }

    fun setUniform(name: String, value: Int) {
        GL20.glUniform1i(GL20.glGetUniformLocation(id, name), value)
    }
}