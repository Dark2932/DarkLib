package com.dark2932.darklib;

import com.dark2932.darklib.content.command.DLCommand;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Dark2932
 */
@Mod(DarkLib.MODID)
public class DarkLib {

    public static final String MODID = "darklib";
    public static final Path FOLDER = Paths.get(FMLPaths.GAMEDIR.get().toAbsolutePath().toString(), "darklib");

    public DarkLib() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        setup();

        new DLCommand(); //command
    }

    private static void setup() {
        try {
            Files.createDirectories(FOLDER);
        } catch (Exception ignored) {}
    }

}