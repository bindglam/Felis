package com.bindglam.felis.registry

import com.bindglam.felis.utils.Identifier

interface WritableRegistry<T> : Registry<T> {
    fun freeze()

    operator fun set(identifier: Identifier, value: T)
}