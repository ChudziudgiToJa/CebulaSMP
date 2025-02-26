package pl.chudziudgi.lifesteal.feature.clan.feature.upgrade;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.ClanConfiguration;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;

public class ClanUpgradeInventory {

    private final SurvivalPlugin survivalPlugin;
    private final ClanConfiguration clanConfiguration;

    public ClanUpgradeInventory(SurvivalPlugin survivalPlugin, ClanConfiguration clanConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.clanConfiguration = clanConfiguration;
    }

    public void show(final Player player,User user, Clan clan) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, InventoryType.HOPPER, MessageUtil.smallText("&fzestawy:"));
        Inventory inventory = simpleInventory.getInventory();

        inventory.setItem(1,
                new ItemBuilder(Material.PAPER)
                        .setName("&fMiejsca w klanie")
                        .setLore(
                                "",
                                "&fAktualne sloty w klanie: %s&8/&a%s".formatted(clan.getClanMemberArrayList().size(), clan.getMaxClanMemberSize()),
                                "",
                                "&akliknij aby kupić miejsce w klanie."
                        )
                        .build()
        );


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (event.getSlot() == 1) {
                open(player, user, clan);
            }

        });

        player.openInventory(inventory);
    }

    public void open(final Player player, User user, final Clan clan) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&6&lCzy napewno?"));
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

        inventory.setItem(13, new ItemBuilder(Material.PAPER)
                .setName("&7po kupnie klan będzie posiadał: %s slotów".formatted(clan.getMaxClanMemberSize() + 1))
                .setLore("", "&7Koszt: &a" + clanConfiguration.getClanMemberListMaxSize())
                .build());

        Arrays.stream(glassRedSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setName("&4&lNIE")
                        .build()));


        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (Arrays.asList(glassRedSlots).contains(event.getSlot())) {
                player.closeInventory();
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 5, 5);
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {
                double slotPrice = this.clanConfiguration.getClanMemberListMaxSize();

                if (user.getMoney() < slotPrice) {
                    MessageUtil.sendMessage(player, "&cNie stać cię na zakup dodatkowego slotu! Koszt: &e" + slotPrice);
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 5, 1);
                    return;
                }

                user.setMoney(user.getMoney() - slotPrice);
                MessageUtil.sendTitle(player, "", "&aZakupiono dodatkowy slot do klanu", 20, 50, 20);
                clan.setMaxClanMemberSize(clan.getMaxClanMemberSize() + 1);
                player.closeInventory();
            }
        });

        player.openInventory(inventory);

    }
}
