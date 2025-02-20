package pl.chudziudgi.lifesteal.feature.bordercollection;


import net.citizensnpcs.api.event.NPCRightClickEvent;
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
    public void onClickNpc(NPCRightClickEvent event) {
        Player player = event.getClicker();
        if (event.getNPC().getId() == this.borderCollectionConfiguration.getNpcId()) {
            this.borderCollectionInventory.show(player);
            player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5 ,5);
            event.setCancelled(true);
        }
    }
}
