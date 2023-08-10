package cat_std.broseidon.mtleco.events;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerJoinEvent implements Listener {

    private final MultiEco plugin;

    public PlayerJoinEvent(MultiEco plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        loadData(event.getPlayer());
    }

    private void loadData(Player player) {
        File playerDataFile = new File(plugin.getDataPackage(), player.getUniqueId() + ".yml");

        if (!playerDataFile.exists()) {
            try {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Data not found! Created data file for " + player.getName());
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

        List<String> missingCurrencies = new ArrayList<>();

        for (EconomyImplementer currency : plugin.getEconomyHandler().getEconomyImplementers()) {
            if (playerDataConfig.contains(currency.getId())) {
                double balance = playerDataConfig.getDouble(currency.getId());
                currency.setBalance(player, balance);
            } else {
                currency.setBalance(player, 0);
                playerDataConfig.set(currency.getId(), 0);
                missingCurrencies.add(currency.getId());
            }
        }

        // Remove missing currencies from player data file
        for (String missingCurrency : missingCurrencies) {
            playerDataConfig.set(missingCurrency, null);
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Loaded currencies data for " + ChatColor.YELLOW + player.getName());

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
