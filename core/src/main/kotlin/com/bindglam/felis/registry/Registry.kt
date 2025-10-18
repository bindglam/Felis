package com.bindglam.felis.registry

import com.bindglam.felis.utils.Identifier

interface Registry<T> : Iterable<T> {
    fun size(): Int

    fun entries(): Collection<Map.Entry<Identifier, T>>

    operator fun get(identifier: Identifier): T?

    fun getOrThrow(identifier: Identifier): T = get(identifier)!!
}