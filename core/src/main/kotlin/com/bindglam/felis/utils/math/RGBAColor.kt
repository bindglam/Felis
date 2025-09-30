package com.bindglam.felis.utils.math

interface RGBAColor : RGBColor {
    fun a(): Short

    companion object {
        @JvmStatic
        fun of(r: Short, g: Short, b: Short, a: Short): RGBAColor = Impl(r, g, b, a)
    }

    private class Impl(private val r: Short, private val g: Short, private val b: Short, private val a: Short) : RGBAColor {
        override fun r(): Short = r
        override fun g(): Short = g
        override fun b(): Short = b
        override fun a(): Short = a
    }
}