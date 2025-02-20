package pl.chudziudgi.lifesteal.feature.nether;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.configuration.implementation.WorldsSettings;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class NetherManager {

    private final WorldsSettings worldsSettings;

    private final BossBar bossBar =  Bukkit.createBossBar(
            MessageUtil.smallTextToColor("&cNether jest &awłączony"),
            BarColor.RED,
            BarStyle.SOLID
    );

    public NetherManager(WorldsSettings worldsSettings) {
        this.worldsSettings = worldsSettings;
    }

    public void toggleNetherBossBar() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.worldsSettings.netherJoinStatus) {
                NetherBossBarManager.addBossBar(player.getUniqueId(), this.bossBar);
                this.bossBar.addPlayer(player);
            } else {
                NetherBossBarManager.removeBossBar(player.getUniqueId());
                this.bossBar.removePlayer(player);
            }
        });
    }

    public void removeBossBar(Player player) {
        NetherBossBarManager.removeBossBar(player.getUniqueId());
        this.bossBar.removePlayer(player);
    }

    public void addBossBar(Player player) {
        NetherBossBarManager.addBossBar(player.getUniqueId(), this.bossBar);
        this.bossBar.addPlayer(player);
    }
}
