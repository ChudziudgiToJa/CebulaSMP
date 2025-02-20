package pl.chudziudgi.lifesteal.feature.statistic;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

public class StatisticController implements Listener {

    private final UserService userService;

    public StatisticController(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;
        if (event.isCancelled()) return;

        Player killer = victim.getKiller();
        if (killer == null) return;

        User user = this.userService.findUserByNickName(killer.getName());
        if (user != null) {
            user.setKill(user.getKill() + 1);
        }
    }

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled()) return;

        User user = this.userService.findUserByNickName(player.getName());
        if (user != null) {
            user.setDead(user.getDead() + 1);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled()) return;

        User user = this.userService.findUserByNickName(player.getName());
        if (user != null) {
            user.setPlaceBlock(user.getPlaceBlock() + 1);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled()) return;

        User user = this.userService.findUserByNickName(player.getName());
        if (user != null) {
            user.setBreakBlock(user.getBreakBlock() + 1);
        }
    }
}

