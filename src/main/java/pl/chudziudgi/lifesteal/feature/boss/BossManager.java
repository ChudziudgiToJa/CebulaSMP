package pl.chudziudgi.lifesteal.feature.boss;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Warden;

import java.util.Objects;

@Setter
@Getter
public class BossManager {

    private Warden boss;

    public void spawn(Location location) {
        if (boss != null && !boss.isDead()) {
            boss.remove();
        }

        boss = location.getWorld().spawn(location, Warden.class);
        boss.setCustomNameVisible(false);

        setAttribute(boss, Attribute.GENERIC_MAX_HEALTH, 2048.0);
        setAttribute(boss, Attribute.GENERIC_SCALE, 2.5);
        increaseAttribute(boss, Attribute.GENERIC_JUMP_STRENGTH, 1.1);
        increaseAttribute(boss, Attribute.GENERIC_STEP_HEIGHT, 2);
        increaseAttribute(boss, Attribute.GENERIC_ATTACK_SPEED, 4.0);

        boss.setHealth(2048.0);
        boss.setAI(true);
        boss.setGravity(true);
        boss.setInvulnerable(false);
        boss.setPersistent(true);
        boss.setVisualFire(false);
        boss.setAware(true);
        boss.setSilent(false);


        BossBarManager.createBar(this);
    }

    private void setAttribute(Warden entity, Attribute attribute, double value) {
        if (entity.getAttribute(attribute) != null) {
            Objects.requireNonNull(entity.getAttribute(attribute)).setBaseValue(value);
        }
    }

    private void increaseAttribute(Warden entity, Attribute attribute, double increment) {
        if (entity.getAttribute(attribute) != null) {
            Objects.requireNonNull(entity.getAttribute(attribute)).setBaseValue(Objects.requireNonNull(entity.getAttribute(attribute)).getBaseValue() + increment);
        }
    }
}