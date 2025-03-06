package pl.chudziudgi.lifesteal.feature.boss;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.Random;

public class BossController implements Listener {

    private final Random random;
    private final BossManager bossManager;
    private boolean abilityUsed = false;

    public BossController(Random random, BossManager bossManager) {
        this.random = random;
        this.bossManager = bossManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (bossManager.getBoss() != null) {
            BossBarManager.addBossBar(player);
        } else {
            BossBarManager.removeBossBar(player);
        }
    }

    @EventHandler
    public void onBossDamage(EntityDamageEvent event) {
        if (bossManager.getBoss() == null || event.getEntity() != bossManager.getBoss()) return;

        Warden boss = bossManager.getBoss();
        double halfHealth = boss.getHealth() / 2;

        if (!abilityUsed && boss.getHealth() - event.getFinalDamage() <= halfHealth) {
            abilityUsed = true;
            boss.getWorld().getPlayers().forEach(player -> {
                if (boss.getLocation().distance(player.getLocation()) < 20) {
                    player.setVelocity(player.getVelocity().setY(30));
                    MessageUtil.sendTitle(player, "", "&cUWAGA", 20, 20, 20);
                }
            });
        }
    }

    @EventHandler
    public void onPlayerHitBoss(EntityDamageByEntityEvent event) {
        if (bossManager.getBoss() == null && event.getEntity() != bossManager.getBoss() && event.getDamager().getType() == EntityType.PLAYER) return;
        BossBarManager.refreshBar(bossManager, (Player) event.getDamager());

        if (event.getDamager() instanceof Player player) {

            if (random.nextInt(100) < 5) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 5, 1));
            }
            if (random.nextInt(100) < 10) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 5));
            }
            if (random.nextInt(100) < 2) {
                bossManager.getBoss().getWorld().getPlayers().forEach(local -> {
                    if (bossManager.getBoss().getLocation().distance(local.getLocation()) < 10) {
                        for (int i = 0; i < 3; i++) {
                            bossManager.getBoss().getWorld().strikeLightning(local.getLocation());
                        }
                    }
                });
            }
        }
    }
}
