package pl.chudziudgi.lifesteal.feature.boss;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.nether.NetherBossBarManager;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class BossBarManager {

    private final BossManager bossManager;
    private BossBar bossBar;

    public BossBarManager(BossManager bossManager) {
        this.bossManager = bossManager;
        if (bossManager.getBoss() != null) {
            createBar();
        }
    }

    public void createBar() {
        if (bossManager.getBoss() == null) {
            return;
        }

        bossBar = Bukkit.createBossBar(
                MessageUtil.smallTextToColor("&0&lBOSS &8- &f%s %s %s".formatted(
                        bossManager.getBoss().getLocation().getX(),
                        bossManager.getBoss().getLocation().getY(),
                        bossManager.getBoss().getLocation().getZ()
                )),
                BarColor.RED,
                BarStyle.SOLID
        );
        Bukkit.getOnlinePlayers().forEach(this::addBossBar);
    }

    public void refreshBar() {
        if (bossBar != null && bossManager.getBoss() != null) {
            bossBar.setTitle(MessageUtil.smallTextToColor("&0&lBOSS &8- &f%s %s %s".formatted(
                    bossManager.getBoss().getLocation().getX(),
                    bossManager.getBoss().getLocation().getY(),
                    bossManager.getBoss().getLocation().getZ()
            )));
        }
    }

    public void toggleNetherBossBar() {
        if (bossBar == null) return;

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (bossManager.getBoss() != null) {
                NetherBossBarManager.addBossBar(player.getUniqueId(), bossBar);
                bossBar.addPlayer(player);
            } else {
                NetherBossBarManager.removeBossBar(player.getUniqueId());
                bossBar.removePlayer(player);
            }
        });
    }

    public void removeBossBar(Player player) {
        if (bossBar == null) return;

        NetherBossBarManager.removeBossBar(player.getUniqueId());
        bossBar.removePlayer(player);
    }

    public void addBossBar(Player player) {
        if (bossBar == null) return;

        NetherBossBarManager.addBossBar(player.getUniqueId(), bossBar);
        bossBar.addPlayer(player);
    }
}
