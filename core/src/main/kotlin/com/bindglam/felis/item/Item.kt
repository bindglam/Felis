package com.bindglam.felis.item

import com.bindglam.felis.utils.Identifier
import com.bindglam.felis.utils.IdentifierLike

class Item(private val identifier: Identifier) : IdentifierLike {
    override fun identifier(): Identifier = identifier
}