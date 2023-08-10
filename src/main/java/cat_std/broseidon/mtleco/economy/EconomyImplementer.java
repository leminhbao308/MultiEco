package cat_std.broseidon.mtleco.economy;

import cat_std.broseidon.mtleco.MultiEco;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EconomyImplementer implements Economy {

    private final MultiEco plugin;
    private final String id;
    private final String name;
    private String icon;

    private final HashMap<UUID, Double> playerBalances;

    public EconomyImplementer(MultiEco plugin,String id) {
        this.plugin = plugin;
        this.id = id;
        this.name = id;
        this.icon = id;
        this.playerBalances = new HashMap<>();
    }

    public HashMap<UUID, Double> getPlayerBalances() {
        return playerBalances;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    @Override
    public double getBalance(String name) {
        Player player = Bukkit.getPlayer(name);
        UUID uuid = player.getUniqueId();
        return this.playerBalances.get(uuid);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        return this.playerBalances.get(uuid);
    }

    @Override
    public double getBalance(String name, String s1) {
        Player player = Bukkit.getPlayer(name);
        UUID uuid = player.getUniqueId();
        return this.playerBalances.get(uuid);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        UUID uuid = offlinePlayer.getUniqueId();
        return this.playerBalances.get(uuid);
    }

    @Override
    public boolean has(String name, double amount) {
        if (Bukkit.getPlayer(name) == null) return false;
        else
            return this.playerBalances.get(Bukkit.getPlayer(name).getUniqueId()) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        return this.playerBalances.get(offlinePlayer.getUniqueId()) >= amount;
    }

    @Override
    public boolean has(String name, String s1, double amount) {
        if (Bukkit.getPlayer(name) == null) return false;
        else
            return this.playerBalances.get(Bukkit.getPlayer(name).getUniqueId()) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double amount) {
        return this.playerBalances.get(offlinePlayer.getUniqueId()) >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(String name, double amount) {
        return getEconomyResponse(name, amount);
    }

    private EconomyResponse getEconomyResponse(String name, double amount) {
        double oldBalance = 0;
        if (has(name, amount)) {
            Player player = Bukkit.getPlayer(name);
            UUID uuid = player.getUniqueId();
            oldBalance = this.playerBalances.get(uuid);
            this.playerBalances.put(uuid, oldBalance - amount);
            return new EconomyResponse(amount, oldBalance - amount, EconomyResponse.ResponseType.SUCCESS, "");
        } else
            return new EconomyResponse(amount, oldBalance, EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        double oldBalance = 0;
        if (has(offlinePlayer, amount)) {
            UUID uuid = offlinePlayer.getUniqueId();
            oldBalance = this.playerBalances.get(uuid);
            this.playerBalances.put(uuid, oldBalance - amount);
            return new EconomyResponse(amount, oldBalance - amount, EconomyResponse.ResponseType.SUCCESS, "");
        } else
            return new EconomyResponse(amount, oldBalance, EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String name, String s1, double amount) {
        return getEconomyResponse(name, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double amount) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = this.playerBalances.get(uuid);
        this.playerBalances.put(uuid, oldBalance - amount);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String name, double amount) {
        Player player = Bukkit.getPlayer(name);
        UUID uuid = player.getUniqueId();
        double oldBalance = this.playerBalances.get(uuid);
        this.playerBalances.put(uuid, oldBalance + amount);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = this.playerBalances.get(uuid);
        this.playerBalances.put(uuid, oldBalance + amount);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String name, String s1, double amount) {
        Player player = Bukkit.getPlayer(name);
        UUID uuid = player.getUniqueId();
        double oldBalance = this.playerBalances.get(uuid);
        this.playerBalances.put(uuid, oldBalance + amount);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double amount) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = this.playerBalances.get(uuid);
        this.playerBalances.put(uuid, oldBalance + amount);
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    public void setBalance(Player player, double balance) {
        UUID uuid = player.getUniqueId();
        this.playerBalances.put(uuid, balance);
    }
}