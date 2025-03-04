package pl.chudziudgi.lifesteal.feature.enderchest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

public class EnderChestController implements Listener {

    private final UserService userService;
    private final EnderChestIventory enderChestIventory;

    public EnderChestController(UserService userService, EnderChestIventory enderChestIventory) {
        this.userService = userService;
        this.enderChestIventory = enderChestIventory;
    }

    @EventHandler
    public void onClickEnderChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.isCancelled()) {
            return;
        }

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (block != null && block.getType() == Material.ENDER_CHEST) {
                User user = this.userService.findUserByUUID(player.getUniqueId());
                if (user != null) {
                    this.enderChestIventory.showMainPage(player, user);
                }
            }
        }
    }
}
