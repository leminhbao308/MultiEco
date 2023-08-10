package cat_std.broseidon.mtleco.events;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class PlayerJoinEvent implements Listener {

    private final MultiEco plugin;

    public PlayerJoinEvent(MultiEco plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        loadData(event);
    }

    private void loadData(org.bukkit.event.player.PlayerJoinEvent event) {
        File playerDataFile = new File(plugin.getDataPackage(), event.getPlayer().getUniqueId() + ".yml");

        if (!playerDataFile.exists()) {
            try {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Data not found! Created data file for " + event.getPlayer().getName());
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

        Player player = event.getPlayer();
        if (player != null) {
            for (EconomyImplementer currency : plugin.getEconomyHandler().getEconomyImplementers()) {
                if (playerDataConfig.contains(currency.getId())) {
                    double balance = playerDataConfig.getDouble(currency.getId());
                    currency.setBalance(player, balance);
                } else {
                    currency.setBalance(player, 0);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Loaded default balance for " + event.getPlayer().getName() + " in " + ChatColor.YELLOW + currency.getId() + " economy");
                    playerDataConfig.set(currency.getId(), 0);
                }
            }
        }
    }
}
