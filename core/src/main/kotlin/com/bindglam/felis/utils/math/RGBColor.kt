package com.bindglam.felis.utils.math

interface RGBColor {
    fun r(): Float

    fun g(): Float

    fun b(): Float


    companion object {
        @JvmStatic
        fun of(r: Float, g: Float, b: Float): RGBColor = Impl(r, g, b)
    }

    private class Impl(private val r: Float, private val g: Float, private val b: Float) : RGBColor {
        override fun r(): Float = r
        override fun g(): Float = g
        override fun b(): Float = b
    }
}