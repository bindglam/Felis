package com.bindglam.felis.registry

import com.bindglam.felis.utils.Identifier

class ScalableRegistry<T> : WritableRegistry<T> {
    private val map = hashMapOf<Identifier, T>()

    private var isFrozen = false

    override fun freeze() {
        if(isFrozen)
            throw IllegalStateException("Already frozen")

        isFrozen = true
    }

    override fun set(identifier: Identifier, value: T) {
        if(isFrozen)
            throw IllegalStateException("The registry is frozen")

        map[identifier] = value
    }

    override fun size(): Int = map.size

    override fun entries(): Collection<Map.Entry<Identifier, T>> = map.entries

    override fun get(identifier: Identifier): T? = map[identifier]

    override fun iterator(): Iterator<T> = map.values.iterator()
}