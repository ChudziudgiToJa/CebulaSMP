package pl.chudziudgi.lifesteal.feature.spawner;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpawnerController implements Listener {

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (event.isCancelled()) return;

        if (block.getType() != Material.SPAWNER) return;
        double chance = 0;
        if (player.hasPermission("cebulasmp.spawner.cebulak")) {
            chance = 0.50;
        } else if (player.hasPermission("cebulasmp.spawner.mvip")) {
            chance = 0.30;
        } else if (player.hasPermission("cebulasmp.spawner.vip")) {
            chance = 0.20;
        } else {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.containsEnchantment(Enchantment.SILK_TOUCH)) {
                chance = 0.10;
            } else {
                return;
            }
        }
        if (Math.random() <= chance) {
            event.setExpToDrop(0);
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SPAWNER));
        }
    }


    @EventHandler
    public void onSpawnerMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            LivingEntity entity = event.getEntity();
            entity.addScoreboardTag("mob_from_spawner");
            AttributeInstance movementAttribute = entity.getAttribute(Attribute.MOVEMENT_SPEED);
            if (movementAttribute == null) {
                event.setCancelled(true);
                return;
            }
            movementAttribute.setBaseValue(0);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntity().getScoreboardTags().contains("mob_from_spawner")) {
            event.setCancelled(true);
        }
    }
}
