package cat_std.broseidon.mtleco.commands;

import cat_std.broseidon.mtleco.MultiEco;
import cat_std.broseidon.mtleco.economy.EconomyImplementer;
import cat_std.broseidon.mtleco.utils.ColorCode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class này dùng để quản lý lệnh /pay trong plugin
 * Lệnh /pay dùng để chuyển tiền cho người chơi khác
 *
 * @author Broseidon
 */
public class PayCommand implements CommandExecutor, TabCompleter {

    private final MultiEco plugin;

    public PayCommand(MultiEco plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mtleco.pay")) {
            sender.sendMessage(ColorCode.translate(ColorCode.autoReplace(plugin.getMessageManager().getNoPermission(), sender.getName(), "", "", 0)));
            return true;
        }

        //Thực hiện lệnh khi người chơi gõ /pay <tên người chơi> <tên tiền tệ> <số tiền>
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

                                player.sendMessage(ColorCode.translate(ColorCode.autoReplace(plugin.getMessageManager().getPayMessage(), target.getName(), plugin.getEconomyHandler().getEconomyImplementer(args[1]).getName(), plugin.getEconomyHandler().getEconomyImplementer(args[1]).getIcon(), Integer.parseInt(args[2]))));
                                target.sendMessage(ColorCode.translate(ColorCode.autoReplace(plugin.getMessageManager().getReceivedMessage(), player.getName(), plugin.getEconomyHandler().getEconomyImplementer(args[1]).getName(), plugin.getEconomyHandler().getEconomyImplementer(args[1]).getIcon(), Integer.parseInt(args[2]))));
                            } else {
                                player.sendMessage(ColorCode.translate(ColorCode.autoReplace(plugin.getMessageManager().getNotEnoughMoney(), player.getName(), plugin.getEconomyHandler().getEconomyImplementer(args[1]).getName(), plugin.getEconomyHandler().getEconomyImplementer(args[1]).getIcon(), Integer.parseInt(args[2]))));
                            }
                        } else {
                            player.sendMessage(ColorCode.translate(ColorCode.autoReplace(plugin.getMessageManager().getCurrencyNotExists(), player.getName(), args[1], args[1], Integer.parseInt(args[2]))));
                        }
                    } else {
                        player.sendMessage(ColorCode.translate(ColorCode.autoReplace(plugin.getMessageManager().getNotOnline(), args[0], args[1], args[1], Integer.parseInt(args[2]))));
                    }
                }
            }
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
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                tabComplete.add(player.getName());
            }
        } else if (args.length == 2) {
            for (EconomyImplementer economyImplementer : plugin.getEconomyHandler().getEconomyImplementers()) {
                tabComplete.add(economyImplementer.getId());
            }
        } else if (args.length == 3) {
            tabComplete.add("1");
            tabComplete.add("10");
            tabComplete.add("100");
            tabComplete.add("1000");
            tabComplete.add("10000");
            tabComplete.add("100000");
            tabComplete.add("1000000");
        }
        return tabComplete;
    }
}
