package pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.blocker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.chudziudgi.lifesteal.configuration.implementation.ClanConfiguration;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class ClanCuboidCommandBlockerController implements Listener {

    private final ClanService clanService;
    private final ClanConfiguration clanConfiguration;

    public ClanCuboidCommandBlockerController(ClanService clanService, ClanConfiguration clanConfiguration) {
        this.clanService = clanService;
        this.clanConfiguration = clanConfiguration;
    }

    @EventHandler
    public void onSendCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        pl.chudziudgi.lifesteal.feature.clan.Clan clan = this.clanService.findClanByLocation(player.getLocation());

        if (clan == null) {
            return;
        }
        if (player.hasPermission("cebulasmp.clan.admin")) return;

        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase();

        if (command.equals("tpaaccept") || command.equals("tpaccept")) {
            String[] args = event.getMessage().split(" ");
            if (args.length > 1) {
                String targetPlayerName = args[1];
                if (clan.containsMemberByName(targetPlayerName)) {
                    return;
                }
            }
        }

        if (clan.containsMemberByUUID(player.getUniqueId().toString())) {
            if (this.clanConfiguration.blockCommandListForClan.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendTitle(player, "", "&ckomenda jest zablokowana na terenie klanu.", 20, 50, 20);
            }
        } else {
            if (this.clanConfiguration.blockCommandList.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendTitle(player, "", "&ckomenda jest zablokowana na terenie klanu.", 20, 50, 20);
            }
        }
    }
}
