package pl.chudziudgi.lifesteal.feature.end;

import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.WorldsSettings;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class EndStatusTask {

    private final SurvivalPlugin survivalPlugin;
    private final WorldsSettings worldsSettings;

    public EndStatusTask(SurvivalPlugin survivalPlugin, WorldsSettings worldsSettings) {
        this.survivalPlugin = survivalPlugin;
        this.worldsSettings = worldsSettings;
    }

    public void scheduleDailyTasks() {
        ZoneId polandZone = ZoneId.of("Europe/Warsaw");

        new BukkitRunnable() {
            @Override
            public void run() {
                worldsSettings.setEndJoinStatus(true);
            }
        }.runTaskTimerAsynchronously(survivalPlugin, getTicksUntilNextTime(19, polandZone), 24 * 60 * 60 * 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                worldsSettings.setEndJoinStatus(false);
            }
        }.runTaskTimerAsynchronously(survivalPlugin, getTicksUntilNextTime(21, polandZone), 24 * 60 * 60 * 20L);
    }

    private long getTicksUntilNextTime(int hour, ZoneId zoneId) {
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        ZonedDateTime nextRun = now.withHour(hour).withMinute(0).withSecond(0).withNano(0);

        if (now.isAfter(nextRun) || now.equals(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }

        long secondsUntilNext = ChronoUnit.SECONDS.between(now, nextRun);
        return secondsUntilNext * 20L;
    }
}