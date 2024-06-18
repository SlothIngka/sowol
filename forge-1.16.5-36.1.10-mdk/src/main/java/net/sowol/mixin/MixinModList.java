//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.sowol.mixin;

import java.util.Arrays;
import java.util.List;
import net.minecraftforge.fml.network.FMLHandshakeMessages;
import net.sowol.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({FMLHandshakeMessages.C2SModListReply.class})
public class MixinModList {
    @Shadow
    private List<String> mods;

    public MixinModList() {
    }

    @Inject(
            method = {"<init>()V"},
            at = {@At("RETURN")}
    )
    private void onInit(CallbackInfo info) {
        ConfigManager.loadHiddenModConfig();
        String[] hiddenMods = ConfigManager.getHiddenModsArray();
        this.mods.removeIf((modId) -> {
            return Arrays.asList(hiddenMods).contains(modId);
        });
    }
}
