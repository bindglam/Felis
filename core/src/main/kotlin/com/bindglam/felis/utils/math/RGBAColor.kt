package com.bindglam.felis.utils.math

interface RGBAColor : RGBColor {
    fun a(): Float

    companion object {
        @JvmStatic
        fun of(r: Float, g: Float, b: Float, a: Float): RGBAColor = Impl(r, g, b, a)
    }

    private class Impl(private val r: Float, private val g: Float, private val b: Float, private val a: Float) : RGBAColor {
        override fun r(): Float = r
        override fun g(): Float = g
        override fun b(): Float = b
        override fun a(): Float = a
    }
}