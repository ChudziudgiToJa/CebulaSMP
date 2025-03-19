package pl.chudziudgi.lifesteal.feature.check;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class CheckController implements Listener {

    private final CheckService checkService;
    private final PluginConfiguration pluginConfiguration;

    public CheckController(CheckService checkService, PluginConfiguration pluginConfiguration) {
        this.checkService = checkService;
        this.pluginConfiguration = pluginConfiguration;
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (this.checkService.contains(player.getUniqueId())) {
            String command = this.pluginConfiguration.checkSettings.commandToExeciute.replace("{PLAYER}", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            this.checkService.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (this.checkService.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (this.checkService.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (this.checkService.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (this.checkService.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            MessageUtil.sendMessage(event.getPlayer(), "&cJesteś podczas sprawdzania nie możesz używać komend.");
        }
    }
}
