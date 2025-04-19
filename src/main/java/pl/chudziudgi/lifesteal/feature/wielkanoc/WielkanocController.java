package pl.chudziudgi.lifesteal.feature.wielkanoc;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;

import java.util.Objects;
import java.util.Random;

public class WielkanocController implements Listener {

    private final Random random;
    private final PluginConfiguration pluginConfiguration;

    public WielkanocController(Random random, PluginConfiguration pluginConfiguration) {
        this.random = random;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onGrassBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (block.getType() == Material.SHORT_GRASS || block.getType() == Material.TALL_GRASS) {
            if (random.nextDouble() <= 0.05) {
                block.getWorld().dropItemNaturally(block.getLocation(), Objects.requireNonNull(ItemStackSerializable.readItemStack(this.pluginConfiguration.wielkanocItemStack)));
            }
        }
    }
}
