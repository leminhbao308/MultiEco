package cat_std.broseidon.mtleco;

import cat_std.broseidon.mtleco.commands.EconomyCommands;
import cat_std.broseidon.mtleco.commands.PayCommand;
import cat_std.broseidon.mtleco.economy.EconomyHandler;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import cat_std.broseidon.mtleco.events.PlayerJoinEvent;
import cat_std.broseidon.mtleco.events.PlayerLeaveEvent;
import cat_std.broseidon.mtleco.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class MultiEco extends JavaPlugin {

    private EconomyHandler economyHandler;
    private VaultHook vaultHook;

    private File dataPackage;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadData();
        loadEconomyHandler(getConfig());
        hookPlugin();
        register();
    }

    private void loadEconomyHandler(FileConfiguration config) {
        ConfigurationSection currenciesSection = config.getConfigurationSection("currencies");
        List<EconomyImplementer> economyImplementers = new ArrayList<>();

        for (String currencyID : currenciesSection.getKeys(false)) {
            String icon = currenciesSection.getString(currencyID + ".icon");
            Bukkit.getConsoleSender().sendMessage("Loading " + currencyID + " economy...");
            EconomyImplementer economyImplementer = new EconomyImplementer(this, currencyID);
            economyImplementer.setIcon(icon);
            // Add economyImplementer into EconomyHandler
            economyImplementers.add(economyImplementer);
        }
        economyHandler = new EconomyHandler(economyImplementers);
    }


    private void loadData() {
        File pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }
        File configFile = new File(pluginFolder, "config.yml");
        if (!configFile.exists()) {
            getConfig().options().copyDefaults();
            saveDefaultConfig();
        }

        dataPackage = new File(pluginFolder, "data");
        if (!dataPackage.exists()) {
            dataPackage.mkdir();
        }
    }

    public File getDataPackage() {
        return dataPackage;
    }

    private void register() {
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(this), this);
        this.getCommand("eco").setExecutor(new EconomyCommands(this));
        this.getCommand("eco").setTabCompleter(new EconomyCommands(this));
        this.getCommand("pay").setExecutor(new PayCommand(this));
        this.getCommand("pay").setTabCompleter(new PayCommand(this));
    }

    private void hookPlugin() {
        vaultHook = new VaultHook(this);
        vaultHook.hook();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        vaultHook.unhook();
    }

    public EconomyHandler getEconomyHandler() {
        return economyHandler;
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }
}
