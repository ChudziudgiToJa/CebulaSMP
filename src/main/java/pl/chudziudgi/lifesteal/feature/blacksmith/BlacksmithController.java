package pl.chudziudgi.lifesteal.feature.blacksmith;

import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;

public class BlacksmithController implements Listener {

    private final BlacksmithInventory blacksmithInventory;
    private final PluginConfiguration pluginConfiguration;

    public BlacksmithController(BlacksmithInventory blacksmithInventory, PluginConfiguration pluginConfiguration) {
        this.blacksmithInventory = blacksmithInventory;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void click(NpcInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getNpc().getData().getId().equals(this.pluginConfiguration.blackSmithID)) {
            this.blacksmithInventory.show(player);
            player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5 ,5);
            event.setCancelled(true);
        }
    }
}
