package cat_std.broseidon.mtleco.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {
    private FileConfiguration config;

    public MessageManager(FileConfiguration config) {
        this.config = config;
    }

    public String getPlayerDataNotFound() {
        return config.getString("messages.player-data-not-found");
    }

    public String getPlayerDataLoaded() {
        return config.getString("messages.player-data-loaded");
    }

    public String getPlayerDataSaved() {
        return config.getString("messages.player-data-saved");
    }

    public String getNoPermission() {
        return config.getString("messages.no-permission");
    }

    public String getConfigReloaded() {
        return config.getString("messages.config-reloaded");
    }

    public String getBanlanceTitle() {
        return config.getString("messages.balance-title");
    }

    public String getBalanceOtherTitle() {
        return config.getString("messages.balance-other-title");
    }

    public String getBalanceDisplay() {
        return config.getString("messages.balance");
    }

    public String getBalanceOtherDisplay() {
        return config.getString("messages.balance-other");
    }

    public String getNoPlayerSpecified() {
        return config.getString("messages.no-player-specified");
    }

    public String getNotOnline() {
        return config.getString("messages.not-online");
    }

    public String getNotFound() {
        return config.getString("messages.not-found");
    }

    public String getNegativeAmount() {
        return config.getString("messages.negative-amount");
    }

    public String getNotEnoughMoney() {
        return config.getString("messages.not-enough-money");
    }

    public String getGiveMessage() {
        return config.getString("messages.give-message");
    }

    public String getSetMessage() {
        return config.getString("messages.set-message");
    }

    public String getTakeMessage() {
        return config.getString("messages.take-message");
    }

    public String getNotEnoughToTake() {
        return config.getString("messages.not-enough-to-take");
    }

    public String getPayMessage() {
        return config.getString("messages.pay-message");
    }

    public String getReceivedMessage() {
        return config.getString("messages.received-message");
    }

    public String getCurrencyCreated() {
        return config.getString("messages.currency-created");
    }

    public String getCurrencyAlreadyExists() {
        return config.getString("messages.currency-already-exists");
    }

    public String getCurrencyDeleted() {
        return config.getString("messages.currency-deleted");
    }

    public String getCurrencyNotExists() {
        return config.getString("messages.currency-not-exists");
    }

    public String getCurrencyIconSet() {
        return config.getString("messages.icon-set");
    }

    public String getDefaultSet() {
        return config.getString("messages.default-set");
    }
}
