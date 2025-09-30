package com.bindglam.felis.utils.math

interface RGBColor {
    fun r(): Short

    fun g(): Short

    fun b(): Short


    companion object {
        @JvmStatic
        fun of(r: Short, g: Short, b: Short): RGBColor = Impl(r, g, b)
    }

    private class Impl(private val r: Short, private val g: Short, private val b: Short) : RGBColor {
        override fun r(): Short = r
        override fun g(): Short = g
        override fun b(): Short = b
    }
}