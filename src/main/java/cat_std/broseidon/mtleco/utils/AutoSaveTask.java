package cat_std.broseidon.mtleco.utils;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

//TODO: Create Auto Save data after 5 minutes
public class AutoSaveTask extends BukkitRunnable {

    private final MultiEco plugin;

    public AutoSaveTask(MultiEco plugin) {
        this.plugin = plugin;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
//        File playerDataFile = new File(plugin.getDataPackage(), playerUUID.toString() + ".yml");
//        YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
//
//        Player player = Bukkit.getPlayer(playerUUID);
//        if (player != null) {
//            for (EconomyImplementer currency : plugin.getEconomyHandler().getEconomyImplementers()) {
//                double balance = currency.getBalance(player);
//                playerDataConfig.set(currency.getId(), balance);
//            }
//
//            // Remove non-existent currencies from the data
//            for (String currencyId : playerDataConfig.getKeys(false)) {
//                if (plugin.getEconomyHandler().getEconomyImplementer(currencyId) == null) {
//                    playerDataConfig.set(currencyId, null);
//                }
//            }
//
//
//            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Saved currencies data for " + ChatColor.YELLOW + player.getName());
//            try {
//                playerDataConfig.save(playerDataFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
