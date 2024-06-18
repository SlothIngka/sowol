package net.sowol.config;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

@OnlyIn(Dist.CLIENT)
public class ConfigGui extends Screen {
    private final Screen parentScreen;
    private List<Mod> mods = new ArrayList<>();
    private int selected = -1;
    private int scroll = 0;

    public ConfigGui(Screen parent) {
        super(new StringTextComponent("Mod Configuration"));
        this.parentScreen = parent;
        ConfigManager.loadHiddenModConfig();
        Stream var10000 = ModList.get().getMods().stream().map((modInfo) -> {
            return new Mod(modInfo.getModId(), ConfigManager.isModHidden(modInfo.getModId()));
        });
        List var10001 = this.mods;
        var10000.forEach(var10001::add);
    }

    protected void init() {
        super.init();
        this.addButton(new Button(this.width / 2 - 155, this.height - 29, 150, 20, new StringTextComponent("Hide"), (button) -> {
            if (this.selected >= 0 && this.selected < this.mods.size()) {
                Mod mod = (Mod)this.mods.get(this.selected);
                mod.setHidden(true);
                ConfigManager.hideMod(mod.getModId());
            }

        }));
        this.addButton(new Button(this.width / 2 + 5, this.height - 29, 150, 20, new StringTextComponent("Show"), (button) -> {
            if (this.selected >= 0 && this.selected < this.mods.size()) {
                Mod mod = (Mod)this.mods.get(this.selected);
                mod.setHidden(false);
                ConfigManager.showMod(mod.getModId());
            }

        }));
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, "Mod Configuration", this.width / 2, 15, Color.WHITE.getRGB());
        int y = 35;

        for(int i = this.scroll; i < this.mods.size(); ++i) {
            Mod mod = (Mod)this.mods.get(i);
            String modText = mod.getModId() + (mod.isHidden() ? " (Hidden)" : "");
            drawString(matrixStack, this.font, modText, 20, y, mod.isHidden() ? Color.RED.getRGB() : Color.WHITE.getRGB());
            if (i == this.selected) {
                drawString(matrixStack, this.font, ">", 10, y, Color.YELLOW.getRGB());
            }

            y += 12;
            if (y > this.height - 60) {
                break;
            }
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            int y = 35;
            int modIndex = (int)((mouseY - (double)y) / 12.0) + this.scroll;
            if (modIndex >= 0 && modIndex < this.mods.size()) {
                this.selected = modIndex;
            }

            return true;
        }
    }

    private static class Mod {
        private String modId;
        private boolean hidden;

        public Mod(String modId, boolean hidden) {
            this.modId = modId;
            this.hidden = hidden;
        }

        public String getModId() {
            return this.modId;
        }

        public boolean isHidden() {
            return this.hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }
    }
}
