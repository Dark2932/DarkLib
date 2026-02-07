package com.dark2932.darklib.util;

import com.dark2932.darklib.network.DLNetworkHandler;
import com.dark2932.darklib.network.msg.SoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;

/**
 * @author Dark2932
 */
public class PlayerUtil {

    public static void sendSoundPacket(Player player, SoundEvent sound) {
        sendSoundPacket(player, sound, 1.0f, 1.0f);
    }

    public static void sendSoundPacket(Player player, SoundEvent sound, float volume, float pitch) {
        if (player != null) DLNetworkHandler.sendToPlayerClient(new SoundPacket(sound, volume, pitch), (ServerPlayer) player);
    }

}