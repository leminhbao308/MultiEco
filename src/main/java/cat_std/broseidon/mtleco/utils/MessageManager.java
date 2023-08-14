package cat_std.broseidon.mtleco.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {
    private FileConfiguration config;

    public MessageManager(FileConfiguration config) {
        this.config = config;
    }

    private String getPrefix() {
        return config.getString("prefix");
    }

    public String getPlayerDataNotFound() {
        return getPrefix() + config.getString("messages.player-data-not-found");
    }

    public String getPlayerDataLoaded() {
        return getPrefix() + config.getString("messages.player-data-loaded");
    }

    public String getPlayerDataSaved() {
        return getPrefix() + config.getString("messages.player-data-saved");
    }

    public String getNoPermission() {
        return getPrefix() + config.getString("messages.no-permission");
    }

    public String getConfigReloaded() {
        return getPrefix() + config.getString("messages.config-reloaded");
    }

    public String getBalanceTitle() {
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
        return getPrefix() + config.getString("messages.no-player-specified");
    }

    public String getNotOnline() {
        return getPrefix() + config.getString("messages.not-online");
    }

    public String getNotFound() {
        return getPrefix() + config.getString("messages.not-found");
    }

    public String getNegativeAmount() {
        return getPrefix() + config.getString("messages.negative-amount");
    }

    public String getNotEnoughMoney() {
        return getPrefix() + config.getString("messages.not-enough-money");
    }

    public String getGiveMessage() {
        return getPrefix() + config.getString("messages.give-message");
    }

    public String getSetMessage() {
        return getPrefix() + config.getString("messages.set-message");
    }

    public String getTakeMessage() {
        return getPrefix() + config.getString("messages.take-message");
    }

    public String getNotEnoughToTake() {
        return getPrefix() + config.getString("messages.not-enough-to-take");
    }

    public String getPayMessage() {
        return getPrefix() + config.getString("messages.pay-message");
    }

    public String getReceivedMessage() {
        return getPrefix() + config.getString("messages.received-message");
    }

    public String getCurrencyCreated() {
        return getPrefix() + config.getString("messages.currency-created");
    }

    public String getCurrencyAlreadyExists() {
        return getPrefix() + config.getString("messages.currency-already-exists");
    }

    public String getCurrencyDeleted() {
        return getPrefix() + config.getString("messages.currency-deleted");
    }

    public String getCurrencyNotExists() {
        return getPrefix() + config.getString("messages.currency-not-exists");
    }

    public String getCurrencyIconSet() {
        return getPrefix() + config.getString("messages.icon-set");
    }

    public String getDefaultSet() {
        return getPrefix() + config.getString("messages.default-set");
    }
}
