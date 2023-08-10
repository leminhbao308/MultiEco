package cat_std.broseidon.mtleco.utils;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

import java.util.ArrayList;
import java.util.List;

public class VaultHook {

    private final MultiEco plugin;

    public VaultHook(MultiEco plugin) {
        this.plugin = plugin;
    }

    private List<Economy> provider;

    public void hook() {
        provider = new ArrayList<>();
        for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
            provider.add(economyImplementer);
            Bukkit.getServicesManager().register(Economy.class, economyImplementer, plugin, ServicePriority.Highest);
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI hooked to " + ChatColor.AQUA + economyImplementer.getId());
        }
    }

    public void singleHook(EconomyImplementer economyImplementer) {
        provider.add(economyImplementer);
        Bukkit.getServicesManager().register(Economy.class, economyImplementer, plugin, ServicePriority.Highest);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI hooked to " + ChatColor.AQUA + economyImplementer.getId());
    }

    public void unhook() {

        for (Economy economy : provider) {
            Bukkit.getServicesManager().unregister(Economy.class, economy);
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI unhooked from " + ChatColor.AQUA + economy.getName());
        }
    }

    public void singleUnhook(EconomyImplementer economyImplementer) {
        Bukkit.getServicesManager().unregister(Economy.class, economyImplementer);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI unhooked from " + ChatColor.AQUA + economyImplementer.getName());
    }
}
