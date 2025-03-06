package pl.chudziudgi.lifesteal.feature.boss;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Warden;

@Getter
public class BossManager {

    public Warden boss;



    public void spawn(Location location) {
        if (boss != null && !boss.isDead()) {
            boss.remove();
        }
        boss = location.getWorld().spawn(location, Warden.class);
        boss.setCustomNameVisible(true);
        boss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2048.0);
        boss.setHealth(2048.0);
        boss.setAI(true);
        boss.setGravity(true);
        boss.setInvulnerable(false);
        boss.setPersistent(true);
        boss.setVisualFire(false);
        boss.setAware(true);
        boss.setSilent(false);
        boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(3.0);

        BossBarManager.createBar(this);
    }

}
