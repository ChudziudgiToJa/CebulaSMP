package pl.chudziudgi.lifesteal.feature.check;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class CheckTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final CheckService checkService;

    public CheckTask(SurvivalPlugin survivalPlugin, CheckService checkService) {
        this.survivalPlugin = survivalPlugin;
        this.checkService = checkService;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 20,20*5);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.checkService.contains(player.getUniqueId())) {
                MessageUtil.sendTitle(player, "&aUWAGA jestes sprawdzany/a", "&7masz 5min wejscie na poczekalnie /dc",1,20,1);
            }
        });

    }
}
