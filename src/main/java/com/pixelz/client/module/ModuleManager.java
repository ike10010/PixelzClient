package com.pixelz.client.module;

import com.pixelz.client.PixelzClient;
import com.pixelz.client.module.modules.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void load() {
        // Combat - requested menu
        register(new KillAuraModule());
        register(new VelocityModule()); // Anti-Knockback
        register(new CriticalsModule());
        register(new AutoClickerModule());
        register(new CrystalAuraModule());
        register(new BowAimbotModule());
        register(new AimAssistModule());
        register(new MultiAuraModule());
        register(new ClickAuraModule());
        register(new TriggerBotModule());
        register(new FastBowModule());
        // Movement - requested menu
        register(new FlyModule()); // Flight / CreativeFlight
        register(new SpeedModule()); // SpeedHack / BunnyHop
        register(new SprintModule());
        register(new NoFallModule());
        register(new JesusModule());
        register(new StepModule());
        register(new BlinkModule());
        register(new ParkourModule());
        register(new TimerModule());
        register(new SpiderModule());
        register(new SafeWalkModule());
        // Player
        register(new NoSlowModule());
        register(new AutoArmorModule());
        register(new AutoEatModule());
        register(new AutoSoupModule());
        // World - Wurst Blocks + requested
        register(new ScaffoldModule());
        register(new ChestStealerModule());
        register(new NukerModule());
        register(new FastBreakModule());
        register(new FastPlaceModule());
        register(new AutoToolModule());
        register(new AutoFishModule());
        register(new AutoMineModule());
        register(new VeinMinerModule());
        // Render - requested + performance
        register(new ESPModule());
        register(new FullBrightModule());
        register(new TracersModule());
        register(new HudModule());
        register(new BlockESPModule());
        register(new XRayModule());
        register(new CaveFinderModule());
        register(new FreecamModule());
        register(new ChestESPModule());
        register(new MobESPModule());
        register(new PlayerESPModule());
        register(new FPSBoostModule());
        register(new EntityCullingModule());
        register(new FastRenderModule());
        register(new NoLagModule());
        // Client
        register(new ClickGuiModule());
        register(new AltManagerModule());

        modules.sort(Comparator.comparing(Module::getName));
        PixelzClient.LOGGER.info("Registered {} modules", modules.size());
    }

    private void register(Module m) {
        modules.add(m);
    }

    public void onTick() {
        for (Module m : modules) if (m.isEnabled()) m.onTick();
    }

    public void onRender() {
        for (Module m : modules) if (m.isEnabled()) m.onRender();
    }

    public void onWorldRender(float tickDelta) {
        for (Module m : modules) if (m.isEnabled()) m.onWorldRender(tickDelta);
    }

    public void onKey(int key, int action) {
        // action: 1 = press, like GLFW_PRESS
        if (action != 1) return;
        for (Module m : modules) {
            if (m.getKey() == key) {
                m.toggle();
                if (PixelzClient.mc().player != null) {
                    String state = m.isEnabled() ? "§aenabled" : "§cdisabled";
                    // Optional chat feedback for toggles (like LB)
                    // PixelzClient.mc().player.sendMessage(Text.literal("§8[§dPixelz§8] " + m.getName() + " " + state), true);
                }
            }
        }
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getByCategory(Category c) {
        return modules.stream().filter(m -> m.getCategory() == c).toList();
    }

    public Optional<Module> get(String name) {
        return modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst();
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T get(Class<T> clazz) {
        for (Module m : modules) if (clazz.isInstance(m)) return (T) m;
        return null;
    }
}
