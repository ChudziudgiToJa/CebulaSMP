package pl.chudziudgi.lifesteal.feature.boss;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;

public class BossHealthBossBarTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final BossManager bossManager;

    public BossHealthBossBarTask(SurvivalPlugin survivalPlugin, BossManager bossManager) {
        this.survivalPlugin = survivalPlugin;
        this.bossManager = bossManager;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 0, 20);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            BossBarManager.refreshBar(this.bossManager, player);
        });
    }
}
