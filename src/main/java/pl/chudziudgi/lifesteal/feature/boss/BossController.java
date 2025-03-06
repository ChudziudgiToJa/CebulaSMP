package pl.chudziudgi.lifesteal.feature.boss;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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
    public void onBossDeath(EntityDeathEvent event) {
        if (event.getEntity() == bossManager.getBoss()) {
            bossManager.setBoss(null);
            BossBarManager.removeBossBarFromAll();

            Player player = event.getEntity().getKiller();
            if (player == null) return;


        }
    }

    @EventHandler
    public void onPlayerHitBoss(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        if (bossManager.getBoss() == null || event.getEntity() != bossManager.getBoss())
            return;

        BossBarManager.refreshBar(bossManager, player);

        if (random.nextInt(100) < 1) {
            bossManager.getBoss().getWorld().getPlayers().forEach(damager -> {
                if (bossManager.getBoss().getLocation().distance(damager.getLocation()) < 20) {
                    damager.setVelocity(damager.getLocation().toVector().subtract(damager.getLocation().add(0, -0.1, 0).toVector()).normalize().multiply(30));
                    MessageUtil.sendTitle(damager, "&cUWAGA", "&bWATER SKILL", 20, 50, 20);
                }
            });
        }

        if (random.nextInt(100) < 5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 5, 1));
            MessageUtil.sendTitle(player, "", "&4&lBOSS &czaaplikował ci &a&ltruciznę&c!", 20, 50, 20);
        }

        if (random.nextInt(100) < 10) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 5));
            MessageUtil.sendTitle(player, "", "&4&lBOSS &cwzrok cię &8&lopuścił&c!", 20, 50, 20);
        }

        if (random.nextInt(100) < 3) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 1));
            MessageUtil.sendTitle(player, "", "&4&lBOSS &cpodarował ci &b&llewitację&c!", 20, 50, 20);
        }

        if (random.nextInt(100) < 15) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 5, 1));
            MessageUtil.sendTitle(player, "", "&4&lBOSS &czmusza cię do &6&lgłodówki&c!", 20, 50, 20);
        }


        if (random.nextInt(100) < 1) {
            Location bossLocation = bossManager.getBoss().getLocation();
            World world = bossLocation.getWorld();
            for (int i = 0; i < 10; i++) {
                world.spawnEntity(bossLocation, EntityType.ZOMBIE);
            }
        }

        if (random.nextInt(100) < 1) {
            Location bossLocation = bossManager.getBoss().getLocation();
            World world = bossLocation.getWorld();
            for (int i = 0; i < 5; i++) {
                world.spawnEntity(bossLocation, EntityType.TNT);
            }
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
