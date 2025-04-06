package pl.chudziudgi.lifesteal.feature.customitem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.CustomItemConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

public class CustomItemInventory {

    private final SurvivalPlugin survivalPlugin;
    private final CustomItemConfiguration customItemConfiguration;

    public CustomItemInventory(SurvivalPlugin survivalPlugin, CustomItemConfiguration customItemConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.customItemConfiguration = customItemConfiguration;
    }


    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&fcustom itemy:"));
        Inventory inventory = simpleInventory.getInventory();

        inventory.addItem(this.customItemConfiguration.barrier);

        player.openInventory(inventory);
    }
}