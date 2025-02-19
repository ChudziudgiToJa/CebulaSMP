package pl.chudziudgi.lifesteal.feature.clan.feature.create;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.ClanConfiguration;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;

public class CreatePurchaseMenu {

    private final SurvivalPlugin survivalPlugin;
    private final ClanConfiguration clanConfiguration;
    private final ClanService clanService;

    public CreatePurchaseMenu(SurvivalPlugin survivalPlugin, ClanConfiguration clanConfiguration, ClanService clanService) {
        this.survivalPlugin = survivalPlugin;
        this.clanConfiguration = clanConfiguration;
        this.clanService = clanService;
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
                        .setName("&7Klan o tagu: &f" + clan.getTag())
                        .setLore("", "&7Koszt: &a" + clanConfiguration.getClanPrice())
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
                user.setMoney(user.getMoney() - this.clanConfiguration.getClanPrice());
                this.clanService.createClan(clan);
                Bukkit.getOnlinePlayers().forEach(player1 -> MessageUtil.sendMessage(player1, player.getName() + " &astworzył nowy klan &2" + clan.getTag().toUpperCase()));
                player.closeInventory();
            }
        });

        player.openInventory(inventory);
    }
}
