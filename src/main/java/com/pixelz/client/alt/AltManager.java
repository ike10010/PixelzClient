package com.pixelz.client.alt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pixelz.client.mixin.MixinMinecraftClientAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import net.minecraft.util.Uuids;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Wurst-style AltManager - persists alts to config/pixelz/alts.json, handles login.
 * Cracked login uses offline UUID (like Wurst AltManager for cracked alts).
 * Premium would need Microsoft auth - for now shows Prism hint.
 */
public class AltManager {
    private static final AltManager INSTANCE = new AltManager();
    public static AltManager getInstance() { return INSTANCE; }

    private final Path file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final List<Alt> alts = new ArrayList<>();
    private Alt currentAlt = null;

    private AltManager() {
        Path dir = FabricLoader.getInstance().getConfigDir().resolve("pixelz");
        try { Files.createDirectories(dir); } catch (IOException ignored) {}
        this.file = dir.resolve("alts.json");
        load();
    }

    public List<Alt> getAlts() { return alts; }

    public void load() {
        if (!Files.exists(file)) return;
        try {
            String json = Files.readString(file);
            Type type = new TypeToken<List<Alt>>(){}.getType();
            List<Alt> loaded = gson.fromJson(json, type);
            if (loaded != null) {
                alts.clear();
                alts.addAll(loaded);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.writeString(file, gson.toJson(alts));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAlt(Alt alt) {
        alts.add(alt);
        save();
    }

    public void removeAlt(Alt alt) {
        alts.remove(alt);
        save();
    }

    public Optional<Alt> getByName(String name) {
        return alts.stream().filter(a -> a.getName().equalsIgnoreCase(name)).findFirst();
    }

    public void setStarred(Alt alt, boolean starred) {
        // only one starred like Wurst favorite
        if (starred) alts.forEach(a -> a.setStarred(false));
        alt.setStarred(starred);
        save();
    }

    /**
     * Login with alt - for cracked, sets offline session.
     * For premium, shows message to use Prism (proper Microsoft auth).
     */
    public boolean login(Alt alt) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (alt.isCracked()) {
            try {
                UUID uuid = Uuids.getOfflinePlayerUuid(alt.getName());
                Session session = new Session(alt.getName(), uuid, "", Optional.empty(), Optional.empty());
                ((MixinMinecraftClientAccessor) mc).setSession(session);
                this.currentAlt = alt;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public Alt getCurrentAlt() { return currentAlt; }
}
