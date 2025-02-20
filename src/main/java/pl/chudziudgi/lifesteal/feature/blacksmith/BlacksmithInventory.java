package pl.chudziudgi.lifesteal.feature.blacksmith;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;

public class BlacksmithInventory {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;

    public BlacksmithInventory(SurvivalPlugin survivalPlugin, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
    }


    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fkowal"));
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByUUID(player.getUniqueId());

        double discountMultiplier;
        if (player.hasPermission("cebulasmp.blacksmith.cebulak")) {
            discountMultiplier = 0.8;
        } else if (player.hasPermission("cebulasmp.blacksmith.mvip")) {
            discountMultiplier = 0.9;
        } else if (player.hasPermission("cebulasmp.blacksmith.vip")) {
            discountMultiplier = 0.95;
        } else {
            discountMultiplier = 1.0;
        }

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(24, new ItemBuilder(Material.IRON_SWORD)
                .setName("&7Napraw cały ekwipunek")
                .addLore(
                        "",
                        "&fKliknij, aby naprawić",
                        "&fswój cały ekwipunek.",
                        "",
                        "&aKoszt: " + DecimalUtil.getFormat(5500 * discountMultiplier) + " monet"
                )
                .build());

        inventory.setItem(22, new ItemBuilder(Material.ACACIA_HANGING_SIGN)
                .setName("&7Rangi posiadają przeceny")
                .addLore(
                        "",
                        "&fꑅ 5% taniej.",
                        "&fꑇ 10% taniej.",
                        "&fꑍ 20% taniej."
                )
                .build());

        inventory.setItem(20, new ItemBuilder(Material.GOLDEN_CHESTPLATE)
                .setName("&7Napraw przedmiot w ręce")
                .addLore(
                        "",
                        "&fKliknij, aby naprawić",
                        "&fprzedmiot w swojej ręce.",
                        "",
                        "&aKoszt: " + DecimalUtil.getFormat(2750 * discountMultiplier) + " monet"
                )
                .build());


        simpleInventory.click(event -> {
            ItemStack clickedItem = event.getCurrentItem();
            event.setCancelled(true);
            if (user == null) return;

            if (clickedItem == null || clickedItem.getType().equals(Material.AIR)) return;

            if (event.getSlot() == 24) {
                double repairCost = 5500 * discountMultiplier;

                if (user.getMoney() >= repairCost) {
                    boolean repaired = false;

                    for (ItemStack itemStack : player.getInventory().getContents()) {
                        if (itemStack != null && itemStack.getType().getMaxDurability() > 0) {
                            itemStack.setDurability((short) 0);
                            repaired = true;
                        }
                    }

                    if (repaired) {
                        user.setMoney(user.getMoney() - repairCost);
                        MessageUtil.sendTitle(player, "", "&aNaprawiono cały ekwipunek", 20, 50, 20);
                    } else {
                        MessageUtil.sendTitle(player, "", "&cNie posiadasz przedmiotów do naprawy!", 20, 50, 20);
                    }
                } else {
                    MessageUtil.sendTitle(player, "", "&cNie stać Cię na naprawę całego ekwipunku!", 20, 50, 20);
                }
                player.closeInventory();
            }

            if (event.getSlot() == 20) {
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                double repairCost = 2750 * discountMultiplier;
                if (itemInHand.getType().getMaxDurability() > 0) {
                    if (user.getMoney() >= repairCost) {
                        itemInHand.setDurability((short) 0);
                        user.setMoney(user.getMoney() - repairCost);
                        MessageUtil.sendTitle(player, "", "&aNaprawiono przedmiot w ręce", 20, 50, 20);
                    } else {
                        MessageUtil.sendTitle(player, "", "&cNie stać Cię na naprawę tego przedmiotu!", 20, 50, 20);
                    }
                } else {
                    MessageUtil.sendTitle(player, "", "&cPrzedmiot w ręce nie może być naprawiony!", 20, 50, 20);
                }
                player.closeInventory();
            }
        });
        player.openInventory(inventory);
    }
}

