//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.sowol.mixin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.maxish0t.wac.common.handlers.AntiCheatHandler;
import me.maxish0t.wac.utils.HashUtil;
import me.maxish0t.wac.utils.ModReference;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({AntiCheatHandler.class})
public abstract class MixinHyper {
    public MixinHyper() {
    }

    @Overwrite
    public static String getModMD5s() throws IOException {
        ArrayList<String> modMD5s = new ArrayList();
        LoadingModList loadingModList = FMLLoader.getLoadingModList();
        List<ModInfo> mods = loadingModList.getMods();
        Iterator var3 = mods.iterator();

        while(var3.hasNext()) {
            ModInfo mod = (ModInfo)var3.next();
            if (!mod.getModId().equals("forge") && !mod.getModId().equals("cheonglyeong") && !mod.getModId().equals("baritoe") && !mod.getModId().equals("forgehax") && !mod.getModId().equals("minecraft") && !mod.getModId().equals("wurst")) {
                File modFile = mod.getOwningFile().getFile().getFilePath().toFile();
                String fileSize = ModReference.getFileSizeBytes(modFile);
                String modID = HashUtil.getMD5Hash(fileSize) + "-" + mod.getNamespace();
                modMD5s.add(modID);
            }
        }

        return String.join(",", modMD5s);
    }
}
