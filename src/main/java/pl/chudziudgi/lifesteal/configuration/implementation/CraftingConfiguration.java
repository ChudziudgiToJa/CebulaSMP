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
            ),
            new Crafting(
                    List.of(
                            new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).build(), new ItemBuilder(Material.NETHER_STAR).build(), new ItemBuilder(Material.ENDER_EYE).build(),
                            new ItemBuilder(Material.CHORUS_FRUIT).build(), new ItemBuilder(Material.DRAGON_BREATH).build(), new ItemBuilder(Material.CHORUS_FRUIT).build(),
                            new ItemBuilder(Material.NETHERITE_BLOCK).build(), new ItemBuilder(Material.NETHERITE_BLOCK).build(), new ItemBuilder(Material.NETHERITE_BLOCK).build()
                    ),
                    new ItemBuilder(Material.ELYTRA).build()
            ),
            new Crafting(
                    List.of(
                            new ItemBuilder(Material.NETHERITE_INGOT).build(), new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).build(), new ItemBuilder(Material.NETHERITE_INGOT).build(),
                            new ItemBuilder(Material.DIAMOND).build(), new ItemBuilder(Material.DRAGON_BREATH).build(), new ItemBuilder(Material.DIAMOND).build(),
                            new ItemBuilder(Material.NETHERITE_INGOT).build(), new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).build(), new ItemBuilder(Material.NETHERITE_INGOT).build()
                    ),
                    new ItemBuilder(Material.TOTEM_OF_UNDYING).build()
            ),
            new Crafting(
                    List.of(
                            new ItemBuilder(Material.GUNPOWDER).build(), new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).build(), new ItemBuilder(Material.GUNPOWDER).build(),
                            new ItemBuilder(Material.BONE).build(), new ItemBuilder(Material.LEATHER).build(), new ItemBuilder(Material.BONE).build(),
                            new ItemBuilder(Material.GUNPOWDER).build(), new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).build(), new ItemBuilder(Material.GUNPOWDER).build()
                    ),
                    new ItemBuilder(Material.RED_DYE)
                            .setName("&4połówka serca")
                            .setLore("", "&akliknij aby dodać")
                            .build()
            )
    );
}
