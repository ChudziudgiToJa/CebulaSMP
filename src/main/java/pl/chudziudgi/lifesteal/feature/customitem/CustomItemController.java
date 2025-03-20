package pl.chudziudgi.lifesteal.feature.customitem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomItemController implements Listener {

    @EventHandler
    public void onClickCustomItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();


    }

}
