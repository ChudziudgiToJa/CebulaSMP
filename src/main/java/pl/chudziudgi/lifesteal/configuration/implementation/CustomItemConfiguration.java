package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItem;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItemType;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;

import java.util.List;

public class CustomItemConfiguration extends OkaeriConfig {
    public List<CustomItem> customItems = List.of(
            new CustomItem(
                    CustomItemType.BOW_SWAP,
                    300,
                    ItemStackSerializable.write(new ItemBuilder(Material.BOW)
                            .setName("&aŁuk Zamiany")
                            .setLore(
                                    "&d&lPrzedmiot eventowy",
                                    "",
                                    "&7Tajemniczy łuk o niezwykłych właściwościach.",
                                    "&7Potężny artefakt używany podczas eventów.",
                                    "&7Strzały wystrzelone z tego łuku mogą nieść",
                                    "&7nieoczekiwane efekty... 🎭",
                                    ""
                            )
                            .build())
    ));
}
