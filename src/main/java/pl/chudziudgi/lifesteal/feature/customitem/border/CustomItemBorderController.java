package pl.chudziudgi.lifesteal.feature.customitem.border;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.CustomItemConfiguration;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItemCoolDownManager;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItemData;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class CustomItemBorderController implements Listener {
    private final SurvivalPlugin survivalPlugin;
    private final CustomItemConfiguration customItemConfiguration;
    private final CustomItemCoolDownManager coolDownManager;

    public CustomItemBorderController(SurvivalPlugin survivalPlugin, CustomItemConfiguration customItemConfiguration, CustomItemCoolDownManager coolDownManager) {
        this.survivalPlugin = survivalPlugin;
        this.customItemConfiguration = customItemConfiguration;
        this.coolDownManager = coolDownManager;
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager) || !(event.getEntity() instanceof Player target)) {
            return;
        }

        ItemStack item = damager.getInventory().getItemInMainHand();
        if (!this.customItemConfiguration.barrier.equals(item)) return;

        if (coolDownManager.isCoolDown(damager)) {
            CustomItemData customItemData = this.coolDownManager.getData(damager);
            long remaining = customItemData.getTime() - System.currentTimeMillis();
            String formatted = DurationUtil.getTimeFormat(remaining);
            MessageUtil.sendTitle(damager, "", "&4Poczekaj jeszcze: " +formatted, 20,50,20);
            return;
        }

        this.coolDownManager.addCoolDown(damager, 10 * 60 * 1000L);
        damager.setCooldown(item.getType(), 20 * 10);

        Location center = damager.getLocation();
        CustomItemBorderManager.sendWorldBorderPacket(damager, center, 10);
        CustomItemBorderManager.sendWorldBorderPacket(target, center, 10);

        new BukkitRunnable() {
            @Override
            public void run() {
                CustomItemBorderManager.resetWorldBorder(damager);
                CustomItemBorderManager.resetWorldBorder(target);
            }
        }.runTaskLater(this.survivalPlugin, 20 * 10);
    }
}
