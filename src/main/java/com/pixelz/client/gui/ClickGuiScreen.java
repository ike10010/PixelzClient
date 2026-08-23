package com.pixelz.client.gui;

import com.pixelz.client.PixelzClient;
import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Pixelz Client ClickGUI - 1.21.11 compatible (KeyInput/Click records).
 */
public class ClickGuiScreen extends Screen {
    private final List<Panel> panels = new ArrayList<>();

    public ClickGuiScreen() {
        super(Text.literal("Pixelz ClickGUI"));
    }

    @Override
    protected void init() {
        panels.clear();
        int x = 10;
        int panelWidth = 110;
        for (Category cat : Category.values()) {
            Panel p = new Panel(cat, x, 20, panelWidth);
            x += panelWidth + 8;
            var mods = PixelzClient.INSTANCE.getModuleManager().getByCategory(cat);
            p.modules.addAll(mods);
            panels.add(p);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x80000000);
        context.drawCenteredTextWithShadow(this.textRenderer, "Pixelz Client - 1.21.11", this.width / 2, 6, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Right-Shift to open | ESC to close | Click to toggle", this.width / 2, this.height - 12, 0xFFAAAAAA);
        for (Panel p : panels) p.render(context, mouseX, mouseY, textRenderer);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        double mouseX = click.x();
        double mouseY = click.y();
        int button = click.button();
        for (Panel p : panels) {
            if (p.mouseClicked(mouseX, mouseY, button)) return true;
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.key() == 256) { // ESC
            this.close();
            return true;
        }
        return super.keyPressed(input);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    static class Panel {
        final Category category;
        int x, y, width;
        final List<Module> modules = new ArrayList<>();
        boolean expanded = true;

        Panel(Category cat, int x, int y, int width) {
            this.category = cat;
            this.x = x;
            this.y = y;
            this.width = width;
        }

        void render(DrawContext ctx, int mouseX, int mouseY, net.minecraft.client.font.TextRenderer tr) {
            boolean hoverHeader = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 14;
            ctx.fill(x, y, x + width, y + 14, hoverHeader ? category.getColor() : 0xFF222222);
            ctx.fill(x, y, x + width, y + 1, category.getColor());
            ctx.drawText(tr, category.getDisplayName(), x + 4, y + 4, 0xFFFFFFFF, true);
            ctx.drawText(tr, expanded ? "-" : "+", x + width - 10, y + 4, 0xFFFFFFFF, false);
            if (!expanded) return;
            int curY = y + 15;
            for (Module m : modules) {
                boolean hover = mouseX >= x + 1 && mouseX <= x + width - 1 && mouseY >= curY && mouseY <= curY + 12;
                boolean enabled = m.isEnabled();
                int bg = enabled ? 0xFF2A2A6E : (hover ? 0xFF333333 : 0xFF1E1E1E);
                ctx.fill(x + 1, curY, x + width - 1, curY + 12, bg);
                if (enabled) ctx.fill(x + 1, curY, x + 3, curY + 12, category.getColor());
                ctx.drawText(tr, m.getName(), x + 6, curY + 2, enabled ? 0xFFFFFFFF : 0xFFAAAAAA, false);
                curY += 13;
            }
            // manual border (drawBorder removed in 1.21.11)
            int h = curY - y;
            int border = 0xFF000000;
            ctx.fill(x, y, x + width, y + 1, border);
            ctx.fill(x, y + h - 1, x + width, y + h, border);
            ctx.fill(x, y, x + 1, y + h, border);
            ctx.fill(x + width - 1, y, x + width, y + h, border);
        }

        boolean mouseClicked(double mx, double my, int button) {
            if (mx >= x && mx <= x + width && my >= y && my <= y + 14) {
                if (button == 1) {
                    expanded = !expanded;
                    return true;
                }
                return true;
            }
            if (!expanded) return false;
            int curY = y + 15;
            for (Module m : modules) {
                if (mx >= x + 1 && mx <= x + width - 1 && my >= curY && my <= curY + 12) {
                    if (button == 0) {
                        m.toggle();
                        if (PixelzClient.mc().player != null) {
                            PixelzClient.mc().player.playSound(net.minecraft.sound.SoundEvents.UI_BUTTON_CLICK.value(), 0.5f, 1.0f);
                        }
                    }
                    return true;
                }
                curY += 13;
            }
            return false;
        }
    }
}
