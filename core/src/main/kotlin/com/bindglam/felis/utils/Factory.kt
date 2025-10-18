package com.bindglam.felis.utils

interface Factory<O, I> {
    fun create(value: I): O
}