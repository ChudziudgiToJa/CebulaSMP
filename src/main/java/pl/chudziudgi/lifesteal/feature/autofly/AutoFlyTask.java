package pl.chudziudgi.lifesteal.feature.autofly;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class AutoFlyTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;

    public AutoFlyTask(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 5, 5);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getWorld().getName().equals("spawn")) {
                if (!player.hasPermission("cebulasmp.spawn.fly")) return;
                if (!player.getAllowFlight()) {
                    MessageUtil.sendTitle(player, "", "&awłączono automatyczy fly dla rang", 20, 50, 20);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setAllowFlight(true);
                        }
                    }.runTask(this.survivalPlugin);
                }
            } else {
                if (player.getAllowFlight() && player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR && !player.hasPermission("cebula.admin")) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setAllowFlight(false);
                            player.setFlying(false);
                        }
                    }.runTask(this.survivalPlugin);
                }
            }
        });
    }
}
