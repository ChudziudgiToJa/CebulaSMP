package pl.chudziudgi.lifesteal.feature.shop.time;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

public class TimeShopTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;

    public TimeShopTask(SurvivalPlugin survivalPlugin, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 0,20);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user == null) return;
            user.setTimeMoney(user.getTimeMoney() + 1);
        });
    }
}
