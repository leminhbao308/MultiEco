package cat_std.broseidon.mtleco.utils;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

import java.util.ArrayList;
import java.util.List;

/**
 * Class này dùng để liên kết các loại tiền tệ với VaultAPI
 *
 * @author Broseidon
 * @see EconomyImplementer
 */
public class VaultHook {

    private final MultiEco plugin;

    public VaultHook(MultiEco plugin) {
        this.plugin = plugin;
    }

    private List<Economy> provider;

    /**
     * Liên kết các loại tiền tệ với VaultAPI
     */
    public void hook() {
        provider = new ArrayList<>();
        for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
            addEconomy(economyImplementer);
        }
    }

    /**
     * Liên kết một loại tiền tệ với VaultAPI
     *
     * @param economyImplementer Loại tiền tệ cần liên kết
     */
    public void singleHook(EconomyImplementer economyImplementer) {
        addEconomy(economyImplementer);
    }

    private void addEconomy(EconomyImplementer economyImplementer) {
        provider.add(economyImplementer);
        if (plugin.getConfig().getBoolean("currencies." + economyImplementer.getId() + ".default")) {

            Bukkit.getServicesManager().register(Economy.class, economyImplementer, plugin, ServicePriority.Highest);
            if (plugin.getConfig().getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI hooked to " + ChatColor.AQUA + economyImplementer.getId() + ChatColor.YELLOW + " as default currency");
            }
        } else {
            Bukkit.getServicesManager().register(Economy.class, economyImplementer, plugin, ServicePriority.Normal);
            if (plugin.getConfig().getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI hooked to " + ChatColor.AQUA + economyImplementer.getId());
            }
        }
    }

    /**
     * Hủy liên kết tất cả các loại tiền tệ với VaultAPI
     */
    public void unhook() {

        for (Economy economy : provider) {
            Bukkit.getServicesManager().unregister(Economy.class, economy);
            if (plugin.getConfig().getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI unhooked from " + ChatColor.AQUA + economy.getName());
            }
        }
    }

    /**
     * Hủy liên kết một loại tiền tệ với VaultAPI
     *
     * @param economyImplementer Loại tiền tệ cần hủy liên kết
     */
    public void singleUnhook(EconomyImplementer economyImplementer) {
        Bukkit.getServicesManager().unregister(Economy.class, economyImplementer);
        if (plugin.getConfig().getBoolean("debug")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI unhooked from " + ChatColor.AQUA + economyImplementer.getName());
        }
    }
}
