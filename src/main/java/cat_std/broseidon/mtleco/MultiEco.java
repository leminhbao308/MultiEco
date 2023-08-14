package cat_std.broseidon.mtleco;

import cat_std.broseidon.mtleco.commands.EconomyCommands;
import cat_std.broseidon.mtleco.commands.PayCommand;
import cat_std.broseidon.mtleco.economy.EconomyHandler;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import cat_std.broseidon.mtleco.events.PlayerJoinEvent;
import cat_std.broseidon.mtleco.events.PlayerLeaveEvent;
import cat_std.broseidon.mtleco.utils.ColorCode;
import cat_std.broseidon.mtleco.utils.MessageManager;
import cat_std.broseidon.mtleco.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class của plugin
 *
 * @author Broseidon
 */
public final class MultiEco extends JavaPlugin {

    private EconomyHandler economyHandler;
    private VaultHook vaultHook;

    private File dataPackage;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("=====================================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "MultiEco is starting...");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Author: " + ChatColor.AQUA + "Broseidon0308");
        Bukkit.getConsoleSender().sendMessage("=====================================");
        loadData();
        loadEconomyHandler(getConfig());
        hookPlugin();
        register();
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "MultiEco started successfully");
        Bukkit.getConsoleSender().sendMessage("=====================================");
    }

    /**
     * Load dữ liệu cần thiết cho plugin
     */
    private void loadData() {
        //Đường dẫn đến thư mục plugin
        File pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }
        //Lấy file config.yml
        File configFile = new File(pluginFolder, "config.yml");
        //Nếu file config.yml không tồn tại thì tạo file config.yml
        if (!configFile.exists()) {
            getConfig().options().copyDefaults();
            saveDefaultConfig();
        }
        //Tạo thư mục data
        dataPackage = new File(pluginFolder, "data");
        if (!dataPackage.exists()) {
            dataPackage.mkdir();
        }
        messageManager = new MessageManager(getConfig());
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Data loaded");
        Bukkit.getConsoleSender().sendMessage("=====================================");
    }

    /**
     * Load các loại tiền tệ và lưu trữ vào EconomyHandler
     *
     * @param config FileConfiguration của config.yml
     */
    private void loadEconomyHandler(FileConfiguration config) {
        ConfigurationSection currenciesSection = config.getConfigurationSection("currencies");
        List<EconomyImplementer> economyImplementers = new ArrayList<>();

        for (String currencyID : currenciesSection.getKeys(false)) {
            String icon = currenciesSection.getString(currencyID + ".icon");
            EconomyImplementer economyImplementer = new EconomyImplementer(this, currencyID);
            economyImplementer.setIcon(icon);
            // Add economyImplementer into EconomyHandler
            economyImplementers.add(economyImplementer);
        }
        economyHandler = new EconomyHandler(economyImplementers);
    }

    /**
     * Liên kết vào Vault
     */
    private void hookPlugin() {
        vaultHook = new VaultHook(this);
        vaultHook.hook();
    }

    /**
     * Đăng ký các sự kiện và lệnh
     */
    private void register() {
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(this), this);
        this.getCommand("eco").setExecutor(new EconomyCommands(this));
        this.getCommand("eco").setTabCompleter(new EconomyCommands(this));
        this.getCommand("pay").setExecutor(new PayCommand(this));
        this.getCommand("pay").setTabCompleter(new PayCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        vaultHook.unhook();
    }

    @Override
    public void reloadConfig() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            File playerDataFile = new File(this.dataPackage, player.getUniqueId().toString() + ".yml");
            YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

            for (EconomyImplementer currency : this.economyHandler.getEconomyImplementers()) {
                double balance = currency.getBalance(player);
                playerDataConfig.set(currency.getId(), balance);
            }

            // Remove non-existent currencies from the data
            removeNonExistCurrency(playerDataFile, playerDataConfig);
        }

        super.reloadConfig();
        loadEconomyHandler(getConfig());
        messageManager = new MessageManager(getConfig());
        if (vaultHook != null) {
            vaultHook.unhook();
            vaultHook.hook();
            for (Player player : Bukkit.getOnlinePlayers()) {
                File playerDataFile = new File(this.dataPackage, player.getUniqueId().toString() + ".yml");
                YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

                for (EconomyImplementer currency : this.economyHandler.getEconomyImplementers()) {
                    currency.setBalance(player, playerDataConfig.getDouble(currency.getId()));
                }

                // Remove non-existent currencies from the data
                removeNonExistCurrency(playerDataFile, playerDataConfig);
            }
        }
    }

    private void removeNonExistCurrency(File playerDataFile, YamlConfiguration playerDataConfig) {
        for (String currencyId : playerDataConfig.getKeys(false)) {
            if (this.economyHandler.getEconomyImplementer(currencyId) == null) {
                playerDataConfig.set(currencyId, null);
            }
        }

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDataPackage() {
        return dataPackage;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public EconomyHandler getEconomyHandler() {
        return economyHandler;
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }
}
