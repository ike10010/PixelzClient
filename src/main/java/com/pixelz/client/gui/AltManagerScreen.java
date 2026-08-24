package com.pixelz.client.gui;

import com.pixelz.client.alt.Alt;
import com.pixelz.client.alt.AltManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;

/**
 * Wurst-style AltManager GUI - add/edit/remove/star/login alts without restarting.
 * Cracked login works offline; premium shows Prism hint (like Wurst does for Microsoft).
 */
public class AltManagerScreen extends Screen {
    private final Screen parent;
    private final AltManager manager = AltManager.getInstance();
    private Alt selected = null;
    private int scroll = 0;
    private String status = "";
    private long statusTime = 0;

    public AltManagerScreen(Screen parent) {
        super(Text.literal("AltManager - Wurst Style"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int w = width / 2;
        int y0 = 42;

        // Buttons - Wurst style bottom row
        addDrawableChild(ButtonWidget.builder(Text.literal("Login"), b -> loginSelected()).dimensions(w - 210, height - 28, 68, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Add"), b -> openAdd()).dimensions(w - 136, height - 28, 50, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Edit"), b -> openEdit()).dimensions(w - 80, height - 28, 50, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Remove"), b -> removeSelected()).dimensions(w + -24, height - 28, 60, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("★ Star"), b -> starSelected()).dimensions(w + 40, height - 28, 58, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Prism Hint"), b -> status("For Microsoft alts, use PrismLauncher → Accounts (more secure than password).", true)).dimensions(w + 102, height - 28, 80, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Back"), b -> close()).dimensions(width - 60, 12, 50, 20).build());

        // Direct login field
        addDrawableChild(ButtonWidget.builder(Text.literal("Direct Login (name)"), b -> directLogin()).dimensions(10, height - 28, 130, 20).build());
    }

    private void loginSelected() {
        if (selected == null) { status("Select an alt first.", true); return; }
        if (selected.isCracked()) {
            boolean ok = manager.login(selected);
            if (ok) {
                status("Logged in as " + selected.getName() + " (offline).", false);
            } else status("Failed to login cracked alt.", true);
        } else {
            status("Premium alt '" + selected.getName() + "' needs Microsoft token. Use PrismLauncher → Accounts for Microsoft alts.", true);
        }
    }

    private void directLogin() {
        if (client == null) return;
        client.setScreen(new DirectLoginScreen(this));
    }

    private void openAdd() {
        if (client == null) return;
        client.setScreen(new AddAltScreen(this, null));
    }

    private void openEdit() {
        if (selected == null) { status("Select an alt to edit.", true); return; }
        if (client == null) return;
        client.setScreen(new AddAltScreen(this, selected));
    }

    private void removeSelected() {
        if (selected == null) { status("Select an alt to remove.", true); return; }
        manager.removeAlt(selected);
        selected = null;
        status("Removed alt.", false);
    }

    private void starSelected() {
        if (selected == null) { status("Select an alt to star.", true); return; }
        manager.setStarred(selected, !selected.isStarred());
        status(selected.isStarred() ? "Starred " + selected.getName() : "Unstarred " + selected.getName(), false);
    }

    private void status(String msg, boolean isError) {
        status = (isError ? "§c" : "§a") + msg;
        statusTime = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        // Title
        context.drawCenteredTextWithShadow(textRenderer, "AltManager - Wurst Style", width/2, 14, 0xFFFFFFFF);
        String current = client != null && client.getSession() != null ? "Current: " + client.getSession().getUsername() : "Current: ?";
        context.drawCenteredTextWithShadow(textRenderer, current, width/2, 26, 0xFFAAAAAA);
        if (!status.isEmpty() && System.currentTimeMillis() - statusTime < 4000) {
            context.drawCenteredTextWithShadow(textRenderer, status, width/2, 38, 0xFFFFFFFF);
        }

        // Alt list box
        int listX = 10;
        int listY = 52;
        int listW = width - 20;
        int listH = height - 92;
        context.fill(listX, listY, listX + listW, listY + listH, 0x80000000);
        context.fill(listX, listY, listX + listW, listY + 1, 0xFF333333);
        context.fill(listX, listY + listH - 1, listX + listW, listY + listH, 0xFF333333);
        context.fill(listX, listY, listX + 1, listY + listH, 0xFF333333);
        context.fill(listX + listW - 1, listY, listX + listW, listY + listH, 0xFF333333);

        var alts = manager.getAlts();
        if (alts.isEmpty()) {
            context.drawCenteredTextWithShadow(textRenderer, "No alts. Click Add to create a cracked alt (e.g., PixelzSteve).", width/2, listY + listH/2, 0xFF777777);
        } else {
            int y = listY + 4 - scroll;
            for (int i = 0; i < alts.size(); i++) {
                Alt alt = alts.get(i);
                int entryY = y + i * 20;
                if (entryY < listY - 20 || entryY > listY + listH) continue;
                boolean isSelected = alt == selected;
                int bg = isSelected ? 0xFF2A2A6E : 0xFF1A1A24;
                context.fill(listX + 2, entryY, listX + listW - 2, entryY + 18, bg);
                if (isSelected) context.fill(listX + 2, entryY, listX + 4, entryY + 18, 0xFF6D5CFF);
                String name = alt.getDisplayName();
                context.drawText(textRenderer, name, listX + 10, entryY + 5, isSelected ? 0xFFFFFFFF : 0xFFCCCCCC, false);
                String type = alt.isCracked() ? "cracked" : "premium";
                context.drawText(textRenderer, type, listX + listW - 70, entryY + 5, 0xFF777777, false);
            }
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        double mx = click.x();
        double my = click.y();
        int listX = 10;
        int listY = 52;
        int listW = width - 20;
        int listH = height - 92;
        if (mx >= listX && mx <= listX + listW && my >= listY && my <= listY + listH) {
            var alts = manager.getAlts();
            int y = listY + 4 - scroll;
            for (int i = 0; i < alts.size(); i++) {
                int entryY = y + i * 20;
                if (my >= entryY && my <= entryY + 18) {
                    selected = alts.get(i);
                    if (doubled) loginSelected();
                    return true;
                }
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        var alts = manager.getAlts();
        int maxScroll = Math.max(0, alts.size() * 20 + 8 - (height - 92));
        scroll = (int) Math.max(0, Math.min(maxScroll, scroll - verticalAmount * 10));
        return true;
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.key() == 256) { close(); return true; }
        return super.keyPressed(input);
    }

    @Override
    public void close() {
        if (client != null) client.setScreen(parent);
    }

    // --- Add/Edit dialog ---
    private static class AddAltScreen extends Screen {
        private final Screen parent;
        private final Alt editTarget;
        private TextFieldWidget nameField;
        private TextFieldWidget emailField;
        private boolean cracked = true;

        protected AddAltScreen(Screen parent, Alt editTarget) {
            super(Text.literal(editTarget == null ? "Add Alt" : "Edit Alt"));
            this.parent = parent;
            this.editTarget = editTarget;
            if (editTarget != null) cracked = editTarget.isCracked();
        }

        @Override
        protected void init() {
            nameField = new TextFieldWidget(textRenderer, width/2 - 100, height/2 - 46, 200, 20, Text.literal("Name"));
            nameField.setMaxLength(16);
            nameField.setPlaceholder(Text.literal("Username (e.g., PixelzSteve)"));
            if (editTarget != null) nameField.setText(editTarget.getName());
            addDrawableChild(nameField);
            setInitialFocus(nameField);

            emailField = new TextFieldWidget(textRenderer, width/2 - 100, height/2 - 18, 200, 20, Text.literal("Email"));
            emailField.setMaxLength(64);
            emailField.setPlaceholder(Text.literal("Email (for premium, optional)"));
            if (editTarget != null) emailField.setText(editTarget.getEmail());
            addDrawableChild(emailField);

            addDrawableChild(ButtonWidget.builder(Text.literal(cracked ? "Mode: Cracked" : "Mode: Premium"), b -> {
                cracked = !cracked;
                b.setMessage(Text.literal(cracked ? "Mode: Cracked" : "Mode: Premium"));
            }).dimensions(width/2 - 100, height/2 + 12, 200, 20).build());

            addDrawableChild(ButtonWidget.builder(Text.literal(editTarget == null ? "Add" : "Save"), b -> save()).dimensions(width/2 - 102, height/2 + 40, 96, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), b -> close()).dimensions(width/2 + 6, height/2 + 40, 96, 20).build());
        }

        private void save() {
            String name = nameField.getText().trim();
            if (name.isEmpty() || name.length() < 3) return;
            String email = emailField.getText().trim();
            AltManager mgr = AltManager.getInstance();
            if (editTarget != null) {
                editTarget.setName(name);
                editTarget.setEmail(email);
                editTarget.setCracked(cracked);
                mgr.save();
            } else {
                Alt alt = cracked ? Alt.cracked(name) : Alt.premium(name, email, "");
                mgr.addAlt(alt);
            }
            close();
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            renderBackground(context, mouseX, mouseY, delta);
            context.drawCenteredTextWithShadow(textRenderer, editTarget == null ? "Add Alt - Wurst Style" : "Edit Alt", width/2, height/2 - 68, 0xFFFFFFFF);
            context.drawText(textRenderer, "Cracked = offline (no password). Premium needs Prism for Microsoft.", width/2 - 100, height/2 + 32, 0xFF777777, false);
            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public void close() {
            if (client != null) client.setScreen(parent);
        }

        @Override
        public boolean keyPressed(KeyInput input) {
            if (input.key() == 256) { close(); return true; }
            return super.keyPressed(input);
        }
    }

    private static class DirectLoginScreen extends Screen {
        private final Screen parent;
        private TextFieldWidget nameField;
        protected DirectLoginScreen(Screen parent) { super(Text.literal("Direct Login")); this.parent = parent; }
        @Override
        protected void init() {
            nameField = new TextFieldWidget(textRenderer, width/2 - 100, height/2 - 10, 200, 20, Text.literal("Name"));
            nameField.setPlaceholder(Text.literal("Username to login (cracked)"));
            addDrawableChild(nameField);
            setInitialFocus(nameField);
            addDrawableChild(ButtonWidget.builder(Text.literal("Login"), b -> {
                String n = nameField.getText().trim();
                if (n.length() >= 3) {
                    Alt tmp = Alt.cracked(n);
                    boolean ok = AltManager.getInstance().login(tmp);
                    if (client != null) client.setScreen(parent);
                }
            }).dimensions(width/2 - 102, height/2 + 16, 96, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), b -> close()).dimensions(width/2 + 6, height/2 + 16, 96, 20).build());
        }
        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            renderBackground(context, mouseX, mouseY, delta);
            context.drawCenteredTextWithShadow(textRenderer, "Direct Login (Cracked)", width/2, height/2 - 32, 0xFFFFFFFF);
            super.render(context, mouseX, mouseY, delta);
        }
        @Override
        public void close() { if (client != null) client.setScreen(parent); }
        @Override
        public boolean keyPressed(KeyInput input) { if (input.key()==256) {close(); return true;} return super.keyPressed(input); }
    }
}
