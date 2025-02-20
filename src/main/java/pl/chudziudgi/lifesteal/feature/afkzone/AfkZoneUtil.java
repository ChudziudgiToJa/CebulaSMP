package pl.chudziudgi.lifesteal.feature.afkzone;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AfkZoneUtil {

    public static boolean isPlayerInRegion(Player player, String regionName) {
        Location playerLocation = player.getLocation();
        RegionManager regionManager = getRegionManager(playerLocation);

        if (regionManager == null) {
            return false;
        }

        BlockVector3 wgLocation = BukkitAdapter.asBlockVector(playerLocation);
        ApplicableRegionSet regionSet = regionManager.getApplicableRegions(wgLocation);

        for (ProtectedRegion region : regionSet) {
            if (region.getId().equalsIgnoreCase(regionName)) {
                return true;
            }
        }
        return false;
    }

    public static RegionManager getRegionManager(Location location) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        return container.get(BukkitAdapter.adapt(location.getWorld()));
    }


    public static void giveItemWithChance(Player player, ItemStack item) {
        if (player == null || item == null)
            throw new IllegalArgumentException("Invalid input: Player, item cannot be null, and baseChance must be between 0 and 100!");

        double chance = 10;
        if (player.hasPermission("cebula.afk.vip")) {
            chance += 20;
        }
        if (player.hasPermission("cebula.afk.mvip")) {
            chance += 30;
        }
        if (player.hasPermission("cebula.afk.cebulak")) {
            chance += 40;
        }

        if (Math.random() * 100 <= chance)
            player.getInventory().addItem(item);
    }

}
