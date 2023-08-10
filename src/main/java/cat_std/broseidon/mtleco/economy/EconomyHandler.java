package cat_std.broseidon.mtleco.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class EconomyHandler {
    private final List<EconomyImplementer> economyImplementers;


    public EconomyHandler(List<EconomyImplementer> economyImplementers) {
        this.economyImplementers = economyImplementers;
    }

    public void addEconomyImplementer(EconomyImplementer economyImplementer) {
        if (economyImplementers.contains(economyImplementer)) {
            return;
        }
        economyImplementers.add(economyImplementer);
        for (Player player : Bukkit.getOnlinePlayers()) {
            economyImplementer.setBalance(player, 0);
        }
    }

    public void removeEconomyImplementer(EconomyImplementer economyImplementer) {
        if (!economyImplementers.contains(economyImplementer)) {
            return;
        }
        economyImplementers.remove(economyImplementer);
    }

    public EconomyImplementer getEconomyImplementer(String id) {
        for (EconomyImplementer economyImplementer : economyImplementers) {
            if (economyImplementer.getId().equals(id)) {
                return economyImplementer;
            }
        }
        return null;
    }

    public List<EconomyImplementer> getEconomyImplementers() {
        return economyImplementers;
    }
}
