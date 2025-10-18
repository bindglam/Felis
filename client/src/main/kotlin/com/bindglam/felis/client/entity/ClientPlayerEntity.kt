package com.bindglam.felis.client.entity

import com.bindglam.felis.client.rendering.scene.ICamera
import com.bindglam.felis.entity.AbstractPlayerEntity

class ClientPlayerEntity(val isMultiplayerAuthority: Boolean) : AbstractPlayerEntity(), ICamera {
    override fun tick() {
        super.tick()
    }

    override fun updateCamera() {
        if(isMultiplayerAuthority) {
        }
    }
}