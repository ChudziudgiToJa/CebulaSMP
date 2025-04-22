package pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.combat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.feature.combatlogout.CombatLogoutManager;

public class ClanCuboidCombatLogoutPushTask extends BukkitRunnable {

    private final ClanService clanService;
    private final CombatLogoutManager combatLogoutManager;
    private final SurvivalPlugin survivalPlugin;

    public ClanCuboidCombatLogoutPushTask(final SurvivalPlugin survivalPlugin, ClanService clanService, CombatLogoutManager combatLogoutManager) {
        this.survivalPlugin = survivalPlugin;
        this.clanService = clanService;
        this.combatLogoutManager = combatLogoutManager;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 2);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!player.isOnline() || player.isDead()) return;

            if (this.combatLogoutManager.inCombat(player)) {
                Location playerLocation = player.getLocation();

                if (this.clanService.isLocationOnClanCuboid(playerLocation)) {
                    Clan clan = this.clanService.findClanByLocation(playerLocation);
                    if (clan == null) return;

                    Location center = clan.getBukkitLocation();
                    Vector direction = playerLocation.toVector().subtract(center.toVector()).normalize();
                    Vector pushBack = direction.multiply(1.5);

                    Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                        player.setVelocity(pushBack.setY(0));
                    });
                }
            }
        });
    }

}
