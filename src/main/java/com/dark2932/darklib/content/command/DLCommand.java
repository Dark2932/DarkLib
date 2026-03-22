package com.dark2932.darklib.content.command;

import com.dark2932.darklib.api.DLAbstractCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.dark2932.darklib.DarkLib.FOLDER;
import static com.dark2932.darklib.DarkLib.checkFolder;

/**
 * @author Dark2932
 */
public class DLCommand extends DLAbstractCommand {

    private static final List<ResourceKey<Registry<?>>> REGISTRIES = new ArrayList<>();

    //扫描Registries中所有的静态常量，并存放在一个List中
    static {
        for (Field field : Registries.class.getFields()) {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                if (field.getType() == ResourceKey.class) {
                    try {
                        Object value = field.get(null);
                        if (value instanceof ResourceKey<?> key) {
                            REGISTRIES.add((ResourceKey<Registry<?>>) key);
                        }
                    } catch (IllegalAccessException ignored) {}
                }
            }
        }
    }

    //通过在事件里调用父类方法实现注册，这里仅做具体实现
    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var main_cmd = Commands.literal("darklib").requires(source -> source.hasPermission(4));

        //'saveAll' command
        main_cmd.then(Commands.literal("saveAll")
                .executes(ctx -> {
                    for (ResourceKey<Registry<?>> key : REGISTRIES) {
                        writeToFile(formatName((ResourceKey) key), (ResourceKey) key, ctx, true);
                    }
                    ctx.getSource().sendSuccess(() -> Component.literal("All registries have been successfully saved to: ").append(
                            Component.literal(FOLDER.toAbsolutePath().toString())
                                    .withStyle(style -> style
                                            .withColor(ChatFormatting.GREEN)
                                            .withUnderlined(true)
                                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, FOLDER.toAbsolutePath().toString()))
                                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to open the folder")))
                                    )
                    ), true);
                    return 0;
                })
        );

        //'save' command
        var save_cmd = Commands.literal("save");
        for (ResourceKey<Registry<?>> key : REGISTRIES) {
            String name = formatName((ResourceKey) key);
            save_cmd.then(Commands.literal(name)
                    .executes(ctx -> writeToFile(name, (ResourceKey) key, ctx, false))
            );
        }
        main_cmd.then(save_cmd);

        dispatcher.register(main_cmd);
    }

    private static <T> int writeToFile(String fileName, ResourceKey<Registry<T>> key, CommandContext<CommandSourceStack> ctx, boolean silent) {
        checkFolder();
        Path file = FOLDER.resolve(fileName + ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {

            //排序并写入txt文件
            Registry<T> objs = ctx.getSource().registryAccess().registryOrThrow(key);
            List<ResourceLocation> ids = new ArrayList<>(objs.keySet());
            ids.sort(Comparator.comparing(ResourceLocation::getNamespace).thenComparing(ResourceLocation::getPath));
            for (ResourceLocation id : ids) {
                writer.write(id.toString());
                writer.newLine();
            }

            //silent为false时，发送存储地址并可以通过点击打开
            if (!silent) {
                String filePath = file.toAbsolutePath().toString();
                ctx.getSource().sendSuccess(() -> Component.literal("All ids have been successfully saved to: ").append(
                        Component.literal(filePath)
                                .withStyle(style -> style
                                        .withColor(ChatFormatting.GREEN)
                                        .withUnderlined(true)
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, filePath))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to open the file")))
                                )
                ), true);
            }

        } catch (IOException e) {
            ctx.getSource().sendFailure(Component.literal("Failed to save ids: " + e.getClass().getName()).withStyle(ChatFormatting.RED));
        }
        return 0;
    }

    private static <T> String formatName(ResourceKey<Registry<T>> key) {
        String name = key.location().getPath();
        return name.contains("worldgen/") ? name.replace("worldgen/", "") : name;
    }

}
