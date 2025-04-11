package pl.chudziudgi.lifesteal.feature.antivoid;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;

public class AntiVoidTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final PluginConfiguration pluginConfiguration;

    public AntiVoidTask(SurvivalPlugin survivalPlugin, PluginConfiguration pluginConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.pluginConfiguration = pluginConfiguration;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 5, 20);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getLocation().getY() < -60) {
                if (pluginConfiguration.location == null) return;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(pluginConfiguration.location);
                    }
                }.runTask(this.survivalPlugin);
            }
        });
    }
}
