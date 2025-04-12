package pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.particle;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;

public class ClanCuboidBorderParticleTask extends BukkitRunnable {

    private final ClanService clanService;

    public ClanCuboidBorderParticleTask(ClanService clanService, final SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 2);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Clan clan : clanService.getAllClans()) {
                Location clanLocation = clan.getBukkitLocation();
                if (isPlayerAtClanBorder(player, clan)) {
                    double centerX = clanLocation.getX() + 0.5;
                    double centerZ = clanLocation.getZ() + 0.5;
                    double size = clan.getClanLevelType().getSize();
                    for (double i = -size; i <= size; i += 0.8) {
                        spawnParticle(player, clan, centerX + i, centerZ - size);
                        spawnParticle(player, clan, centerX + i, centerZ + size);
                        spawnParticle(player, clan, centerX - size, centerZ + i);
                        spawnParticle(player, clan, centerX + size, centerZ + i);
                    }
                }
            }
        }
    }

    private boolean isPlayerAtClanBorder(final Player player, final Clan clan) {
        double dx = player.getLocation().getX() - clan.getLocation().getX();
        double dz = player.getLocation().getZ() - clan.getLocation().getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        double borderRadius = clan.getClanLevelType().getSize();

        return distance >= (borderRadius - 30) && distance <= (borderRadius + 30);
    }

    private void spawnParticle(Player player, Clan clan, double x, double z) {
        Location particleLocation = new Location(player.getWorld(), x, player.getLocation().getY() + 1, z);

        if (clan.containsMemberByUUID(player.getUniqueId().toString())) {
            player.spawnParticle(Particle.DUST, particleLocation, 1, 0, 0, 0, 0,
                    new Particle.DustOptions(Color.fromRGB(0, 255, 0), 1));
        } else {
            player.spawnParticle(Particle.DUST, particleLocation, 1, 0, 0, 0, 0,
                    new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1));
        }
    }
}
