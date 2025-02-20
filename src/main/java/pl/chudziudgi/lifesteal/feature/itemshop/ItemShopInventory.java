package pl.chudziudgi.lifesteal.feature.itemshop;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.ItemShopConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;

public class ItemShopInventory {
    private final SurvivalPlugin survivalPlugin;
    private final ItemShopConfiguration itemShopConfiguration;
    private final ItemShopManager itemShopManager;
    private final UserService userService;

    public ItemShopInventory(SurvivalPlugin survivalPlugin, ItemShopConfiguration itemShopConfiguration, ItemShopManager itemShopManager, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.itemShopConfiguration = itemShopConfiguration;
        this.itemShopManager = itemShopManager;
        this.userService = userService;
    }


    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&6&lITEMSHOP"));
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByNickName(player.getName());



        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(49, new ItemBuilder(Material.GOLD_INGOT)
                        .setName("&fTwoje saldo&8: &a" + DecimalUtil.getFormat(user.getVPln()) + " vpln")
                        .setLore("","&fdoładuj vpln na &7-> &2&ncebulasmp.pl")
                .build());

        for (ItemShop itemShop : this.itemShopConfiguration.shops) {
            inventory.addItem(itemShop.getItemStack());
        }


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            for (ItemShop itemShop : this.itemShopConfiguration.shops) {
                if (event.getCurrentItem() != null && event.getCurrentItem().equals(itemShop.getItemStack())) {
                    showAreYouSure(player, itemShop, user);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK, 5, 5);
                    break;
                }
            }
        });

        player.openInventory(inventory);
    }


    public void showAreYouSure(final Player player, final ItemShop shop, User user) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&6&lITEMSHOP &7czy na pewno?"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassGreenSlots = new Integer[]{
                1,2,0,
                9,10,11,
                18,19,20
        };

        Integer[] glassRedSlots = new Integer[]{
                6,7,8,
                15,16,17,
                24,25,26
        };

        Arrays.stream(glassGreenSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                        .setName("&a&lTAK")
                        .build()));

        Arrays.stream(glassRedSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setName("&4&lNIE")
                        .build()));

        inventory.setItem(13, shop.getItemStack());


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (Arrays.asList(glassRedSlots).contains(event.getSlot())) {
                show(player);
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 1 ,1);
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {
                this.itemShopManager.onBuyItem(user, shop.getPrice(), shop);
                player.closeInventory();
            }
        });

        player.openInventory(inventory);
    }
}
