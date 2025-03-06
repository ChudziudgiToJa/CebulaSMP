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

    private static BossBar bossBar;

    public static void createBar(BossManager bossManager) {
        bossBar = Bukkit.createBossBar(
                MessageUtil.smallTextToColor("&9&lWARDEN &4&l❤ %s  &8| &fx%s y%s z%s".formatted(
                        (int) bossManager.getBoss().getHealth(),
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
            double maxHealth = bossManager.getBoss().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            double currentHealth = bossManager.getBoss().getHealth();

            bossBar.setTitle(MessageUtil.smallTextToColor("&9&lWARDEN &4&l❤ %s  &8| &fx%s y%s z%s".formatted(
                    (int) bossManager.getBoss().getHealth(),
                    (int) bossManager.getBoss().getLocation().getX(),
                    (int) bossManager.getBoss().getLocation().getY(),
                    (int) bossManager.getBoss().getLocation().getZ()
            )));

            bossBar.setProgress(Math.max(0, currentHealth / maxHealth));
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

    public static void removeBossBarFromAll() {
        if (bossBar == null) return;
        Bukkit.getOnlinePlayers().forEach(bossBar::removePlayer);
        bossBar = null;
    }
}