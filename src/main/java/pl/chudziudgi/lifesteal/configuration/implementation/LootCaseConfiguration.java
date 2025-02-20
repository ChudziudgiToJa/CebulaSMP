package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.chudziudgi.lifesteal.feature.lootcase.LootCase;

import java.util.List;

public class LootCaseConfiguration extends OkaeriConfig {

    public List<LootCase> lootCases = List.of(
            new LootCase(
                    "afk",
                    "skrzynia afk &8[&e⭐&7⭐⭐⭐⭐&8]",
                    new Location(Bukkit.getWorlds().getFirst(),30,72,28),
                    "",
                    List.of(
                    )
            ),
            new LootCase(
                    "wedrowca",
                    "skrzynia wędrowca &8[&e⭐⭐&7⭐⭐⭐&8]",
                    new Location(Bukkit.getWorlds().getFirst(),26,72,25),
                    "",
                    List.of(
                    )
            ),
            new LootCase(
                    "smoka",
                    "skrzynia smoka &8[&e⭐⭐⭐&7⭐⭐&8]",
                    new Location(Bukkit.getWorlds().getFirst(),25,72,17),
                    "",
                    List.of(
                    )
            ),
            new LootCase(
                    "piekiel",
                    "skrzynia piekieł &8[&e⭐⭐⭐⭐⭐&8]",
                    new Location(Bukkit.getWorlds().getFirst(),29,72,13),
                    "",
                    List.of(
                    )
            )
    );
}
