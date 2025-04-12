package pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.portal;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class ClanCuboidPortal extends BukkitRunnable {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;

    public ClanCuboidPortal(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 2);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Clan clan = this.clanService.findClanByMember(player.getUniqueId());
            if (!isInKlanRegion(player)) {
                return;
            }
            if (clan == null) {
                MessageUtil.sendTitle(player, "", "&cNie masz klanu.", 20, 50, 20);
                return;
            }
            Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                player.teleport(clan.getBukkitTeleportLocation());
            });
        });
    }

    private boolean isInKlanRegion(Player player) {
        Location location = player.getLocation();
        com.sk89q.worldguard.protection.regions.RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));

        if (regions == null) return false;

        ApplicableRegionSet set = regions.getApplicableRegions(BukkitAdapter.asBlockVector(location));
        for (ProtectedRegion region : set) {
            if (region.getId().equalsIgnoreCase("klan")) {
                return true;
            }
        }
        return false;
    }
}

