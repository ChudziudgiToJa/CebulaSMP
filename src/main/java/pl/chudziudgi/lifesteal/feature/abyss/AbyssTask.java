package pl.chudziudgi.lifesteal.feature.abyss;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class AbyssTask extends BukkitRunnable {
    private static final int START_TIME = 1000;
    private final SurvivalPlugin survivalPlugin;

    public AbyssTask(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 20);
    }

    @Override
    public void run() {
        if (AbyssManager.time <= 0) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player, Sound.BLOCK_BELL_RESONATE, 5, 5);
                MessageUtil.sendTitle(player, "", "&7Wszystkie byty zostały skutecznie usunięte. &2\uD83C\uDF0D", 20, 50, 20);
            });
            Bukkit.getScheduler().runTask(survivalPlugin, () -> {
                Bukkit.getWorlds().forEach(world ->
                        world.getEntities().stream()
                                .filter(entity ->
                                        entity instanceof Item)
                                .forEach(Entity::remove)
                );
            });
            AbyssManager.time = START_TIME;
        } else {
            AbyssManager.time--;
        }
    }
}
