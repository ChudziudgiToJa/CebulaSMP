package pl.chudziudgi.lifesteal.feature.clan.feature.invite;

import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.clan.Clan;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClanInviteService {

    private final SurvivalPlugin survivalPlugin;

    public Map<Clan, String> clanInviteConcurrentHashMap = new ConcurrentHashMap<>();

    public ClanInviteService(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void inviteToClan(Clan clan, String target) {
        this.clanInviteConcurrentHashMap.put(clan, target);
        new BukkitRunnable() {
            @Override
            public void run() {
                removeInviteToClan(clan, target);
            }
        }.runTaskLater(survivalPlugin, 20 * 120);
    }

    public void removeInviteToClan(Clan clan, String target) {
        this.clanInviteConcurrentHashMap.remove(clan, target);
    }
}
