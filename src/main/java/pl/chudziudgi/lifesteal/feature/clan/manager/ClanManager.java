package pl.chudziudgi.lifesteal.feature.clan.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.ClanMember;
import pl.chudziudgi.lifesteal.feature.clan.feature.armor.ClanArmorHandler;

import java.util.ArrayList;
import java.util.List;

public class ClanManager {

    public static void removeMember(Clan clan, final Player player) {
        ClanMember clanMember = findClanMemberByUuid(clan, player);
        if (clanMember == null) return;
        clan.getMembers().remove(clanMember);
        if (player == null) return;
        Bukkit.getOnlinePlayers().forEach(target -> {
            ClanArmorHandler.refreshArmorPacket(player, target);
        });
    }

    public static void removeMember(Clan clan, final ClanMember clanMember) {
        clan.getMembers().remove(clanMember);
    }

    public static List<String> formatPlayerStatus(List<ClanMember> clanMembers) {
        List<String> formattedNames = new ArrayList<>();
        for (ClanMember name : clanMembers) {
            Player player = Bukkit.getPlayerExact(name.getName());
            if (player != null && player.isOnline()) {
                formattedNames.add("&a" + name.getName());
            } else {
                formattedNames.add("&7" + name.getName());
            }
        }

        return formattedNames;
    }

    public static ClanMember findClanMemberByUuid(Clan clan, final Player player) {
        return clan.getMembers().stream()
                .filter(clanMember -> clanMember.getUuid().equals(player.getUniqueId()))
                .findFirst()
                .orElse(null);
    }

    public static List<Player> getAllClanMembersPlayerList(final Clan clan) {
        final List<Player> playerList = new ArrayList<>();
        clan.getMembers().forEach(clanMember -> {
            final Player player = Bukkit.getPlayer(clanMember.getUuid());
            if (player != null) {
                playerList.add(player);
            }
        });
        return playerList;
    }

}
