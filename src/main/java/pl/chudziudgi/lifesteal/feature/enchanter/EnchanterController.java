package pl.chudziudgi.lifesteal.feature.enchanter;

import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;

public class EnchanterController implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final EnchanterInventory enchanterInventory;

    public EnchanterController(PluginConfiguration pluginConfiguration, EnchanterInventory enchanterInventory) {
        this.pluginConfiguration = pluginConfiguration;
        this.enchanterInventory = enchanterInventory;
    }

    @EventHandler
    public void click(NpcInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getNpc().getData().getId().equals(this.pluginConfiguration.getEnchanterID())) {
            this.enchanterInventory.show(player);
            player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5 ,5);
            event.setCancelled(true);
        }
    }

}
