package pl.chudziudgi.lifesteal.feature.combatlogout;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class CombatLogoutElytraTask extends BukkitRunnable {

    private final CombatLogoutManager combatLogoutManager;
    private final SurvivalPlugin survivalPlugin;

    public CombatLogoutElytraTask(CombatLogoutManager combatLogoutManager, SurvivalPlugin survivalPlugin) {
        this.combatLogoutManager = combatLogoutManager;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 0,2);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.combatLogoutManager.inCombat(player)) {
                if (player.isGliding()) {
                    MessageUtil.sendTitle(player, "", "&cLatanie elytrą jest zablokowane podczas walki!",20,50,20);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setGliding(false);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 3));
                        }
                    }.runTask(this.survivalPlugin);
                }
            }
        });
    }
}