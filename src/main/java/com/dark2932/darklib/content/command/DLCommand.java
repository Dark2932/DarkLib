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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

import static com.dark2932.darklib.DarkLib.FOLDER;

/**
 * @author Dark2932
 */
public class DLCommand extends DLAbstractCommand {

    private static final Map<String, ResourceKey<? extends Registry<?>>> REGISTRIES = new TreeMap<>();

    //扫描Registries中所有的静态常量，并存放在一个TreeMap中
    static {
        for (Field field : Registries.class.getFields()) {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                if (field.getType() == ResourceKey.class) {
                    try {
                        Object value = field.get(null);
                        if (value instanceof ResourceKey<?> key) {
                            // Check if the key refers to a registry (usually it starts with 'minecraft:...')
                            // or just take everything that is a ResourceKey in Registries class
                            REGISTRIES.put(formatName(field.getName()), (ResourceKey<? extends Registry<?>>) key);
                        }
                    } catch (IllegalAccessException ignored) {}
                }
            }
        }
    }

    //通过在事件里调用父类方法实现注册，这里仅做具体实现
    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var command = Commands.literal("darklib")
                .requires(source -> source.hasPermission(4));

        for (Map.Entry<String, ResourceKey<? extends Registry<?>>> entry : REGISTRIES.entrySet()) {
            command.then(Commands.literal("save" + entry.getKey())
                    .executes(ctx -> writeToFile(entry.getKey().toLowerCase(), (ResourceKey) entry.getValue(), ctx))
            );
        }

        dispatcher.register(command);
    }

    private static <T> int writeToFile(String fileName, ResourceKey<Registry<T>> key, CommandContext<CommandSourceStack> ctx) {
        Path file = FOLDER.resolve(fileName + ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {

            //写入
            Registry<T> objs = ctx.getSource().registryAccess().registryOrThrow(key);
            for (T registry : objs) {
                ResourceLocation id = objs.getKey(registry);
                if (id != null) {
                    writer.write(id.toString());
                    writer.newLine();
                }
            }

            //发送存储地址并可以通过点击打开
            String filePath = file.toAbsolutePath().toString();
            MutableComponent pathComponent = Component.literal(filePath)
                    .withStyle(style -> style
                            .withColor(ChatFormatting.GREEN)
                            .withUnderlined(true)
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, filePath))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to open the file")))
                    );
            ctx.getSource().sendSuccess(() -> Component.literal("Objects are successfully saved to: ").append(pathComponent), true);

        } catch (IOException e) {
            ctx.getSource().sendFailure(Component.literal("Failed to save objects: ").withStyle(ChatFormatting.RED).append(e.getMessage()));
        }
        return 0;
    }

    private static String formatName(String name) {
        String[] parts = name.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

}
