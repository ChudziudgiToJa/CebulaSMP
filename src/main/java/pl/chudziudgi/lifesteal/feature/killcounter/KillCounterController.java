package pl.chudziudgi.lifesteal.feature.killcounter;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitTask;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillCounterController implements Listener {
    private final SurvivalPlugin survivalPlugin;
    private final Map<UUID, Integer> killCounter = new HashMap<>();
    private final Map<UUID, BukkitTask> resetTasks = new HashMap<>();

    public KillCounterController(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killerEntity = player.getKiller();

        if (!(killerEntity instanceof Player killer)) return;

        UUID killerId = killer.getUniqueId();
        int kills = killCounter.getOrDefault(killerId, 0);
        kills++;
        killCounter.put(killerId, kills);

        if (resetTasks.containsKey(killerId)) {
            resetTasks.get(killerId).cancel();
        }

        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(
                this.survivalPlugin,
                () -> {
                    killCounter.remove(killerId);
                    resetTasks.remove(killerId);
                },
                20L * 60
        );
        resetTasks.put(killerId, task);

        switch (kills) {
            case 1 -> MessageUtil.sendTitle(killer, "&2&lzabiłeś/aś", "&agracza &f" + player.getName(), 20, 50, 20);
            case 2 -> MessageUtil.sendTitle(killer, "&b&lDouble Kill", "&azabiłeś/aś gracza &f" + player.getName(), 20, 50, 20);
            case 3 -> MessageUtil.sendTitle(killer, "&b&lTriple kill", "&azabiłeś/aś gracza &f" + player.getName(), 20, 50, 20);
            case 4 -> MessageUtil.sendTitle(killer, "&b&lQuadra kill", "&azabiłeś/aś gracza &f" + player.getName(), 20, 50, 20);
            case 5 -> MessageUtil.sendTitle(killer, "&b&lPenta kill", "&azabiłeś/aś gracza &f" + player.getName(), 20, 50, 20);
            default -> MessageUtil.sendTitle(killer, "&b&lmulti KILL", "&azabiłeś/aś gracza &f" + player.getName(), 20, 50, 20);
        }
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
    }
}