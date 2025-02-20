package pl.chudziudgi.lifesteal.feature.user.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

public class SpentTimeTask implements Runnable {

    private final UserService userService;

    public SpentTimeTask(final SurvivalPlugin survivalPlugin, UserService userService) {
        this.userService = userService;
        Bukkit.getScheduler().runTaskTimerAsynchronously(survivalPlugin, this, 20L, 20L);
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            User user = this.userService.findUserByNickName(onlinePlayer.getName());

            if (user == null) {
                return;
            }

            user.addOnlineTime(1);
            user.addProgress(300);
        }
    }
}