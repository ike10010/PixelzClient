package com.pixelz.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.pixelz.client.PixelzClient;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

public class CommandManager {
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register(CommandManager::register);
    }

    private static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
        // .toggle <module>  or /pixelz toggle
        dispatcher.register(ClientCommandManager.literal("pixelz")
                .then(ClientCommandManager.literal("toggle")
                        .then(ClientCommandManager.argument("module", StringArgumentType.word())
                                .executes(ctx -> {
                                    String name = StringArgumentType.getString(ctx, "module");
                                    var opt = PixelzClient.INSTANCE.getModuleManager().get(name);
                                    if (opt.isEmpty()) {
                                        ctx.getSource().sendError(Text.literal("Module not found: " + name));
                                        return 0;
                                    }
                                    var m = opt.get();
                                    m.toggle();
                                    ctx.getSource().sendFeedback(Text.literal(m.getName() + " " + (m.isEnabled() ? "enabled" : "disabled")));
                                    return 1;
                                })))
                .then(ClientCommandManager.literal("list").executes(ctx -> {
                    var mods = PixelzClient.INSTANCE.getModuleManager().getModules();
                    StringBuilder sb = new StringBuilder("Modules (" + mods.size() + "): ");
                    mods.forEach(m -> sb.append(m.isEnabled() ? "§a" : "§7").append(m.getName()).append("§7, "));
                    ctx.getSource().sendFeedback(Text.literal(sb.toString()));
                    return 1;
                }))
                .then(ClientCommandManager.literal("help").executes(ctx -> {
                    ctx.getSource().sendFeedback(Text.literal("§dPixelz Client §7- Commands:"));
                    ctx.getSource().sendFeedback(Text.literal("§7/pixelz toggle <module> §8- toggle module"));
                    ctx.getSource().sendFeedback(Text.literal("§7/pixelz list §8- list modules"));
                    ctx.getSource().sendFeedback(Text.literal("§7Binds: Press key -> module toggles. Right-Shift opens ClickGUI"));
                    return 1;
                }))
                .executes(ctx -> {
                    ctx.getSource().sendFeedback(Text.literal("§dPixelz Client v" + PixelzClient.VERSION + " §7for 1.21.11 §8| §7/pixelz help"));
                    return 1;
                })
        );
    }
}
