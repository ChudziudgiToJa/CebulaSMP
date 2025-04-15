package pl.chudziudgi.lifesteal.feature.spawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpawnerController implements Listener {

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() != Material.SPAWNER) return;
        double chance = 0;
        if (player.hasPermission(getS())) {
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

    private static @NotNull String getS() {
        return "cebulasmp.spawner.cebulak";
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            LivingEntity entity = event.getEntity();
            entity.setAI(false);
        }
    }

}
