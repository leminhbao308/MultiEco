package cat_std.broseidon.mtleco.commands;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommands implements CommandExecutor {

    private MultiEco plugin;

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
                    if (args.length == 1) {
                        plugin.reloadConfig();
                        player.sendMessage(ChatColor.GREEN + "Reloaded config");
                    }
                    return true;
                }

                //GIVE COMMAND
                if (args[0].equalsIgnoreCase("give")) {
                    if (args.length == 4) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            String economyID = args[2];
                            int depositAmount = Integer.parseInt(args[3]);

                            plugin.getEconomyHandler().getEconomyImplementer(economyID).depositPlayer(target, depositAmount);
                            player.sendMessage(ChatColor.GRAY + "You have give §a" + depositAmount + "§7 into §a" + target.getName() + "'s " + economyID + " §7account");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }

                //BALANCE COMMAND
                if (args[0].equalsIgnoreCase("balance")) {
                    if (args.length == 1) {
                        try {
                            for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
                                int balance = (int) economyImplementer.getBalance(player);
                                player.sendMessage(ChatColor.GREEN + player.getName() + " §7has §a" + balance + "§7 in their " + ChatColor.AQUA + economyImplementer.getId() + ChatColor.GREEN + " account");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }

                //TAKE COMMAND
                if (args[0].equalsIgnoreCase("take")) {
                    if (args.length == 4) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            String economyID = args[2];
                            int withdrawAmount = Integer.parseInt(args[3]);

                            if (plugin.getEconomyHandler().getEconomyImplementer(economyID).getBalance(target) < withdrawAmount) {
                                player.sendMessage(ChatColor.RED + "You cannot withdraw more than you have in your account (" + ChatColor.AQUA + plugin.getEconomyHandler().getEconomyImplementer(economyID).getBalance(target) + ChatColor.RED + ")");
                                return true;
                            }

                            plugin.getEconomyHandler().getEconomyImplementer(economyID).withdrawPlayer(target, withdrawAmount);
                            player.sendMessage(ChatColor.GRAY + "You have withdrawn §a$" + withdrawAmount + "§7 from §a" + target.getName() + "'s " + economyID + " §7account");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }

                //SET COMMAND
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length == 4) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            String economyID = args[2];
                            int setAmount = Integer.parseInt(args[3]);

                            if (target == null) {
                                player.sendMessage(ChatColor.RED + "Player not found");
                                return true;
                            }

                            if (setAmount < 0) {
                                player.sendMessage(ChatColor.RED + "You cannot set a negative amount");
                                return true;
                            }

                            plugin.getEconomyHandler().getEconomyImplementer(economyID).setBalance(target, setAmount);
                            player.sendMessage(ChatColor.GRAY + "You have set §a" + target.getName() + "'s " + economyID + " §7account to §a" + setAmount);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            }
        }
        return true;
    }
}