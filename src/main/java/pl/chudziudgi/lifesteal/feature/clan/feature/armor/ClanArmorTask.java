package pl.chudziudgi.lifesteal.feature.clan.feature.armor;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;

public class ClanArmorTask extends BukkitRunnable {

    private final ClanService clanService;

    public ClanArmorTask(final SurvivalPlugin survivalPlugin, ClanService clanService) {
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(survivalPlugin, 40, 40);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getWorld().getPlayers().stream()
                    .filter(nearbyPlayer -> nearbyPlayer.getLocation().distance(player.getLocation()) < 30)
                    .filter(nearbyPlayer -> !nearbyPlayer.equals(player))
                    .forEach(nearbyPlayer -> {
                        Clan playerClan = this.clanService.findClanByMember(player.getName());
                        Clan nearbyPlayerClan = this.clanService.findClanByMember(nearbyPlayer.getName());
                        if (playerClan != null && playerClan.equals(nearbyPlayerClan)) {
                            ClanArmorHandler.sendArmorPacket(player, nearbyPlayer);
                        }
                    });
        });
    }
}