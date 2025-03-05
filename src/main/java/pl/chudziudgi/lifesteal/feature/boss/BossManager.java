package pl.chudziudgi.lifesteal.feature.boss;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Warden;

@Getter
public class BossManager {

    private final BossBarManager bossBarManager;

    public Warden boss;

    public BossManager(BossBarManager bossBarManager) {
        this.bossBarManager = bossBarManager;
    }

    public void spawn(Location location) {
        if (boss != null && !boss.isDead()) {
            boss.remove();
        }
        boss = location.getWorld().spawn(location, Warden.class);
        boss.setCustomNameVisible(true);
        boss.setHealth(5000.0);
        boss.setAI(true);
        boss.setGravity(true);
        boss.setInvulnerable(false);
        boss.setPersistent(true);
        boss.setVisualFire(false);
        boss.setAware(true);
        boss.setSilent(false);
        boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(2.0);

        this.bossBarManager.createBar();
    }

}
