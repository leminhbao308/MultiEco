package cat_std.broseidon.mtleco.utils;

import cat_std.broseidon.mtleco.MultiEco;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSaveTask extends BukkitRunnable {

    private final MultiEco plugin;

    public AutoSaveTask(MultiEco plugin) {
        this.plugin = plugin;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}
