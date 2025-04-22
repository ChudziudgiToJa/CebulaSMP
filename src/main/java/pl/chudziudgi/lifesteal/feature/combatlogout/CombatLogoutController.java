package pl.chudziudgi.lifesteal.feature.combatlogout;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.chudziudgi.lifesteal.configuration.implementation.CombatLogoutConfiguration;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.util.MessageUtil;


public class CombatLogoutController implements Listener {

    private final CombatLogoutConfiguration configuration;
    private final CombatLogoutManager combatLogoutManager;
    private final ClanService clanManager;

    public CombatLogoutController(CombatLogoutConfiguration configuration, CombatLogoutManager combatLogoutManager, ClanService clanManager) {
        this.configuration = configuration;
        this.combatLogoutManager = combatLogoutManager;
        this.clanManager = clanManager;
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;

        Entity damager = event.getDamager();

        if (event.isCancelled()) return;

        switch (damager) {
            case Player attacker -> {
                Clan damagerClan = this.clanManager.findClanByMember(attacker.getUniqueId().toString());
                Clan victimClan = this.clanManager.findClanByMember(victim.getUniqueId().toString());

                if (!victim.getGameMode().equals(GameMode.SURVIVAL)) return;

                if (victimClan != null && victimClan.equals(damagerClan)) {
                    return;
                }

                combatLogoutManager.createCombat(attacker, this.configuration.combatTime);
                combatLogoutManager.createCombat(victim, this.configuration.combatTime);
            }
            case Projectile projectile when projectile.getShooter() instanceof Player shooter -> {
                Clan damagerClan = this.clanManager.findClanByMember(shooter.getUniqueId().toString());
                Clan victimClan = this.clanManager.findClanByMember(victim.getUniqueId().toString());

                if (victimClan != null && victimClan.equals(damagerClan)) {
                    return;
                }
                if (!victim.getGameMode().equals(GameMode.SURVIVAL)) return;

                combatLogoutManager.createCombat(shooter, this.configuration.combatTime);
                combatLogoutManager.createCombat(victim, this.configuration.combatTime);
            }
            case LivingEntity livingEntity -> {
                combatLogoutManager.createCombat(victim, this.configuration.combatTime);
            }
            default -> {
            }
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.combatLogoutManager.inCombat(player)) {
            player.setHealthScale(0);
            player.spigot().respawn();
            Bukkit.getOnlinePlayers().forEach(player1 -> MessageUtil.sendMessage(player1, this.configuration.logoutMessage.formatted("{PLAYER}", player.getName())));
        }
    }


    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (this.combatLogoutManager.inCombat(player)) {
            MessageUtil.sendMessage(player, this.configuration.blockInventoryMessage);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (!this.combatLogoutManager.inCombat(player)) return;

        String message = event.getMessage().toLowerCase().substring(1);
        String[] args = message.split(" ");

        if (args.length == 0) return;

        String fullCommand = args[0];
        if (args.length > 1) {
            fullCommand += " " + args[1];
        }

        String finalFullCommand = fullCommand;
        boolean allowed = this.configuration.whitelistCommandsOnCombat.stream()
                .anyMatch(whitelisted -> finalFullCommand.startsWith(whitelisted.toLowerCase()));

        if (!allowed) {
            event.setCancelled(true);
            MessageUtil.sendMessage(player, this.configuration.blockUnWhitelistedCOmmandMessage);
        }
    }
}
