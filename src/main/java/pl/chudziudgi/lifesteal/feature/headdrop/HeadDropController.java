package pl.chudziudgi.lifesteal.feature.headdrop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadDropController implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null) return;

        double chance = 0.0;

        if (killer.hasPermission("cebulasmp.glowa.cebulak")) {
            chance = 0.50;
        } else if (killer.hasPermission("cebulasmp.spawner.mvip")) {
            chance = 0.30;
        } else if (killer.hasPermission("cebulasmp.spawner.vip")) {
            chance = 0.20;
        } else {
            chance = 0.10;
        }

        if (Math.random() <= chance) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            if (meta != null) {
                meta.setOwningPlayer(victim);
                meta.setDisplayName("§fGłowa gracza §e" + victim.getName());
                skull.setItemMeta(meta);
            }
            Location deathLocation = victim.getLocation();
            deathLocation.getWorld().dropItemNaturally(deathLocation, skull);
        }
    }


}
