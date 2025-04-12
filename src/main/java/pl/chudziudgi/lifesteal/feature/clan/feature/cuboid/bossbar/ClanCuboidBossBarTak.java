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

import java.util.ArrayList;
import java.util.List;

public class ClanCuboidBossBarTak extends BukkitRunnable {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;

    public ClanCuboidBossBarTak(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 2);
    }

    @Override
    public void run() {
        this.clanService.getAllClans().forEach(clan -> {
            Location clanCenter = clan.getBukkitLocation();
            List<Player> players = new ArrayList<>(clanCenter.getWorld().getPlayers());
            players.forEach(player -> {
                        if (clanService.isLocationOnClanCuboid(player.getLocation())) {
                            Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                                if (clan.containsMemberByUUID(player.getUniqueId().toString())) {
                                    handleBossBarForMember(player, clan);
                                } else {
                                    handleBossBarForEnemy(player, clan);
                                }
                            });
                        } else {
                            removeBossBar(player);
                        }
                    });
        });
    }


    private void handleBossBarForMember(Player player, Clan clan) {
        double distance = player.getLocation().distance(new Location(Bukkit.getWorlds().getFirst(), clan.getBukkitLocation().getX(), player.getLocation().getY(), clan.getBukkitLocation().getZ()));
        double progress = Math.max(0, 1 - (distance / 20.0));

        String bossBarMessage = String.format(
                "§aᴊᴇsᴛᴇś ɴᴀ ᴛᴇʀᴇɴɪᴇ sᴡᴏᴊᴇɢᴏ ᴋʟᴀɴᴜ §8| §a§l%s §7(%.1f ᴍ ᴏᴅ śʀᴏᴅᴋᴀ ᴋʟᴀɴᴜ)",
                clan.getTag(), distance
        );

        BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(bossBarMessage, BarColor.GREEN, BarStyle.SEGMENTED_10);
            bossBar.addPlayer(player);
            ClanCuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
        }
        bossBar.setTitle(bossBarMessage);
        bossBar.setProgress(progress);
    }

    private void handleBossBarForEnemy(Player player, Clan clan) {
        String bossBarMessage = String.format(
                "§cᴊᴇsᴛᴇś ɴᴀ ᴛᴇʀᴇɴɪᴇ ᴡʀᴏɢɪᴇɢᴏ ᴋʟᴀɴᴜ §8| §4§l%s",
                clan.getTag()
        );

        BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(bossBarMessage, BarColor.RED, BarStyle.SEGMENTED_10);
            bossBar.addPlayer(player);
            ClanCuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
        }
        bossBar.setTitle(bossBarMessage);
        bossBar.setProgress(1);
    }

    private void removeBossBar(Player player) {
        BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removePlayer(player);
            ClanCuboidBossBarManager.removeBossBar(player.getUniqueId());
        }
    }
}