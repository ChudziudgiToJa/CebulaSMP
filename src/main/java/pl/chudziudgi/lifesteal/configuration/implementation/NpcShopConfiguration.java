package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import pl.chudziudgi.lifesteal.feature.shop.npc.object.NpcShopItemToInteract;
import pl.chudziudgi.lifesteal.feature.shop.npc.object.NpcShop;
import pl.chudziudgi.lifesteal.util.ItemBuilder;

import java.util.List;

public class NpcShopConfiguration extends OkaeriConfig {
    public List<NpcShop> npcShops = List.of(
            new NpcShop(
                    "Gornik",
                    9,
                    List.of(
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.CHARCOAL).build(),
                                    10,
                                    2.0, // Sprzedaż
                                    1.0  // Za  kup
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.COAL).build(),
                                    11,
                                    3.0,
                                    1.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.COPPER_INGOT).build(),
                                    12,
                                    6.0,
                                    3.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.IRON_INGOT).build(),
                                    13,
                                    12.0,
                                    7.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.LAPIS_LAZULI).build(),
                                    14,
                                    15.0,
                                    8.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.GOLD_INGOT).build(),
                                    15,
                                    18.0,
                                    10.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.EMERALD).build(),
                                    16,
                                    50.0,
                                    30.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.POINTED_DRIPSTONE).build(),
                                    19,
                                    4.0,
                                    2.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.FLINT).build(),
                                    20,
                                    1.5,
                                    0.8
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.AMETHYST_SHARD).build(),
                                    21,
                                    10.0,
                                    5.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.DIAMOND).build(),
                                    22,
                                    100.0,
                                    60.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.NETHERITE_SCRAP).build(),
                                    23,
                                    4000.0,
                                    300.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.REDSTONE).build(),
                                    24,
                                    8.0,
                                    2.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.COBBLESTONE).build(),
                                    25,
                                    1.0,
                                    0.1
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.COBBLED_DEEPSLATE).build(),
                                    28,
                                    1.5,
                                    0.1
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.WATER_BUCKET).build(),
                                    29,
                                    200,
                                    10
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.LAVA_BUCKET).build(),
                                    30,
                                    350,
                                    20
                            )
                    )
            ),
            new NpcShop(
                    "Farmer",
                    10,
                    List.of(
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.WHEAT).build(),
                                    10,
                                    3.0,
                                    1.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.POTATO).build(),
                                    11,
                                    4.0,
                                    2.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.CARROT).build(),
                                    12,
                                    4.5,
                                    2.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.BEETROOT).build(),
                                    13,
                                    5.0,
                                    2.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.PUMPKIN).build(),
                                    14,
                                    6.0,
                                    2.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.MELON_SLICE).build(),
                                    15,
                                    1.5,
                                    0.8
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.GLOW_BERRIES).build(),
                                    16,
                                    10.0,
                                    1.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.SWEET_BERRIES).build(),
                                    19,
                                    6.0,
                                    0.8
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.SUGAR_CANE).build(),
                                    20,
                                    5.5,
                                    0.7
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.BAMBOO).build(),
                                    21,
                                    4,
                                    0.8
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.CACTUS).build(),
                                    22,
                                    3.4,
                                    0.9
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.EGG).build(),
                                    23,
                                    7,
                                    4.0
                            )
                    )
            ),
            new NpcShop(
                    "Zabójca",
                    15,
                    List.of(
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.STRING).build(),
                                    10,
                                    4.0,
                                    2.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.SPIDER_EYE).build(),
                                    11,
                                    6.0,
                                    3.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.ENDER_PEARL).build(),
                                    12,
                                    590.0,
                                    9.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.ROTTEN_FLESH).build(),
                                    13,
                                    2.0,
                                    0.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.FEATHER).build(),
                                    14,
                                    3.0,
                                    1.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.BONE).build(),
                                    15,
                                    3.5,
                                    2.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.FERMENTED_SPIDER_EYE).build(),
                                    16,
                                    12.0,
                                    6.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.GLASS_BOTTLE).build(),
                                    19,
                                    2.5,
                                    1.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.GUNPOWDER).build(),
                                    20,
                                    300,
                                    8.1
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.ARROW).build(),
                                    21,
                                    1.0,
                                    0.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.SLIME_BALL).build(),
                                    22,
                                    5.0,
                                    2.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.GHAST_TEAR).build(),
                                    24,
                                    50.0,
                                    25.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.MAGMA_CREAM).build(),
                                    25,
                                    15.0,
                                    7.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.LEATHER).build(),
                                    23,
                                    3.0,
                                    1.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.RABBIT_HIDE).build(),
                                    33,
                                    2.0,
                                    1.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.RABBIT_FOOT).build(),
                                    28,
                                    10.0,
                                    5.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.PRISMARINE_SHARD).build(),
                                    29,
                                    8.0,
                                    4.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.PRISMARINE_CRYSTALS).build(),
                                    30,
                                    12.0,
                                    6.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.NAUTILUS_SHELL).build(),
                                    31,
                                    30.0,
                                    15.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.PHANTOM_MEMBRANE).build(),
                                    32,
                                    25.0,
                                    12.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.DRAGON_BREATH).build(),
                                    34,
                                    5400.0,
                                    50.0
                            )
                    )
            ),
            new NpcShop(
                    "Wędkarz",
                    8,
                    List.of(
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.COD).build(),
                                    10,
                                    60.0,
                                    8.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.SALMON).build(),
                                    11,
                                    70.0, // Sprzedaż (14.0 / 2)
                                    9.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.TROPICAL_FISH).build(),
                                    12,
                                    50.0, // Sprzedaż (10.0 / 2)
                                    6.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.PUFFERFISH).build(),
                                    13,
                                    90.0, // Sprzedaż (18.0 / 2)
                                    12.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.NAME_TAG).build(),
                                    14,
                                    12000,
                                    120.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.SADDLE).build(),
                                    15,
                                    300.0, // Sprzedaż (60.0 / 2)
                                    30.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.LILY_PAD).build(),
                                    16,
                                    40.0, // Sprzedaż (8.0 / 2)
                                    4.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.INK_SAC).build(),
                                    19,
                                    50.0, // Sprzedaż (10.0 / 2)
                                    4.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.BOWL).build(),
                                    20,
                                    40.5,
                                    1.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.TRIPWIRE_HOOK).build(),
                                    21,
                                    200.0, // Sprzedaż (40.0 / 2)
                                    15.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.LEATHER).build(),
                                    22,
                                    30.5,
                                    3.0
                            )
                    )
            ),
            new NpcShop(
                    "Drwal",
                    30,
                    List.of(
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.OAK_LOG).build(),
                                    10,
                                    6.0,
                                    3.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.SPRUCE_LOG).build(),
                                    11,
                                    6.0,
                                    3.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.BIRCH_LOG).build(),
                                    12,
                                    6.0,
                                    3.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.JUNGLE_LOG).build(),
                                    13,
                                    7.0,
                                    3.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.ACACIA_LOG).build(),
                                    14,
                                    7.0,
                                    3.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.DARK_OAK_LOG).build(),
                                    15,
                                    7.0,
                                    3.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.MANGROVE_LOG).build(),
                                    16,
                                    8.0,
                                    4.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.CHERRY_LOG).build(),
                                    19,
                                    8.0,
                                    4.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.BAMBOO_BLOCK).build(),
                                    20,
                                    5.0,
                                    2.5
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.CRIMSON_STEM).build(),
                                    21,
                                    10.0,
                                    5.0
                            ),
                            new NpcShopItemToInteract(
                                    new ItemBuilder(Material.WARPED_STEM).build(),
                                    22,
                                    10.0,
                                    5.0
                            )
                    )
            )
    );
}
