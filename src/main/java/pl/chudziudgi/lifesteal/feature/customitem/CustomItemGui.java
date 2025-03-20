package pl.chudziudgi.lifesteal.feature.customitem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.CustomItemConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Objects;

public class CustomItemGui {

    private final SurvivalPlugin survivalPlugin;
    private final CustomItemConfiguration customItemConfiguration;


    public CustomItemGui(SurvivalPlugin survivalPlugin, CustomItemConfiguration customItemConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.customItemConfiguration = customItemConfiguration;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&fvouchery:"));
        Inventory inventory = simpleInventory.getInventory();

        for (CustomItem customItem : this.customItemConfiguration.customItems) {
            if (customItem.getItemStack().isEmpty()) continue;
            inventory.addItem(Objects.requireNonNull(ItemStackSerializable.readItemStack(customItem.getItemStack())));
        }

        player.openInventory(inventory);
    }
}
