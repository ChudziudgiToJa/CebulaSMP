package pl.chudziudgi.lifesteal.feature.bordercollection;


import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.chudziudgi.lifesteal.configuration.implementation.BorderCollectionConfiguration;

public class BorderCollectionController implements Listener {

    private final BorderCollectionConfiguration borderCollectionConfiguration;
    private final BorderCollectionInventory borderCollectionInventory;

    public BorderCollectionController(BorderCollectionConfiguration borderCollectionConfiguration, BorderCollectionInventory borderCollectionInventory) {
        this.borderCollectionConfiguration = borderCollectionConfiguration;
        this.borderCollectionInventory = borderCollectionInventory;
    }


    @EventHandler
    public void onClickNpc(NpcInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getNpc().getData().getId().equals(this.borderCollectionConfiguration.getNpcId())) {
            this.borderCollectionInventory.show(player);
            player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5, 5);
            event.setCancelled(true);
        }
    }
}
