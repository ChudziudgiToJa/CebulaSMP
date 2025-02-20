package pl.chudziudgi.lifesteal.feature.kit;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.KitConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.*;

import java.util.Arrays;
import java.util.HashMap;

public class KitInventory {

    private final SurvivalPlugin survivalPlugin;
    private final KitConfiguration kitConfiguration;
    private final UserService userService;

    public KitInventory(SurvivalPlugin survivalPlugin, KitConfiguration kitConfiguration, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.kitConfiguration = kitConfiguration;
        this.userService = userService;
    }


    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fzestawy:"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        for (Kit kit : this.kitConfiguration.kitList) {
            inventory.addItem(kit.getIcon());
        }


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            for (Kit kit : this.kitConfiguration.kitList) {
                if (event.getCurrentItem() != null && event.getCurrentItem().isSimilar(kit.getIcon())) {
                    this.showPrewiew(player, kit);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK, 5, 5);
                    break;
                }
            }
        });

        player.openInventory(inventory);
    }

    public void showPrewiew(final Player player, final Kit kit) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fpodgląd zestawu: " + kit.getName()));
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByNickName(player.getName());

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(51,
                new ItemBuilder(Material.RED_DYE)
                        .setName("&ckliknij aby cofnąć")
                        .build()
        );

        inventory.setItem(52,
                new ItemBuilder(Material.LIME_DYE)
                        .setName("&akliknij aby odebrac zestaw")
                        .build()
        );

        for (ItemStack itemStack : kit.getItemStackArrayList()) {
            inventory.addItem(itemStack);
        }

        for (String string : kit.getCustomItemList()) {
            ItemStack itemStack = ItemStackSerializable.readItemStack(string);
            if (itemStack != null) {
                inventory.addItem(itemStack);
            }
        }


        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (event.getSlot() == 51) {
                this.show(player);
                player.playSound(player, Sound.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, 5, 5);
            }

            if (event.getSlot() == 52) {

                if (!player.hasPermission(kit.getPermission())) {
                    MessageUtil.sendTitle(player, "", "&cNie posiadasz wymaganej rangi", 20, 50, 20);
                    player.closeInventory();
                    return;
                }

                if (!player.hasPermission("cebulasmp.kit.admin")) {
                    for (KitData kitData : user.getKits()) {
                        if (kitData.getName().equals(kit.getName())) {
                            if (kitData.getTime() > System.currentTimeMillis()) {
                                MessageUtil.sendTitle(player, "", "&cmożesz odebrać za: " + DurationUtil.getTimeFormat(kitData.getTime() - System.currentTimeMillis()), 20, 50, 20);
                                player.closeInventory();
                                player.playSound(player, Sound.ENTITY_BEE_HURT, 5, 5);
                                return;
                            }
                            user.getKits().remove(kitData);
                            break;
                        }
                    }
                }
                user.getKits().add(new KitData(kit.getName(), kit.getCoolDownTime() + System.currentTimeMillis()));
                for (ItemStack itemStack : kit.getItemStackArrayList()) {
                    HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(itemStack);
                    leftover.values().forEach(remaining ->
                            player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                    );
                }
                for (String string : kit.getCustomItemList()) {
                    ItemStack itemStack = ItemStackSerializable.readItemStack(string);
                    if (itemStack != null) {
                        HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(itemStack);
                        leftover.values().forEach(remaining ->
                                player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                        );
                    }
                }
                MessageUtil.sendTitle(player, "", "&aodebrano zestaw", 20, 50, 20);
                player.closeInventory();
            }
        });
        player.openInventory(inventory);
    }
}
