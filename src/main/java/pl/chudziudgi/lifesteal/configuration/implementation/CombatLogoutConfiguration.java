package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.List;

public class CombatLogoutConfiguration extends OkaeriConfig {

    @Comment("Lista komend które są dozwolone podczas walki")
    public List<String> whitelistCommandsOnCombat = List.of(
            "klan info",
            "msg",
            "r",
            "kosz",
            "pomoc"
    );

    public int combatTimeFromPlayer = 30000;
    public int combatTimeFromMob = 15000;


    @Comment("wiadomość podczas walki")
    public String combatMessage = "&4jesteś podczas walki: {TIME}";
    public String combatEndMessage = "&akoniec walki";
    public String logoutMessage = "&7☠ &f{PLAYER} &cwylogował się podczas walki";
    public String blockUnWhitelistedCOmmandMessage = "&4nie możesz użyć tej komendy podczas walki.";
    public String blockInventoryMessage = "&4Nie możesz otwierać ekwipunków podczas walki.";
}
