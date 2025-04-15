package pl.chudziudgi.lifesteal.feature.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.shop.npc.inventory.NpcShopInventory;
import pl.chudziudgi.lifesteal.feature.shop.time.TimeShopInventory;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

public class ShopInventory {

    private final SurvivalPlugin survivalPlugin;
    private final NpcShopInventory shopInventory;
    private final TimeShopInventory timeShopInventory;

    public ShopInventory(SurvivalPlugin survivalPlugin, NpcShopInventory shopInventory, TimeShopInventory timeShopInventory) {
        this.survivalPlugin = survivalPlugin;
        this.shopInventory = shopInventory;
        this.timeShopInventory = timeShopInventory;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, InventoryType.HOPPER, MessageUtil.smallText("&fsklepy"));
        Inventory inventory = simpleInventory.getInventory();

        inventory.setItem(0, new ItemBuilder(Material.GOLD_INGOT)
                .setName("&fLista sklepów u &bnpc")
                .setLore("", "&akliknij aby otworzyć")
                .build()
        );
        inventory.setItem(1, new ItemBuilder(Material.EMERALD_BLOCK)
                .setName("&fSklep za &2vpln &8(/itemshop)")
                .setLore("", "&akliknij aby otworzyć")
                .build()
        );
        inventory.setItem(2, new ItemBuilder(Material.CHEST)
                .setName("&fRynek &6graczy &8(/market)")
                .setLore("", "&akliknij aby otworzyć")
                .build()
        );
        inventory.setItem(3, new ItemBuilder(Material.CLOCK)
                .setName("&fSklep za spędzony czas")
                .setLore("", "&akliknij aby otworzyć")
                .build()
        );


        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (event.getSlot() == 0) {
                this.shopInventory.showAllShops(player);
                return;
            }
            if (event.getSlot() == 1) {
                Bukkit.getServer().dispatchCommand(player, "itemshop");
                return;
            }
            if (event.getSlot() == 2) {
                Bukkit.getServer().dispatchCommand(player, "market otworz");
                return;
            }
            if (event.getSlot() == 3) {
                this.timeShopInventory.showGlobal(player);
                return;
            }

        });
        player.openInventory(inventory);
    }
}
