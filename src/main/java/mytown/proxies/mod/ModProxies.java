package mytown.proxies.mod;

import cpw.mods.fml.common.Loader;
import mytown.MyTown;
import mytown.protection.*;

import java.util.ArrayList;
import java.util.List;

public class ModProxies {
    public static final String BLOOD_MAGIC_MOD_ID = "AWWayofTime";
    public static final String BOTANIA_MOD_ID = "Botania";
    public static final String BC_FACTORY_MOD_ID = "BuildCraft|Factory";
    public static final String BC_TRANSPORT_MOD_ID = "BuildCraft|Transport";
    public static final String EXTRA_UTILITIES_MOD_ID = "ExtraUtilities";
    public static final String FORGE_PERMS_MOD_ID = "ForgePerms";
    public static final String IC2_MOD_ID = "IC2";
    public static final String MFR_MOD_ID = "MineFactoryReloaded";
    public static final String THERMAL_EXPANSION_MOD_ID = "ThermalExpansion";
    public static final String MEKANISM_MOD_ID = "Mekanism";

    private static List<ModProxy> proxies = new ArrayList<ModProxy>();
    private static boolean loaded = false;

    private ModProxies() {
    }

    public static void load() {
        MyTown.instance.log.info("Starting proxies...");

        if (!ModProxies.loaded) {
            ModProxies.loaded = true;
            MyTown.instance.config.getCategory("modproxies").setComment("Holds the enable state of the different ModProxies.\nModProxies handle interaction with other mods.\nIf a mod interaction causes issues, just set it to false.");
        }

        // Load ModProxies
        for (ModProxy p : ModProxies.proxies) {
            /*
            if (p.getModID() == null || !Loader.isModLoaded(p.getName())) {// || !MyTown.instance.config.get("ModProxies", p.getName(), true).getBoolean(true)) {
                continue;
            }
            */
            if(p.getModID() != null && Loader.isModLoaded(p.getModID())) {
                MyTown.instance.log.info("Loading proxy and protection: " + p.getName());
                p.load();
                p.isLoaded = true;
            }
        }
    }

    /**
     * Adds all the {@link ModProxy}'s to the list
     */
    public static void addProxies() {
        proxies.add(new ModProxy("Blood Magic", BLOOD_MAGIC_MOD_ID, new BloodMagicProtection()));
        proxies.add(new ModProxy("Botania", BOTANIA_MOD_ID, new BotaniaProtection()));
        proxies.add(new ModProxy("Buildcraft|Factory", BC_FACTORY_MOD_ID, new BuildCraftFactoryProtection()));
        proxies.add(new ModProxy("Buildcraft|Transport", BC_TRANSPORT_MOD_ID, new BuildCraftTransportProtection()));
        proxies.add(new ModProxy("Extra Utilities", EXTRA_UTILITIES_MOD_ID, new ExtraUtilitiesProtection()));
        proxies.add(new ModProxy("Industrial Craft 2", IC2_MOD_ID, new IC2Protection()));
        proxies.add(new ModProxy("Minefactory Reloaded", MFR_MOD_ID, new MinefactoryReloadedProtection()));
        proxies.add(new ModProxy("Thermal Expansion", THERMAL_EXPANSION_MOD_ID, new ThermalExpansionProtection()));
        proxies.add(new ModProxy("Mekanism", MEKANISM_MOD_ID, new MekanismProtection()));
    }

    /**
     * Adds the given {@link ModProxy}
     *
     * @param proxy
     */
    public static void addProxy(ModProxy proxy) {
        ModProxies.proxies.add(proxy);
    }

    /**
     * Removes the given {@link ModProxy}
     *
     * @param proxy
     */
    public static void removeProxy(ModProxy proxy) {
        ModProxies.proxies.remove(proxy);
    }

    /**
     * Checks if the proxy with the give mod_id is loaded
     *
     * @param mod_id
     * @return
     */
    public static boolean isProxyLoaded(String mod_id) {
        for(ModProxy proxy : proxies) {
            if(proxy.getModID().equals(mod_id))
                return true;
        }
        return false;
    }
}