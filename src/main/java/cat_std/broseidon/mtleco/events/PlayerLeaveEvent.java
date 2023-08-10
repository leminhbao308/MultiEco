package cat_std.broseidon.mtleco.events;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerLeaveEvent implements Listener {
    private final MultiEco plugin;

    public PlayerLeaveEvent(MultiEco plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        saveData(event.getPlayer().getUniqueId());
    }

    private void saveData(UUID playerUUID) {
        File playerDataFile = new File(plugin.getDataPackage(), playerUUID.toString() + ".yml");
        YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            for (EconomyImplementer currency : plugin.getEconomyHandler().getEconomyImplementers()) {
                double balance = currency.getBalance(player);
                playerDataConfig.set(currency.getId(), balance);
            }
            try {
                playerDataConfig.save(playerDataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
