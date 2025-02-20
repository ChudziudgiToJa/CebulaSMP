package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.chudziudgi.lifesteal.feature.kit.Kit;
import pl.chudziudgi.lifesteal.util.ItemBuilder;

import java.util.List;

public class
KitConfiguration extends OkaeriConfig {

    @Comment("## status czy kity są włączone")
    public boolean kitStatus = true;

    @Comment("## czas podajemy w milisekundach bo to liczy na czasie aktualnym świata")
    public List<Kit> kitList = List.of(
            new Kit(
                    "gracz",
                    900000,
                    new ItemBuilder(Material.LEATHER_HELMET)
                            .setName("⚙")
                            .setLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.gracz",
                    List.of(
                            new ItemBuilder(Material.STONE_AXE)
                                    .addEnchant(Enchantment.VANISHING_CURSE, 1)
                                    .build(),
                            new ItemBuilder(Material.STONE_PICKAXE).addEnchant(Enchantment.VANISHING_CURSE, 1).build(),
                            new ItemBuilder(Material.LEATHER_HELMET).addEnchant(Enchantment.VANISHING_CURSE, 1).build(),
                            new ItemBuilder(Material.LEATHER_CHESTPLATE).addEnchant(Enchantment.VANISHING_CURSE, 1).build(),
                            new ItemBuilder(Material.LEATHER_LEGGINGS).addEnchant(Enchantment.VANISHING_CURSE, 1).build(),
                            new ItemBuilder(Material.LEATHER_BOOTS).addEnchant(Enchantment.VANISHING_CURSE, 1).build(),
                            new ItemBuilder(Material.COOKED_BEEF, 16).build()
                    ),
                    List.of(
                    )
            ),
            new Kit(
                    "vip",
                    86400000,
                    new ItemBuilder(Material.IRON_HELMET)
                            .setName("ꑅ")
                            .setLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.vip",
                    List.of(
                            new ItemBuilder(Material.IRON_AXE)
                                    .addEnchant(Enchantment.UNBREAKING, 1)
                                    .build(),
                            new ItemBuilder(Material.IRON_PICKAXE)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.SHEARS)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.COOKED_BEEF, 32).build(),
                            new ItemBuilder(Material.GOLDEN_APPLE, 2).build(),
                            new ItemBuilder(Material.CHAINMAIL_HELMET)
                                    .build(),
                            new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                                    .build(),
                            new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                                    .build(),
                            new ItemBuilder(Material.CHAINMAIL_BOOTS)
                                    .addEnchant(Enchantment.PROTECTION, 1)
                                    .build()

                    ),
                    List.of(
                    )
            ),
            new Kit(
                    "mvip",
                    86400000,
                    new ItemBuilder(Material.DIAMOND_HELMET)
                            .setName("ꑇ")
                            .setLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.mvip",
                    List.of(
                            new ItemBuilder(Material.IRON_AXE)
                                    .addEnchant(Enchantment.UNBREAKING, 1)
                                    .build(),
                            new ItemBuilder(Material.IRON_PICKAXE)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.SHEARS)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.COOKED_BEEF, 32).build(),
                            new ItemBuilder(Material.GOLDEN_APPLE, 2).build(),
                            new ItemBuilder(Material.IRON_HELMET)
                                    .addEnchant(Enchantment.PROTECTION, 1)
                                    .build(),
                            new ItemBuilder(Material.IRON_CHESTPLATE)
                                    .addEnchant(Enchantment.PROTECTION, 1)
                                    .build(),
                            new ItemBuilder(Material.IRON_LEGGINGS)
                                    .addEnchant(Enchantment.PROTECTION, 1)
                                    .build(),
                            new ItemBuilder(Material.IRON_BOOTS)
                                    .addEnchant(Enchantment.PROTECTION, 1)
                                    .build()

                    ),
                    List.of(
                    )
            ),
            new Kit(
                    "cebulak",
                    86400000,
                    new ItemBuilder(Material.NETHERITE_HELMET)
                            .setName("ꑍ")
                            .setLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.cebulak",
                    List.of(
                            new ItemBuilder(Material.IRON_AXE)
                                    .addEnchant(Enchantment.EFFICIENCY, 1)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.DIAMOND_PICKAXE)
                                    .addEnchant(Enchantment.UNBREAKING, 3)
                                    .build(),
                            new ItemBuilder(Material.SHEARS)
                                    .addEnchant(Enchantment.UNBREAKING, 3)
                                    .build(),
                            new ItemBuilder(Material.COOKED_BEEF, 32).build(),
                            new ItemBuilder(Material.GOLDEN_APPLE, 3).build(),
                            new ItemBuilder(Material.IRON_HELMET)
                                    .addEnchant(Enchantment.PROTECTION, 2)
                                    .build(),
                            new ItemBuilder(Material.IRON_CHESTPLATE)
                                    .addEnchant(Enchantment.PROTECTION, 2)
                                    .build(),
                            new ItemBuilder(Material.IRON_LEGGINGS)
                                    .addEnchant(Enchantment.PROTECTION, 2)
                                    .build(),
                            new ItemBuilder(Material.IRON_BOOTS)
                                    .addEnchant(Enchantment.PROTECTION, 2)
                                    .build()
                    ),
                    List.of(
                    )
            ),
            new Kit(
                    "maly",
                    86400000,
                    new ItemBuilder(Material.OMINOUS_TRIAL_KEY)
                            .setName("&b&lmaly &fzestaw kluczy")
                            .setLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.vip",
                    List.of(
                    ),
                    List.of(
                    )
            ),
            new Kit(
                    "sredni",
                    86400000,
                    new ItemBuilder(Material.OMINOUS_TRIAL_KEY)
                            .setName("&b&lsredni &fzestaw kluczy")
                            .setLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.mvip",
                    List.of(
                    ),
                    List.of(
                    )
            ),
            new Kit(
                    "duzy",
                    86400000,
                    new ItemBuilder(Material.OMINOUS_TRIAL_KEY)
                            .setName("&b&lduzy &fzestaw kluczy")
                            .setLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.cebulak",
                    List.of(
                    ),
                    List.of(
                    )
            )
    );
}
