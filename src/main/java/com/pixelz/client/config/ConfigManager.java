package com.pixelz.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixelz.client.PixelzClient;
import com.pixelz.client.module.Module;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private final Path configFile;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ConfigManager() {
        Path configDir = FabricLoader.getInstance().getConfigDir().resolve("pixelz");
        try { Files.createDirectories(configDir); } catch (IOException ignored) {}
        this.configFile = configDir.resolve("config.json");
    }

    public void load() throws IOException {
        if (!Files.exists(configFile)) {
            save();
            return;
        }
        String json = Files.readString(configFile);
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        if (obj == null) return;
        for (Module m : PixelzClient.INSTANCE.getModuleManager().getModules()) {
            if (obj.has(m.getName())) {
                JsonObject modObj = obj.getAsJsonObject(m.getName());
                if (modObj.has("enabled")) m.setEnabled(modObj.get("enabled").getAsBoolean());
                if (modObj.has("key")) m.setKey(modObj.get("key").getAsInt());
            }
        }
    }

    public void save() throws IOException {
        JsonObject obj = new JsonObject();
        for (Module m : PixelzClient.INSTANCE.getModuleManager().getModules()) {
            JsonObject modObj = new JsonObject();
            modObj.addProperty("enabled", m.isEnabled());
            modObj.addProperty("key", m.getKey());
            obj.add(m.getName(), modObj);
        }
        Files.writeString(configFile, gson.toJson(obj));
    }
}
