package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import pl.chudziudgi.lifesteal.feature.crafting.Crafting;
import pl.chudziudgi.lifesteal.util.ItemBuilder;

import java.util.List;

public class CraftingConfiguration extends OkaeriConfig {

    public List<Crafting> craftings = List.of(
            new Crafting(
                    List.of(
                            new ItemBuilder(Material.SHORT_GRASS).build(),
                            new ItemBuilder(Material.SHORT_GRASS).build(),
                            new ItemBuilder(Material.SHORT_GRASS).build(),
                            new ItemBuilder(Material.GOLD_BLOCK).build(),
                            new ItemBuilder(Material.POTATO).build(),
                            new ItemBuilder(Material.GOLD_BLOCK).build(),
                            new ItemBuilder(Material.DIAMOND_BLOCK).build(),
                            new ItemBuilder(Material.DIRT).build(),
                            new ItemBuilder(Material.DIAMOND_BLOCK).build()
                    ),
                    new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).build()
            ),
            new Crafting(
                    List.of(
                            new ItemBuilder(Material.AIR).build(), new ItemBuilder(Material.ENDER_PEARL).build(), new ItemBuilder(Material.ENDER_PEARL).build(),
                            new ItemBuilder(Material.AIR).build(), new ItemBuilder(Material.GOLD_BLOCK).build(), new ItemBuilder(Material.ENDER_PEARL).build(),
                            new ItemBuilder(Material.GOLD_BLOCK).build(), new ItemBuilder(Material.AIR).build(), new ItemBuilder(Material.AIR).build()
                    ),
                    new ItemBuilder(Material.TRIDENT).build()
            )
    );
}
