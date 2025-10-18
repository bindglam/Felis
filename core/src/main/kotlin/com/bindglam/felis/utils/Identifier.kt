package com.bindglam.felis.utils

sealed interface Identifier : IdentifierLike {
    fun namespace(): String

    fun key(): String


    companion object {
        @JvmStatic
        fun of(namespace: String, key: String): Identifier = Impl(namespace, key)
    }

    private data class Impl(private val namespace: String, private val key: String) : Identifier {
        override fun namespace(): String = namespace
        override fun key(): String = key
        override fun identifier(): Identifier = this
    }
}