package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import pl.chudziudgi.lifesteal.feature.itemshop.ItemShop;
import pl.chudziudgi.lifesteal.util.ItemBuilder;

import java.util.List;

public class ItemShopConfiguration extends OkaeriConfig {
    public List<ItemShop> shops = List.of(
            new ItemShop(
                    new ItemBuilder(Material.IRON_HELMET)
                            .setName("ꑅ &8(&f30dni&8)")
                            .setLore(
                                    "",
                                    "&7Cena&8: &f9.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    9.99,
                    List.of("lp user {PLAYER} parent addtemp vip 30d")
            ),
            new ItemShop(
                    new ItemBuilder(Material.DIAMOND_HELMET)
                            .setName("ꑇ &8(&f30dni&8)")
                            .setLore(
                                    "",
                                    "&7Cena&8: &f19.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    19.99,
                    List.of("lp user {PLAYER} parent addtemp mvip 30d")
            ),
            new ItemShop(
                    new ItemBuilder(Material.NETHERITE_HELMET)
                            .setName("ꑍ &8(&f30dni&8)")
                            .setLore(
                                    "",
                                    "&7Cena&8: &f49.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    49.99,
                    List.of("lp user {PLAYER} parent addtemp cebulak 30d")
            ),
            new ItemShop(
                    new ItemBuilder(Material.TRIAL_KEY)
                            .setName("&b&lMALY &7zestaw kluczy")
                            .setLore(
                                    "",
                                    "&8| &fposiada w sobie klucze do:",
                                    " &d5x afk, &e3x smoka",
                                    "",
                                    "&7Cena&8: &f4.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    4.99,
                    List.of(
                            "case klucz {PLAYER} afk 5",
                            "case klucz {PLAYER} smoka 3"
                    )            ),
            new ItemShop(
                    new ItemBuilder(Material.TRIAL_KEY)
                            .setName("&b&lSREDNI &7zestaw kluczy")
                            .setLore(
                                    "",
                                    "&8| &fposiada w sobie klucze do:",
                                    " &d1x wedrowca, &e4x smoka",
                                    "",
                                    "&7Cena&8: &f6.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    6.99,
                    List.of(
                            "case klucz {PLAYER} wedrowca 1",
                            "case klucz {PLAYER} smoka 4"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.OMINOUS_TRIAL_KEY)
                            .setName("&b&lDUZY &7zestaw kluczy")
                            .setLore(
                                    "",
                                    "&8| &fposiada w sobie klucze do:",
                                    " &d2x wedrowca, &e5x smoka, &c2x piekieł",
                                    "",
                                    "&7Cena&8: &f14.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    14.99,
                    List.of(
                            "case klucz {PLAYER} wedrowca 2",
                            "case klucz {PLAYER} smoka 5",
                            "case klucz {PLAYER} piekiel 2"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.TRIAL_KEY)
                            .setName("&b&lKLUCZ &7Smoka")
                            .setLore(
                                    "",
                                    "&8| &fZdobądź 1 klucz smoka!",
                                    "",
                                    "&7Cena&8: &f1.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    1.99,
                    List.of(
                            "case klucz {PLAYER} smoka 1"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.TRIAL_KEY)
                            .setName("&b&lKLUCZ &7Wedrowca")
                            .setLore(
                                    "",
                                    "&8| &fZdobądź 1 klucz wedrowca!",
                                    "",
                                    "&7Cena&8: &f4.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    4.99,
                    List.of(
                            "case klucz {PLAYER} wedrowca 1"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.OMINOUS_TRIAL_KEY)
                            .setName("&b&lKLUCZ &7Piekiel")
                            .setLore(
                                    "",
                                    "&8| &fZdobądź 1 klucz piekiel!",
                                    "",
                                    "&7Cena&8: &f9.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    9.99,
                    List.of(
                            "case klucz {PLAYER} piekiel 1"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.YELLOW_BUNDLE)
                            .setName("&6&lDOLADOWANIE MONET &f10tyś")
                            .setLore(
                                    "",
                                    "&7Cena&8: &f4.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    4.99,
                    List.of(
                            "eco {PLAYER} ADD 10000.0"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.YELLOW_BUNDLE)
                            .setName("&6&lDOLADOWANIE MONET &f30tyś")
                            .setLore(
                                    "",
                                    "&7Cena&8: &f6.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    6.99,
                    List.of(
                            "eco {PLAYER} ADD 30000.0"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.PINK_BANNER)
                            .setName("&5&lDISCO &aZBROJA &8(&f7dni&8)")
                            .setLore(
                                    "",
                                    "&7Cena&8: &f1.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    1.99,
                    List.of(
                            "lp user {PLAYER} permission settemp cebulasmp.disco true 7d"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.PINK_BANNER)
                            .setName("&5&lDISCO &aZBROJA &8(&f30dni&8)")
                            .setLore(
                                    "",
                                    "&7Cena&8: &f4.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    4.99,
                    List.of(
                            "lp user {PLAYER} permission settemp cebulasmp.disco true 30d"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.FEATHER)
                            .setName("&a&llatanie na spawn &8(&f30dni&8)")
                            .setLore(
                                    "",
                                    "&7Cena&8: &f1.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    1.99,
                    List.of(
                            "lp user {PLAYER} permission settemp cebulasmp.spawn.fly true 30d"
                    )
            )
    );
}
