package pl.chudziudgi.lifesteal.feature.autorestart;

import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.time.*;

public class AutoRestartTask {

    private final JavaPlugin plugin;

    public AutoRestartTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void scheduleNextRestart() {
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime now = ZonedDateTime.now(zone);
        ZonedDateTime nextRun = now.withHour(5).withMinute(0).withSecond(0).withNano(0);

        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }

        long delaySeconds = Duration.between(now, nextRun).getSeconds();
        long delayTicks = delaySeconds * 20;

        new BukkitRunnable() {
            @Override
            public void run() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.shutdown();
                    }
                }.runTask(plugin);
            }
        }.runTaskLaterAsynchronously(plugin, delayTicks);

        scheduleWarningAsync(delayTicks, 10);
        scheduleWarningAsync(delayTicks, 5);
        scheduleWarningAsync(delayTicks, 1);
    }

    private void scheduleWarningAsync(long restartDelayTicks, int minutesBefore) {
        long warningTicks = restartDelayTicks - (minutesBefore * 60L * 20L);
        if (warningTicks < 0) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getOnlinePlayers().forEach(player -> MessageUtil.sendTitle(player, "&4&lRESTART", "&frestart nastąpi za " + minutesBefore, 20,50,20));
                    }
                }.runTask(plugin);
            }
        }.runTaskLaterAsynchronously(plugin, warningTicks);
    }
}