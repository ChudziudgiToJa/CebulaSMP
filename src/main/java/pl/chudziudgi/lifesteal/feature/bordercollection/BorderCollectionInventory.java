package pl.chudziudgi.lifesteal.feature.bordercollection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.BorderCollectionConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;

public class BorderCollectionInventory {

    private final SurvivalPlugin survivalPlugin;
    private final BorderCollectionConfiguration borderCollectionConfiguration;
    private final UserService userService;

    public BorderCollectionInventory(SurvivalPlugin survivalPlugin, BorderCollectionConfiguration borderCollectionConfiguration, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.borderCollectionConfiguration = borderCollectionConfiguration;
        this.userService = userService;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&fborder mapy:"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(10, new ItemBuilder(Material.CHEST)
                .setName(" ")
                .setLore(
                        "",
                        "&7Aktualny rozmiar świata&8: &a" + borderCollectionConfiguration.getWorldSize(),
                        "&7wspólnie wydane monety na rozmiar mapy&8: &a" + DecimalUtil.getFormat(this.borderCollectionConfiguration.depositMoney) + "&f monet",
                        ""
                )
                .build());

        inventory.setItem(12, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1)
                .setName("&edodatkowy rozmiar świata &8(1 kratka)")
                .setLore(
                        "",
                        "&7Koszt: &a5,000 monet",
                        "",
                        "&7Aktualny rozmiar świata: &a" + borderCollectionConfiguration.getWorldSize(),
                        "",
                        "&akliknij aby kupić"
                )
                .build());

        inventory.setItem(13, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 2)
                .setName("&edodatkowy rozmiar świata &8(2 kratki)")
                .setLore(
                        "",
                        "&7Koszt: &a10,000 monet",
                        "",
                        "&7Aktualny rozmiar świata: &a" + borderCollectionConfiguration.getWorldSize(),
                        "",
                        "&akliknij aby kupić"
                )
                .build());

        inventory.setItem(14, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 3)
                .setName("&edodatkowy rozmiar świata &8(3 kratki)")
                .setLore(
                        "",
                        "&7Koszt: &a15,000 monet",
                        "",
                        "&7Aktualny rozmiar świata: &a" + borderCollectionConfiguration.getWorldSize(),
                        "",
                        "&akliknij aby kupić"
                )
                .build());

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (event.getSlot() == 12) {
                areYouSure(player, 5000, 1);
                return;
            }
            if (event.getSlot() == 13) {
                areYouSure(player, 10000, 2);
                return;
            }
            if (event.getSlot() == 14) {
                areYouSure(player, 15000, 3);
                return;
            }
        });

        player.openInventory(inventory);
    }

    public void areYouSure(final Player player, final int cost, final int borderMore) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&7czy na pewno?"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassGreenSlots = new Integer[]{
                1, 2, 0,
                9, 10, 11,
                18, 19, 20
        };

        Integer[] glassRedSlots = new Integer[]{
                6, 7, 8,
                15, 16, 17,
                24, 25, 26
        };

        Arrays.stream(glassGreenSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                        .setName("&a&lTAK")
                        .build()));

        Arrays.stream(glassRedSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setName("&4&lNIE")
                        .build()));


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (Arrays.asList(glassRedSlots).contains(event.getSlot())) {
                show(player);
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 5, 5);
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {
                User user = this.userService.findUserByUUID(player.getUniqueId());
                if (user.getMoney() >= cost) {
                    user.removeMoney(cost);
                    this.borderCollectionConfiguration.setWorldSize(borderCollectionConfiguration.getWorldSize() + borderMore);
                    this.borderCollectionConfiguration.setDepositMoney(this.borderCollectionConfiguration.getDepositMoney() + cost);
                    this.borderCollectionConfiguration.save();
                    MessageUtil.sendTitle(player, "", "&aPomyślnie zakupiono &7Nowy rozmiar świata: &a" + borderCollectionConfiguration.getWorldSize(), 20, 50, 20);
                    Bukkit.getWorlds().getFirst().getWorldBorder().setSize(this.borderCollectionConfiguration.getWorldSize());
                    player.playSound(player, Sound.ENTITY_VILLAGER_YES, 10, 10);
                    player.closeInventory();
                } else {
                    MessageUtil.sendTitle(player, "", "&cNie masz wystarczającej ilości monet!", 20, 50, 20);
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 10, 10);
                    player.closeInventory();
                }
            }
        });

        player.openInventory(inventory);
    }
}


