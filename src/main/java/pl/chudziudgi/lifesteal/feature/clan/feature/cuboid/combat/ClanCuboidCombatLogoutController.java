package pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.combat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.feature.combatlogout.CombatLogoutManager;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class ClanCuboidCombatLogoutController implements Listener {

    private final CombatLogoutManager combatLogoutManager;
    private final ClanService clanService;

    public ClanCuboidCombatLogoutController(CombatLogoutManager combatLogoutManager, ClanService clanService) {
        this.combatLogoutManager = combatLogoutManager;
        this.clanService = clanService;
    }

    @EventHandler
    public void onEnderPearl(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }
        if (!this.combatLogoutManager.inCombat(player)) {
            return;
        }
        Clan clan = this.clanService.findClanByLocation(event.getTo());
        if (clan == null) {
            return;
        }
        event.setCancelled(true);
        MessageUtil.sendTitle(player, "", "&cNie możesz rzucać perły na teren klanu będąc w walce!", 20,50,20);
    }
}