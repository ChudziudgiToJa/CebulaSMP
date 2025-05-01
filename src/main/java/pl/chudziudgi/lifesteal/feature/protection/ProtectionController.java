package pl.chudziudgi.lifesteal.feature.protection;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.chudziudgi.lifesteal.configuration.implementation.ProtectionConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.UUID;

public class ProtectionController implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration protectionConfiguration;

    public ProtectionController(ProtectionManager protectionManager, ProtectionConfiguration protectionConfiguration) {
        this.protectionManager = protectionManager;
        this.protectionConfiguration = protectionConfiguration;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!(damager instanceof Player damagerPlayer)) return;
        if (!(entity instanceof Player victimPlayer)) return;

        UUID damagerUUID = damagerPlayer.getUniqueId();
        UUID victimUUID = victimPlayer.getUniqueId();

        if (protectionManager.isProtection(damagerUUID)) {
            MessageUtil.sendMessage(damagerPlayer, protectionConfiguration.cantPvpMessageForDamager);
            event.setCancelled(true);
        } else if (protectionManager.isProtection(victimUUID)) {
            MessageUtil.sendMessage(damagerPlayer, protectionConfiguration.cantPvpMessageForVictrim);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDead(final PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        this.protectionManager.add(player.getUniqueId(), 300000L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN)) {
            this.protectionManager.add(player.getUniqueId(), 10000L);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        UUID playerUUID = player.getUniqueId();
        if (this.protectionManager.isProtection(playerUUID)) {
            event.setCancelled(true);
        }
    }
}

