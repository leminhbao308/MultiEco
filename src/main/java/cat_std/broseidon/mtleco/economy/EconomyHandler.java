package cat_std.broseidon.mtleco.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Class này dùng để quản lý các loại tiền tệ trong plugin
 * Nó sẽ lưu trữ các loại tiền tệ và các thông tin của chúng
 * Nó cũng sẽ quản lý các sự kiện liên quan đến tiền tệ
 *
 * @author Broseidon
 */
public class EconomyHandler {
    private final List<EconomyImplementer> economyImplementers;


    public EconomyHandler(List<EconomyImplementer> economyImplementers) {
        this.economyImplementers = economyImplementers;
    }

    /**
     * Thêm một loại tiền tệ vào EconomyHandler
     * @param economyImplementer Loại tiền tệ mới
     */
    public void addEconomyImplementer(EconomyImplementer economyImplementer) {
        if (economyImplementers.contains(economyImplementer)) {
            return;
        }
        economyImplementers.add(economyImplementer);
        for (Player player : Bukkit.getOnlinePlayers()) {
            economyImplementer.setBalance(player, 0);
        }
    }

    /**
     * Xóa một loại tiền tệ khỏi EconomyHandler
     * @param economyImplementer Loại tiền tệ cần xóa
     */
    public void removeEconomyImplementer(EconomyImplementer economyImplementer) {
        if (!economyImplementers.contains(economyImplementer)) {
            return;
        }
        economyImplementers.remove(economyImplementer);
    }

    /**
     * Lấy một loại tiền tệ từ EconomyHandler
     * @param id ID của loại tiền tệ cần lấy
     * @return Loại tiền tệ cần lấy
     */
    public EconomyImplementer getEconomyImplementer(String id) {
        for (EconomyImplementer economyImplementer : economyImplementers) {
            if (economyImplementer.getId().equals(id)) {
                return economyImplementer;
            }
        }
        return null;
    }

    /**
     * Lấy danh sách các loại tiền tệ trong EconomyHandler
     * @return Danh sách các loại tiền tệ trong EconomyHandler
     */
    public List<EconomyImplementer> getEconomyImplementers() {
        return economyImplementers;
    }
}
