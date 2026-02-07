package com.dark2932.darklib.network;

import com.dark2932.darklib.DarkLib;
import com.dark2932.darklib.network.msg.SoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * @author Dark2932
 */
public class DLNetworkHandler {

    private static int ID = 0;
    private static final String VERSION = "1.14.514";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.tryBuild(DarkLib.MODID, "network"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(ID++, SoundPacket.class, SoundPacket::encode, SoundPacket::decode, SoundPacket::handle);
    }

    public static <M> M sendToPlayerClient(M msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
        return msg;
    }

    public static <M> M sendToAllClient(M msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
        return msg;
    }

    public static <M> M sendToServer(M msg) {
        INSTANCE.sendToServer(msg);
        return msg;
    }

}
