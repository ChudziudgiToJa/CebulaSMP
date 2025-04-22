package pl.chudziudgi.lifesteal.feature.combatlogout;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.CombatLogoutConfiguration;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class CombatLogoutTask extends BukkitRunnable {


    private final CombatLogoutManager combatLogoutManager;
    private final CombatLogoutConfiguration combatLogoutConfiguration;

    public CombatLogoutTask(final SurvivalPlugin survivalPlugin, CombatLogoutManager combatLogoutManager, CombatLogoutConfiguration combatLogoutConfiguration) {
        this.combatLogoutManager = combatLogoutManager;
        this.combatLogoutConfiguration = combatLogoutConfiguration;
        this.runTaskTimerAsynchronously(survivalPlugin, 0,2);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.combatLogoutManager.inCombat(player)) {
                if (this.combatLogoutManager.getCombat(player).getLeftTime() <= 5) {
                    MessageUtil.sendActionbar(player, this.combatLogoutConfiguration.combatEndMessage);
                    return;
                }
                String message = this.combatLogoutConfiguration.combatMessage.replace("{TIME}", DurationUtil.getTimeFormatCombat(this.combatLogoutManager.getCombat(player).getLeftTime() - System.currentTimeMillis()));
                MessageUtil.sendActionbar(player, message);
            }
        });
    }
}
