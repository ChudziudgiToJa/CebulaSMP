package pl.chudziudgi.lifesteal.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerUtil {

    public static void addItemStack(Player player, final ItemStack itemStack) {
        HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(itemStack);
        leftover.values().forEach(remaining ->
                player.getWorld().dropItemNaturally(player.getLocation(), remaining)
        );
    }
}
