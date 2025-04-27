package pl.chudziudgi.lifesteal.feature.clan.feature.upgrade;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.ClanConfiguration;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.feature.level.ClanLevelType;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;
import java.util.Set;

public class ClanUpgradeInventory {

    private final SurvivalPlugin survivalPlugin;

    public ClanUpgradeInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void show(final Player player, User user, Clan clan) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, InventoryType.HOPPER, MessageUtil.smallText("&fzestawy:"));
        Inventory inventory = simpleInventory.getInventory();

        inventory.setItem(1,
                new ItemBuilder(Material.PAPER)
                        .setName("&fMiejsca w klanie")
                        .setLore(
                                "",
                                "&7Aktualny poziom klanu&8: &b" + clan.getClanLevelType().getPolishName(),
                                "",
                                "&7Aktualne sloty w klanie&8: %s&8/&a%s".formatted(clan.getMembers().size(), clan.getClanLevelType().getMaxMember()),
                                "&7Wielkość terenu klanu&8: " + clan.getClanLevelType().getSize(),
                                "",
                                "&akliknij aby ulepszyć."
                        )
                        .build()
        );

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (event.getSlot() == 1) {
                showUpgradeClanInventory(player, user, clan);
            }
        });
        player.openInventory(inventory);
    }


    public void showUpgradeClanInventory(final Player player, User user, final Clan clan) {
        ClanLevelType currentLevel = clan.getClanLevelType();
        ClanLevelType[] levels = ClanLevelType.values();

        if (currentLevel.ordinal() >= levels.length - 1) {
            MessageUtil.sendTitle(player, "", "&cKlan jest już na najwyższym poziomie!", 20, 50, 20);
            return;
        }

        ClanLevelType nextLevel = levels[currentLevel.ordinal() + 1];
        double upgradeCost = nextLevel.getPrice();

        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&6&lPotwierdź ulepszenie klanu"));
        Inventory inventory = simpleInventory.getInventory();

        Set<Integer> greenSlots = Set.of(
                0, 1, 2,
                9, 10, 11,
                18, 19, 20
        );

        Set<Integer> redSlots = Set.of(
                6, 7, 8,
                15, 16, 17,
                24, 25, 26
        );

        greenSlots.forEach(slot ->
                inventory.setItem(slot,
                        new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                                .setName("&a&lTAK")
                                .build())
        );

        redSlots.forEach(slot ->
                inventory.setItem(slot,
                        new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                                .setName("&4&lNIE")
                                .build())
        );

        inventory.setItem(13, new ItemBuilder(Material.PAPER)
                .setName("&fUlepszenie klanu")
                .setLore(
                        "",
                        "&fObecny poziom: &b" + currentLevel.getPolishName(),
                        "&fNastępny poziom: &b" + nextLevel.getPolishName(),
                        "",
                        "&fKoszt ulepszenia: &a%s monet".formatted(upgradeCost),
                        ""
                )
                .build()
        );

        simpleInventory.click(event -> {
            event.setCancelled(true);
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType().isAir()) return;
            if (!clicked.hasItemMeta()) return;

            int slot = event.getSlot();

            if (redSlots.contains(slot)) {
                player.closeInventory();
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 5, 5);
                return;
            }

            if (greenSlots.contains(slot)) {
                if (user.getMoney() < upgradeCost) {
                    MessageUtil.sendMessage(player, "&cNie masz wystarczających środków, aby ulepszyć klan! Koszt: &e" + upgradeCost);
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 5, 1);
                    return;
                }

                user.setMoney(user.getMoney() - upgradeCost);
                clan.setClanLevelType(nextLevel);
                MessageUtil.sendTitle(player, "", "&aKlan został ulepszony do poziomu &b" + nextLevel.getPolishName(), 20, 50, 20);
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
                player.closeInventory();
            }
        });

        player.openInventory(inventory);
    }
}
