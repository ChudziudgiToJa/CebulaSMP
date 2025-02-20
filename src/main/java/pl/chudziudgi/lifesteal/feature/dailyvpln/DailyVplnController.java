package pl.chudziudgi.lifesteal.feature.dailyvpln;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class DailyVplnController implements Listener {

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;
    private final DailyVplnManager dailyVplnManager;

    public DailyVplnController(UserService userService, PluginConfiguration pluginConfiguration, DailyVplnManager dailyVplnManager) {
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
        this.dailyVplnManager = dailyVplnManager;
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        User user = this.userService.findUserByNickName(player.getName());
        if (user == null) return;

        if (event.getNPC().getId() != this.pluginConfiguration.freePlnNpcID) return;

        long currentTime = System.currentTimeMillis();
        if (user.getDailyFreeVpln() > currentTime) {
            MessageUtil.sendTitle(player, "", "&cMożesz odebrać za: " + DurationUtil.getTimeFormat(user.getDailyFreeVpln() - currentTime), 20, 50, 20);
            player.closeInventory();
            player.playSound(player, Sound.ENTITY_BEE_HURT, 5, 5);
            return;
        }

        double vpln = this.dailyVplnManager.getRandomValueForPlayer(player);
        user.setVPln(user.getVPln() + vpln);
        user.setDailyFreeVpln(currentTime + 86400000);

        MessageUtil.sendTitle(player, "", "&aOdebrano darmowe: " + DecimalUtil.getFormat(vpln) + " vpln", 20, 50, 20);
        player.playSound(player, Sound.ENTITY_VILLAGER_YES, 5, 5);
    }
}
