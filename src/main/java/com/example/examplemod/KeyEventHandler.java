package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;

import static com.example.examplemod.ExampleMod.LOGGER;

public class KeyEventHandler {

    public KeyEventHandler() {
    }


    @SubscribeEvent
    public void KeyEvent(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        //if (StickUtil.holdingStick(player) == null) return;

        //LOGGER.info("Input!" + event.getKey());

    }
}
