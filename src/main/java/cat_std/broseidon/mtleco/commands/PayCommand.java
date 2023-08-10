package cat_std.broseidon.mtleco.commands;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PayCommand implements CommandExecutor, TabCompleter {

    private final MultiEco plugin;

    public PayCommand(MultiEco plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mtleco.pay")) {
            //TODO: Change message to message.yml
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("pay")) {
                if (args.length == 3) {
                    Player player = (Player) sender;
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if (target != null) {
                        if (plugin.getEconomyHandler().getEconomyImplementer(args[1]) != null) {
                            if (plugin.getEconomyHandler().getEconomyImplementer(args[1]).getBalance(player) >= Double.parseDouble(args[2])) {
                                plugin.getEconomyHandler().getEconomyImplementer(args[1]).withdrawPlayer(player, Double.parseDouble(args[2]));
                                plugin.getEconomyHandler().getEconomyImplementer(args[1]).depositPlayer(target, Double.parseDouble(args[2]));
                                //TODO: Change message to message.yml
                                player.sendMessage("§aYou have sent §e" + args[2] + " " + plugin.getEconomyHandler().getEconomyImplementer(args[1]).getName() + "§a to §e" + target.getName() + "§a.");
                                target.sendMessage("§aYou have received §e" + args[2] + " " + plugin.getEconomyHandler().getEconomyImplementer(args[1]).getName() + "§a from §e" + player.getName() + "§a.");
                            } else {
                                //TODO: Change message to message.yml
                                player.sendMessage("§cYou do not have enough money.");
                            }
                        } else {
                            //TODO: Change message to message.yml
                            player.sendMessage("§cThe currency does not exist.");
                        }
                    } else {
                        //TODO: Change message to message.yml
                        player.sendMessage("§cThe target is not online.");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                tabComplete.add(player.getName());
            }
        } else if (args.length == 2) {
            for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
                tabComplete.add(economyImplementer.getId());
            }
        }
        return tabComplete;
    }
}
