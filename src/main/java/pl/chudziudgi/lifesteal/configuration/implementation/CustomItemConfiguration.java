package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItem;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItemType;

import java.util.List;

public class CustomItemConfiguration extends OkaeriConfig {

    public List<CustomItem> customItems = List.of(
            new CustomItem(
                    CustomItemType.ENDLESS_FIREWORK,
                    300,
                    ""
            ),
            new CustomItem(
                    CustomItemType.BOW_SWAP,
                    600,
                    ""
            )
    );

}
