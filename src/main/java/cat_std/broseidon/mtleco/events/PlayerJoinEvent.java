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

/**
 * Sự kiện khi người chơi đăng nhập vào server
 *
 * @see PlayerLeaveEvent
 * @author Broseidon
 */
public class PlayerJoinEvent implements Listener {

    private final MultiEco plugin;

    public PlayerJoinEvent(MultiEco plugin) {
        this.plugin = plugin;
    }

    /**
     * Tải dữ liệu của người chơi khi họ đăng nhập vào server
     *
     * @param event
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        loadData(event.getPlayer());
    }

    private void loadData(Player player) {
        //Lấy file dữ liệu của người chơi từ file yml
        File playerDataFile = new File(plugin.getDataPackage(), player.getUniqueId() + ".yml");

        //Nếu file không tồn tại thì tạo file mới (Dữ liệu người chơi không tồn tại)
        if (!playerDataFile.exists()) {
            try {
                //TODO: Change message to message.yml
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Data not found! Created data file for " + player.getName());
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        //Tạo danh sách các loại tiền tệ mà người chơi không có
        List<String> missingCurrencies = new ArrayList<>();

        for (EconomyImplementer currency : plugin.getEconomyHandler().getEconomyImplementers()) {
            //Nếu file dữ liệu của người chơi có chứa loại tiền tệ này thì lấy số dư của người chơi
            if (playerDataConfig.contains(currency.getId())) {
                double balance = playerDataConfig.getDouble(currency.getId());
                currency.setBalance(player, balance);
            }
            //Nếu không thì tạo một loại tiền tệ mới với số dư là 0
            else {
                currency.setBalance(player, 0);
                playerDataConfig.set(currency.getId(), 0);
                missingCurrencies.add(currency.getId());
            }
        }

        // Xóa các loại tiền tệ không tồn tại trong plugin khỏi file dữ liệu của người chơi
        for (String missingCurrency : missingCurrencies) {
            playerDataConfig.set(missingCurrency, null);
        }

        //TODO: Change message to message.yml
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Loaded currencies data for " + ChatColor.YELLOW + player.getName());

        //Lưu file dữ liệu của người chơi
        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
