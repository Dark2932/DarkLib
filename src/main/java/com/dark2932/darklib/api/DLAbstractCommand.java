package com.dark2932.darklib.api;

import com.dark2932.darklib.DarkLib;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dark2932
 */
public abstract class DLAbstractCommand {

    private static final List<DLAbstractCommand> COMMANDS = new ArrayList<>();

    public abstract void register(CommandDispatcher<CommandSourceStack> dispatcher);

    public DLAbstractCommand() {
        if (!COMMANDS.contains(this)) {
            COMMANDS.add(this);
        }
    }

    @Mod.EventBusSubscriber(modid = DarkLib.MODID)
    static class CommandRegisterEvent {

        @SubscribeEvent
        public static void onRegisterCommand(RegisterCommandsEvent event) {
            for (DLAbstractCommand command : COMMANDS) {
                command.register(event.getDispatcher());
            }
        }

    }

}
