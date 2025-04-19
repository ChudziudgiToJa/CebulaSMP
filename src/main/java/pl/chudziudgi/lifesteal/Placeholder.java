package pl.chudziudgi.lifesteal;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.chudziudgi.lifesteal.configuration.implementation.WorldsSettings;
import pl.chudziudgi.lifesteal.feature.abyss.AbyssManager;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.feature.top.TopManager;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.time.Duration;

public class Placeholder extends PlaceholderExpansion implements Relational {

    private final UserService userService;
    private final ClanService clanService;
    private final WorldsSettings worldsSettings;
    private final TopManager topManager;

    public Placeholder(UserService userService, ClanService clanService, WorldsSettings worldsSettings, TopManager topManager) {
        this.userService = userService;
        this.clanService = clanService;
        this.worldsSettings = worldsSettings;
        this.topManager = topManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "survival";
    }

    @Override
    public @NotNull String getAuthor() {
        return "chudziudgi";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        User user = userService.findUserByNickName(player.getName());
        Clan clan = this.clanService.findClanByMember(player.getName());

        if (params.startsWith("topKill_")) {
            return this.topManager.getTopUserName(topManager.get16UsersKills(), params, "topKill_", User::getKill);
        }
        if (params.startsWith("topMoney_")) {
            return this.topManager.getTopUserName(topManager.get16UsersMoneyTop(), params, "topMoney_", User::getMoney);
        }
        if (params.startsWith("topTime_")) {
            return this.topManager.getTopUserName(topManager.get16UsersSpendTime(), params, "topTime_", User::getSpentTime);
        }
        if (params.startsWith("topVpln_")) {
            return this.topManager.getTopUserName(topManager.get16UsersVpln(), params, "topVpln_", User::getVPln);
        }


        if (params.startsWith("monety")) {
            return DecimalUtil.getFormat(user.getMoney());
        }
        if (params.startsWith("mone_cza")) {
            return DecimalUtil.getFormat(user.getTimeMoney());
        }
        if (params.startsWith("czas")) {
            return DurationUtil.format(Duration.ofSeconds(user.getSpentTime()));
        }
        if (params.startsWith("vpln")) {
            return DecimalUtil.getFormat(user.getVPln());
        }
        if (params.startsWith("kills")) {
            return "" + user.getKill();
        }
        if (params.startsWith("deaths")) {
            return "" + user.getDead();
        }
        if (params.startsWith("place_block")) {
            return "" + user.getPlaceBlock();
        }
        if (params.startsWith("break_block")) {
            return "" + user.getBreakBlock();
        }
        if (params.startsWith("kosz")) {
            return MessageUtil.smallText(DurationUtil.convertLong(AbyssManager.time));
        }
        if (params.startsWith("vanished")) {
            return user.isVanish() ? MessageUtil.smallText(" &b&lvanish&f") : "";
        }
        if (params.startsWith("clan")) {
            return MessageUtil.smallTextToColor(" &5" + clan.getTag().toUpperCase());
        }
        if (params.startsWith("kd")) {
            if (user.getKill() == 0.0 || user.getDead() == 0.0) {
                return "0.0";
            }
            return String.format("%.1f", (double) user.getKill() / user.getDead());
        }
        if (params.startsWith("nameclan")) {
            if (clan != null) {
                return MessageUtil.smallText(clan.getTag() + " &8(&7" + clan.getOwner().getName() + "&8)");
            }
            return MessageUtil.smallText("&cbrak &7/klan");
        }
        if (params.startsWith("end")) {
            return MessageUtil.smallTextToColor(this.worldsSettings.endJoinStatus ? "&awłączony" : "&cwyłączony");
        }
        if (params.startsWith("nether")) {
            return MessageUtil.smallTextToColor(this.worldsSettings.netherJoinStatus ? "&awłączony" : "&cwyłączony");
        }
        return "";
    }

    @Override
    public String onPlaceholderRequest(Player one, Player two, String params) {
        if (one == null || two == null || !params.equalsIgnoreCase("clans")) {
            return null;
        }
        Clan clanOne = this.clanService.findClanByMember(one.getName());
        Clan clanTwo = this.clanService.findClanByMember(two.getName());

        if (clanOne != null && clanTwo == null) {
            return "";
        }
        if (clanOne == null && clanTwo != null) {
            return MessageUtil.smallTextToColor(" &c&l" + clanTwo.getTag());
        }
        if (clanOne == null) {
            return "";
        }
        if (clanOne.equals(clanTwo)) {
            return MessageUtil.smallTextToColor(" &a&l" + clanOne.getTag());
        } else {
            return MessageUtil.smallTextToColor(" &c&l" + clanTwo.getTag());
        }
    }

}