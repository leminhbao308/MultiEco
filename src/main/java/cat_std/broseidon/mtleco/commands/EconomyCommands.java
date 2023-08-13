package cat_std.broseidon.mtleco.commands;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * Class này chứa các lệnh /eco của plugin
 * Lệnh /eco dùng để quản lý tiền tệ trong server
 *
 * @author Broseidon
 */
public class EconomyCommands implements CommandExecutor, TabCompleter {

    private final MultiEco plugin;

    public EconomyCommands(MultiEco plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("eco")) {

                // RELOAD COMMAND
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!player.hasPermission("mtleco.eco.admin.reload")) {
                        //TODO: Change message to message.yml
                        player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return true;
                    }

                    if (args.length == 1) {
                        plugin.reloadConfig();
                        //TODO: Change message to message.yml
                        player.sendMessage(ChatColor.GREEN + "Reloaded config");
                    }
                    return true;
                }


                //BALANCE COMMAND
                if (args[0].equalsIgnoreCase("balance")) {
                    if (!player.hasPermission("mtleco.eco.balance")) {
                        //TODO: Change message to message.yml
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return true;
                    }

                    if (args.length == 1) {
                        try {
                            for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
                                int balance = (int) economyImplementer.getBalance(player);
                                //TODO: Change message to message.yml
                                player.sendMessage(ChatColor.GREEN + player.getName() + " §7has §a" + balance + "§7 in their " + ChatColor.AQUA + economyImplementer.getId() + ChatColor.GREEN + " account");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else if (args.length == 2) {
                        if (!player.hasPermission("mtleco.eco.balance.others")) {
                            //TODO: Change message to message.yml
                            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                            return true;
                        }

                        return getOtherBalance(sender, args);
                    }
                }

                //GIVE COMMAND
                if (args[0].equalsIgnoreCase("give")) {
                    if (!player.hasPermission("mtleco.eco.admin.give")) {
                        //TODO: Change message to message.yml
                        player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return true;
                    }

                    return givePlayerBalance(sender, args);
                }

                //SET COMMAND
                if (args[0].equalsIgnoreCase("set")) {
                    if (!player.hasPermission("mtleco.eco.admin.set")) {
                        //TODO: Change message to message.yml
                        player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return true;
                    }

                    return setPlayerBalance(sender, args);
                }

                //TAKE COMMAND
                if (args[0].equalsIgnoreCase("take")) {
                    if (!player.hasPermission("mtleco.eco.admin.take")) {
                        //TODO: Change message to message.yml
                        player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return true;
                    }

                    return takePlayerBalance(sender, args);
                }

                //ADMIN COMMANDS
                if (args[0].equalsIgnoreCase("admin")) {
                    if (args.length == 3) {
                        //CREATE COMMAND
                        if (args[1].equalsIgnoreCase("create")) {
                            if (!player.hasPermission("mtleco.eco.admin.create")) {
                                //TODO: Change message to message.yml
                                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                                return true;
                            }

                            return createCurrency(player, args);
                        }

                        //DELETE COMMAND
                        if (args[1].equalsIgnoreCase("delete")) {
                            if (!player.hasPermission("mtleco.eco.admin.delete")) {
                                //TODO: Change message to message.yml
                                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                                return true;
                            }

                            return deleteCurrency(player, args);
                        }

                        //ICON DELETE COMMAND
                        if (args[1].equalsIgnoreCase("icondel")) {
                            if (!player.hasPermission("mtleco.eco.admin.icondel")) {
                                //TODO: Change message to message.yml
                                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                                return true;
                            }

                            return deleteCurrencyIcon(player, args);
                        }
                    }

                    //ICON SET COMMAND
                    if (args.length == 4) {
                        if (args[1].equalsIgnoreCase("iconset")) {
                            if (!player.hasPermission("mtleco.eco.admin.iconset")) {
                                //TODO: Change message to message.yml
                                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                                return true;
                            }

                            return setCurrencyIcon(player, args);
                        }
                    }
                }
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (command.getName().equalsIgnoreCase("eco")) {
                // RELOAD COMMAND
                if (args[0].equalsIgnoreCase("reload")) {
                    if (args.length == 1) {
                        plugin.reloadConfig();
                        //TODO: Change message to message.yml
                        sender.sendMessage(ChatColor.GREEN + "Reloaded config");
                    }
                    return true;
                }

                //BALANCE COMMAND
                if (args[0].equalsIgnoreCase("balance")) {

                    if (args.length == 1) {
                        sender.sendMessage(ChatColor.RED + "You must specify a player");
                        return false;
                    } else if (args.length == 2) {
                        return getOtherBalance(sender, args);
                    }
                }

                //GIVE COMMAND
                if (args[0].equalsIgnoreCase("give")) {
                    return givePlayerBalance(sender, args);
                }

                //SET COMMAND
                if (args[0].equalsIgnoreCase("set")) {
                    return setPlayerBalance(sender, args);
                }

                //TAKE COMMAND
                if (args[0].equalsIgnoreCase("take")) {
                    return takePlayerBalance(sender, args);
                }

                //ADMIN COMMANDS
                if (args[0].equalsIgnoreCase("admin")) {
                    if (args.length == 3) {
                        //CREATE COMMAND
                        if (args[1].equalsIgnoreCase("create")) {
                            return createCurrency(sender, args);
                        }

                        //DELETE COMMAND
                        if (args[1].equalsIgnoreCase("delete")) {
                            return deleteCurrency(sender, args);
                        }

                        //ICON DELETE COMMAND
                        if (args[1].equalsIgnoreCase("icondel")) {
                            return deleteCurrencyIcon(sender, args);
                        }
                    }

                    //ICON SET COMMAND
                    if (args.length == 4) {
                        if (args[1].equalsIgnoreCase("iconset")) {
                            return setCurrencyIcon(sender, args);
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean givePlayerBalance(CommandSender sender, String[] args) {
        if (args.length == 4) {
            try {
                Player target = Bukkit.getPlayer(args[1]);
                String economyID = args[2];
                int amount = Integer.parseInt(args[3]);

                if (target == null) {
                    //TODO: Change message to message.yml
                    sender.sendMessage(ChatColor.RED + "Player not found");
                    return false;
                }

                plugin.getEconomyHandler().getEconomyImplementer(economyID).depositPlayer(target, amount);
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.GRAY + "You have give §a" + amount + "§7 into §a" + target.getName() + "'s " + economyID + " §7account");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean setPlayerBalance(CommandSender sender, String[] args) {
        if (args.length == 4) {
            try {
                Player target = Bukkit.getPlayer(args[1]);
                String economyID = args[2];
                int amount = Integer.parseInt(args[3]);

                if (target == null) {
                    //TODO: Change message to message.yml
                    sender.sendMessage(ChatColor.RED + "Player not found");
                    return false;
                }

                if (amount < 0) {
                    //TODO: Change message to message.yml
                    sender.sendMessage(ChatColor.RED + "You cannot set a negative amount");
                    return false;
                }

                plugin.getEconomyHandler().getEconomyImplementer(economyID).setBalance(target, amount);
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.GRAY + "You have set §a" + target.getName() + "'s " + economyID + " §7account to §a" + amount);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean takePlayerBalance(CommandSender sender, String[] args) {
        if (args.length == 4) {
            try {
                Player target = Bukkit.getPlayer(args[1]);
                String economyID = args[2];
                int amount = Integer.parseInt(args[3]);

                if (target == null) {
                    //TODO: Change message to message.yml
                    sender.sendMessage(ChatColor.RED + "Player not found");
                    return false;
                }

                if (plugin.getEconomyHandler().getEconomyImplementer(economyID).getBalance(target) < amount) {
                    //TODO: Change message to message.yml
                    sender.sendMessage(ChatColor.RED + "You cannot withdraw more than you have in your account (" + ChatColor.AQUA + plugin.getEconomyHandler().getEconomyImplementer(economyID).getBalance(target) + ChatColor.RED + ")");
                    return false;
                }

                plugin.getEconomyHandler().getEconomyImplementer(economyID).withdrawPlayer(target, amount);
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.GRAY + "You have withdrawn §a$" + amount + "§7 from §a" + target.getName() + "'s " + economyID + " §7account");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean getOtherBalance(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            //TODO: Change message to message.yml
            sender.sendMessage(ChatColor.RED + "Player not found");
            return false;
        }
        try {
            for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
                int balance = (int) economyImplementer.getBalance(target);
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.GREEN + target.getName() + " §7has §a" + balance + "§7 in their " + ChatColor.AQUA + economyImplementer.getId() + ChatColor.GREEN + " account");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean createCurrency(CommandSender sender, String[] args) {
        try {
            String economyID = args[2];
            EconomyImplementer economyImplementer = new EconomyImplementer(plugin, economyID);
            // Get the existing configuration
            FileConfiguration config = plugin.getConfig();
            ConfigurationSection configSection = config.getConfigurationSection("currencies");

            // Check if the economy already exists
            if (!configSection.isConfigurationSection(economyID)) {
                plugin.getEconomyHandler().addEconomyImplementer(economyImplementer); // Add to EconomyHandler
                plugin.getVaultHook().singleHook(economyImplementer); // Hook into Vault
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.GRAY + "You have created a new economy called " + ChatColor.AQUA + economyImplementer.getIcon());

                // Create a section for the new currency
                ConfigurationSection currencySection = configSection.createSection(economyID);

                // Set the 'icon' attribute for the new currency
                currencySection.set("icon", economyID);

                // Save the updated configuration
                plugin.saveConfig();
                plugin.reloadConfig();
            } else {
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.RED + "Economy already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean deleteCurrency(CommandSender sender, String[] args) {
        try {
            String economyID = args[2];
            EconomyImplementer economyImplementer = plugin.getEconomyHandler().getEconomyImplementer(economyID);
            // Get the existing configuration
            FileConfiguration config = plugin.getConfig();
            ConfigurationSection configSection = config.getConfigurationSection("currencies");

            // Check if the economy exists
            if (configSection.isConfigurationSection(economyID)) {
                configSection.set(economyID, null); // Remove the currency section
                plugin.getEconomyHandler().removeEconomyImplementer(economyImplementer); // Remove from EconomyHandler
                plugin.getVaultHook().singleUnhook(economyImplementer); // Unhook from Vault
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.GRAY + "You have deleted the economy called " + ChatColor.AQUA + economyID);

                // Save the updated configuration
                plugin.saveConfig();
                plugin.reloadConfig();
            } else {
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.RED + "Economy does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean deleteCurrencyIcon(CommandSender sender, String[] args) {
        try {
            String economyID = args[2];
            EconomyImplementer economyImplementer = plugin.getEconomyHandler().getEconomyImplementer(economyID);
            // Get the existing configuration
            FileConfiguration config = plugin.getConfig();

            // Check if the economy exists
            if (economyImplementer != null) {
                config.set("currencies." + economyID + ".icon", economyID);
                economyImplementer.setIcon(economyID);
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.GRAY + "You have set the icon for the economy called " + ChatColor.AQUA + economyID);

                // Save the updated configuration
                plugin.saveConfig();
                plugin.reloadConfig();
            } else {
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.RED + "Economy does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean setCurrencyIcon(CommandSender sender, String[] args) {
        try {
            String economyID = args[2];
            String icon = args[3];
            EconomyImplementer economyImplementer = plugin.getEconomyHandler().getEconomyImplementer(economyID);
            // Get the existing configuration
            FileConfiguration config = plugin.getConfig();

            // Check if the economy exists
            if (economyImplementer != null) {
                config.set("currencies." + economyID + ".icon", icon);
                economyImplementer.setIcon(icon);
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.GRAY + "You have set the icon for the economy called " + ChatColor.AQUA + economyID);

                // Save the updated configuration
                plugin.saveConfig();
                plugin.reloadConfig();
            } else {
                //TODO: Change message to message.yml
                sender.sendMessage(ChatColor.RED + "Economy does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @param sender  Người thực hiện lệnh
     * @param command Câu lệnh được thực hiện
     * @param label   Các tên phụ của lệnh
     * @param args    Các đối số được cung cấp cho lệnh
     * @return Danh sách các đối số được đề xuất
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {
            tabComplete.add("admin");
            tabComplete.add("balance");
            tabComplete.add("give");
            tabComplete.add("reload");
            tabComplete.add("set");
            tabComplete.add("take");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("set")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    tabComplete.add(player.getName());
                }
            } else if (args[0].equalsIgnoreCase("balance")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    tabComplete.add(player.getName());
                }
            } else if (args[0].equalsIgnoreCase("admin")) {
                tabComplete.add("create");
                tabComplete.add("delete");
                tabComplete.add("iconset");
                tabComplete.add("icondel");
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("set")) {
                for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
                    tabComplete.add(economyImplementer.getId());
                }
            } else if (args[0].equalsIgnoreCase("admin") && (args[1].equalsIgnoreCase("create") || args[1].equalsIgnoreCase("delete"))) {
                for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
                    tabComplete.add(economyImplementer.getId());
                }
            } else if (args[0].equalsIgnoreCase("admin") && (args[1].equalsIgnoreCase("iconset") || args[1].equalsIgnoreCase("icondel"))) {
                for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
                    tabComplete.add(economyImplementer.getId());
                }
            }
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("set")) {
                tabComplete.add("1");
                tabComplete.add("10");
                tabComplete.add("100");
                tabComplete.add("1000");
                tabComplete.add("10000");
                tabComplete.add("100000");
                tabComplete.add("1000000");
            } else if (args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("iconset")) {
                tabComplete.add("<icon>");
            }
        }
        return tabComplete;
    }
}