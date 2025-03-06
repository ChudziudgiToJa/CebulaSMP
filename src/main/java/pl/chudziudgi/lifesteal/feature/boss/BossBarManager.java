package pl.chudziudgi.lifesteal.feature.boss;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.nether.NetherBossBarManager;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class BossBarManager {

    private  static BossBar bossBar;

    public static void createBar(BossManager bossManager) {
        bossBar = Bukkit.createBossBar(
                MessageUtil.smallTextToColor("&0&lBOSS &8- &f%s %s %s".formatted(
                        (int) bossManager.getBoss().getLocation().getX(),
                        (int) bossManager.getBoss().getLocation().getY(),
                        (int) bossManager.getBoss().getLocation().getZ()
                )),
                BarColor.RED,
                BarStyle.SOLID
        );
        Bukkit.getOnlinePlayers().forEach(BossBarManager::addBossBar);
    }

    public static void refreshBar(BossManager bossManager, Player player) {
        if (bossBar != null && bossManager.getBoss() != null) {
            bossBar.setTitle(MessageUtil.smallTextToColor("&0&lBOSS &8- &f%s %s %s".formatted(
                    (int) bossManager.getBoss().getLocation().getX(),
                    (int) bossManager.getBoss().getLocation().getY(),
                    (int) bossManager.getBoss().getLocation().getZ()
            )));
            bossBar.setProgress(Math.max(0, 1 - (bossManager.getBoss().getHealth() / (int) bossManager.getBoss().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue())));
        } else {
            BossBarManager.removeBossBar(player);
        }
    }

    public static void removeBossBar(Player player) {
        if (bossBar == null) return;
        NetherBossBarManager.removeBossBar(player.getUniqueId());
        bossBar.removePlayer(player);
    }

    public static void addBossBar(Player player) {
        if (bossBar == null) return;
        NetherBossBarManager.addBossBar(player.getUniqueId(), bossBar);
        bossBar.addPlayer(player);
    }
}
