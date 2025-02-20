package pl.chudziudgi.lifesteal.feature.user.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

public class UsersSaveTask implements Runnable {

    private final UserService userService;

    public UsersSaveTask(final SurvivalPlugin survivalPlugin, UserService userService) {
        this.userService = userService;
        Bukkit.getScheduler().runTaskTimerAsynchronously(survivalPlugin, this, 300L, 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user == null) return;
            this.userService.saveUser(user);
        }
    }
}