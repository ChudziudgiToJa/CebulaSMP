package pl.chudziudgi.lifesteal.feature.clan.feature.pvp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;

public class ClanPvpController implements Listener {

    private final ClanService clanService;

    public ClanPvpController(ClanService clanService) {
        this.clanService = clanService;
    }

    @EventHandler
    public void onPlayerAttacked(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }

        Clan clan = this.clanService.findClanByMember(damager.getUniqueId());
        if (clan == null) {
            return;
        }

        boolean sameClan = clan.getMembers().stream().anyMatch(member -> member.getUuid().equals(player.getUniqueId()))
                || clan.getOwner().getUuid().equals(player.getUniqueId());

        if (!sameClan) {
            return;
        }

        if (!clan.isPvp()) {
            event.setCancelled(true);
        } else {
            event.setDamage(0);
        }
    }
}

