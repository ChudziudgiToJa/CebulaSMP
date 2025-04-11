package pl.chudziudgi.lifesteal.feature.end.world;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.configuration.implementation.WorldsSettings;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class EndManager {

    private final WorldsSettings worldsSettings;

    private final BossBar bossBar =  Bukkit.createBossBar(
            MessageUtil.smallTextToColor("&dEnd jest &awłączony"),
            BarColor.RED,
            BarStyle.SOLID
    );

    public EndManager(WorldsSettings worldsSettings) {
        this.worldsSettings = worldsSettings;
    }

    public void toggleNetherBossBar() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.worldsSettings.endJoinStatus) {
                EndBossBarManager.addBossBar(player.getUniqueId(), this.bossBar);
                this.bossBar.addPlayer(player);
            } else {
                EndBossBarManager.removeBossBar(player.getUniqueId());
                this.bossBar.removePlayer(player);
            }
        });
    }

    public void removeBossBar(Player player) {
        EndBossBarManager.removeBossBar(player.getUniqueId());
        this.bossBar.removePlayer(player);
    }

    public void addBossBar(Player player) {
        EndBossBarManager.addBossBar(player.getUniqueId(), this.bossBar);
        this.bossBar.addPlayer(player);
    }
}
