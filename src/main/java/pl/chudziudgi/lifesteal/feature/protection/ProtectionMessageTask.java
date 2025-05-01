package pl.chudziudgi.lifesteal.feature.protection;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.ProtectionConfiguration;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class ProtectionMessageTask extends BukkitRunnable {


    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration protectionConfiguration;

    public ProtectionMessageTask(final SurvivalPlugin survivalPlugin, ProtectionManager protectionManager, ProtectionConfiguration protectionConfiguration) {
        this.protectionManager = protectionManager;
        this.protectionConfiguration = protectionConfiguration;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 2);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Long endTime = this.protectionManager.getUuidLongHashMap().get(player.getUniqueId());
            if (endTime != null && endTime > System.currentTimeMillis()) {
                MessageUtil.sendActionbar(player, this.protectionConfiguration.protectionMessage
                        .replace("{TIME}", DurationUtil.getTimeFormatCombat(endTime - System.currentTimeMillis())));
            }
        });
    }
}
