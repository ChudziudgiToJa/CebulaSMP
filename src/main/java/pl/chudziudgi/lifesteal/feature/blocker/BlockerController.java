package pl.chudziudgi.lifesteal.feature.blocker;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class BlockerController implements Listener {

    private final PluginConfiguration pluginConfiguration;

    public BlockerController(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack result = event.getCurrentItem();
        if (player.hasPermission("cebula.crafting.blocker")) return;
        if (result != null && pluginConfiguration.BlockerSettings.materials.contains(result.getType())) {
            event.setCancelled(true);
            player.closeInventory();
            MessageUtil.sendTitle(player, "", "&cprzedmiot zakazany", 20, 50, 20);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (player.hasPermission("cebula.crafting.blocker")) return;
        if (pluginConfiguration.BlockerSettings.materials.contains(itemInHand.getType())) {
            event.setCancelled(true);
            MessageUtil.sendTitle(player, "", "&cPrzedmiot zakazany", 20, 50, 20);
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        EntityType entityType = event.getEntity().getType();
        if (isBlockedEntity(entityType)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL && isBlockedEntity(event.getEntityType())) {
            event.setCancelled(true);
        }
    }

    private boolean isBlockedEntity(EntityType entityType) {
        return entityType == EntityType.WOLF ||
                entityType == EntityType.CAT ||
                entityType == EntityType.PARROT ||
                entityType == EntityType.HORSE ||
                entityType == EntityType.PILLAGER ||
                entityType == EntityType.PHANTOM ||
                entityType == EntityType.ALLAY ||
                entityType == EntityType.EVOKER ||
                entityType == EntityType.EVOKER_FANGS ||
                entityType == EntityType.DONKEY ||
                entityType == EntityType.ENDERMITE;
    }

}
