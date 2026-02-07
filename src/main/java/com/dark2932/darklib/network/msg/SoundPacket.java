package com.dark2932.darklib.network.msg;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public record SoundPacket(SoundEvent sound, float volume, float pitch) {

    public static void encode(SoundPacket msg, FriendlyByteBuf buffer) {
        msg.sound.writeToNetwork(buffer);
        buffer.writeFloat(msg.volume);
        buffer.writeFloat(msg.pitch);
    }

    public static SoundPacket decode(FriendlyByteBuf buffer) {
        return new SoundPacket(SoundEvent.readFromNetwork(buffer), buffer.readFloat(), buffer.readFloat());
    }

    public static void handle(SoundPacket msg, Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context ctx = sup.get();
        if (ctx.getDirection().getReceptionSide().isClient()) {
            ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SoundClientPacket.handleClient(msg, sup)));
        }
        ctx.setPacketHandled(true);
    }

}

@OnlyIn(Dist.CLIENT)
class SoundClientPacket {

    public static void handleClient(SoundPacket msg, Supplier<NetworkEvent.Context> sup) {
        Player player = Minecraft.getInstance().player;
        NetworkEvent.Context ctx = sup.get();
        if (ctx.getDirection().getReceptionSide().isClient() && player != null) {
            player.playSound(msg.sound(), msg.volume(), msg.volume());
        }
    }

}