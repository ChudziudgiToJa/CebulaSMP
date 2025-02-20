package pl.chudziudgi.lifesteal.feature.blacksmith;

import net.citizensnpcs.api.event.NPCRightClickEvent;
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
    public void click(NPCRightClickEvent event) {
        Player player = event.getClicker();
        if (event.getNPC().getId() == this.pluginConfiguration.blackSmithID) {
            this.blacksmithInventory.show(player);
            player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5 ,5);
            event.setCancelled(true);
        }
    }
}
