package cat_std.broseidon.mtleco.economy;

import java.util.ArrayList;
import java.util.List;

public class EconomyHandler {
    private final List<EconomyImplementer> economyImplementers;

    public EconomyHandler() {
        this.economyImplementers = new ArrayList<>();
    }

    public EconomyHandler(List<EconomyImplementer> economyImplementers) {
        this.economyImplementers = economyImplementers;
    }

    public boolean addEconomyImplementer(EconomyImplementer economyImplementer) {
        if (economyImplementers.contains(economyImplementer)) {
            return false;
        }
        economyImplementers.add(economyImplementer);
        return true;
    }

    public boolean removeEconomyImplementer(EconomyImplementer economyImplementer) {
        if (!economyImplementers.contains(economyImplementer)) {
            return false;
        }
        economyImplementers.remove(economyImplementer);
        return true;
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
