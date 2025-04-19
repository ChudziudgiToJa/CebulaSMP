package pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;

public class ClanCuboidBossBarTak extends BukkitRunnable {

    private final ClanService clanService;

    public ClanCuboidBossBarTak(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 5);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                Clan clan = this.clanService.findClanByLocation(player.getLocation());
                if (clan == null) {
                    removeBossBar(player);
                    return;
                }
                handleBossBarForMember(player, clan);
            }
        });
    }


    private void handleBossBarForMember(Player player, Clan clan) {
        double distance = player.getLocation().distance(
                new Location(
                        Bukkit.getWorlds().getFirst(),
                        clan.getBukkitLocation().getX(),
                        player.getLocation().getY(),
                        clan.getBukkitLocation().getZ()
                )
        );
        double progress = Math.max(0, 1 - (distance / 20.0));

        boolean isMember = clan.containsMemberByUUID(player.getUniqueId().toString());
        String message;
        BarColor color;

        if (isMember) {
            message = String.format(
                    "§aᴊᴇsᴛᴇś ɴᴀ ᴛᴇʀᴇɴɪᴇ sᴡᴏᴊᴇɢᴏ ᴋʟᴀɴᴜ §8| §a§l%s §7(%.1f ᴍ ᴏᴅ śʀᴏᴅᴋᴀ ᴋʟᴀɴᴜ)",
                    clan.getTag(), distance
            );
            color = BarColor.GREEN;
        } else {
            message = String.format(
                    "§cᴊᴇsᴛᴇś ɴᴀ ᴛᴇʀᴇɴɪᴇ ᴡʀᴏɢɪᴇɢᴏ ᴋʟᴀɴᴜ §8| §4§l%s",
                    clan.getTag()
            );
            color = BarColor.RED;
        }

        BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(message, color, BarStyle.SEGMENTED_10);
            bossBar.addPlayer(player);
            ClanCuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
        } else {
            bossBar.setTitle(message);
            bossBar.setColor(color);
        }
        bossBar.setProgress(progress);
    }

    private void removeBossBar(Player player) {
        BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removePlayer(player);
            ClanCuboidBossBarManager.removeBossBar(player.getUniqueId());
        }
    }
}