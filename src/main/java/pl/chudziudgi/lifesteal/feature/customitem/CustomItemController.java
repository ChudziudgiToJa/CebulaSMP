package pl.chudziudgi.lifesteal.feature.customitem;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.CustomItemConfiguration;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CustomItemController implements Listener {
    private final SurvivalPlugin plugin;
    private final CustomItemConfiguration configuration;
    private final Map<UUID, CustomItemCoolDown> cooldowns = new HashMap<>();

    public CustomItemController(SurvivalPlugin plugin, CustomItemConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player shooter)) return;

        ItemStack itemStack = shooter.getInventory().getItemInMainHand();
        Optional<CustomItem> customItemOpt = this.configuration.customItems.stream()
                .filter(customItem -> customItem.getItemStack().equals(ItemStackSerializable.write(itemStack)))
                .findFirst();

        if (customItemOpt.isEmpty()) return;

        CustomItem customItem = customItemOpt.get();
        UUID playerId = shooter.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId)) {
            CustomItemCoolDown cooldown = cooldowns.get(playerId);

            if (cooldown.getCustomItem().equals(customItem)) {
                long remainingTime = cooldown.getTime() - currentTime;
                if (remainingTime > 0) {
                    MessageUtil.sendTitle(shooter, "", "&cPoczekaj " + DurationUtil.getTimeFormat(remainingTime) + " przed ponownym użyciem.", 20, 50, 20);
                    event.setCancelled(true);
                    return;
                }
            }
        }

        cooldowns.put(playerId, new CustomItemCoolDown(customItem, currentTime + customItem.getCoolDownTime()));

        Entity hitEntity = event.getHitEntity();
        if (!(hitEntity instanceof LivingEntity target)) return;

        Vector pullVector = shooter.getLocation().toVector().subtract(target.getLocation().toVector());
        pullVector.normalize().multiply(2);
        target.setVelocity(pullVector);

        MessageUtil.sendTitle(shooter, "", "&aPrzyciągnięto " + target.getName(), 20, 40, 20);
    }
}