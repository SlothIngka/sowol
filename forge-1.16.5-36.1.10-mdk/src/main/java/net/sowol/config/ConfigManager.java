//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.sowol.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConfigManager {
    private static final String CONFIG_FILE = "config/hidden_mods.cfg";
    private static Set<String> hiddenMods = new HashSet();

    public ConfigManager() {
    }

    public static void loadHiddenModConfig() {
        hiddenMods.clear();

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("config/hidden_mods.cfg"));
            Throwable var1 = null;

            try {
                String line;
                try {
                    while((line = reader.readLine()) != null) {
                        hiddenMods.add(line.trim());
                    }
                } catch (Throwable var11) {
                    var1 = var11;
                    throw var11;
                }
            } finally {
                if (reader != null) {
                    if (var1 != null) {
                        try {
                            reader.close();
                        } catch (Throwable var10) {
                            var1.addSuppressed(var10);
                        }
                    } else {
                        reader.close();
                    }
                }

            }
        } catch (IOException var13) {
            IOException e = var13;
            System.err.println("Failed to load hidden mods config: " + e.getMessage());
        }

    }

    public static void saveHiddenModsConfig() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("config/hidden_mods.cfg"));
            Throwable var1 = null;

            try {
                Iterator var2 = hiddenMods.iterator();

                while(var2.hasNext()) {
                    String modId = (String)var2.next();
                    writer.write(modId);
                    writer.newLine();
                }
            } catch (Throwable var12) {
                var1 = var12;
                throw var12;
            } finally {
                if (writer != null) {
                    if (var1 != null) {
                        try {
                            writer.close();
                        } catch (Throwable var11) {
                            var1.addSuppressed(var11);
                        }
                    } else {
                        writer.close();
                    }
                }

            }
        } catch (IOException var14) {
            IOException e = var14;
            System.err.println("Failed to save hidden mods config: " + e.getMessage());
        }

    }

    public static void hideMod(String modId) {
        hiddenMods.add(modId);
        saveHiddenModsConfig();
    }

    public static void showMod(String modId) {
        hiddenMods.remove(modId);
        saveHiddenModsConfig();
    }

    public static boolean isModHidden(String modId) {
        return hiddenMods.contains(modId);
    }

    public static String[] getHiddenModsArray() {
        return (String[])hiddenMods.toArray(new String[0]);
    }
}
