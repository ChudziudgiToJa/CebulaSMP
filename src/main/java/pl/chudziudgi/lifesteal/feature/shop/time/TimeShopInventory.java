package pl.chudziudgi.lifesteal.feature.shop.time;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;


import java.util.Arrays;

public class TimeShopInventory {

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;
    private final SurvivalPlugin survivalPlugin;

    public TimeShopInventory(UserService userService, PluginConfiguration pluginConfiguration, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
        this.survivalPlugin = survivalPlugin;
    }

    public void showGlobal(Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, "&3&lSklep za czas");
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByNickName(player.getName());

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(49, new ItemBuilder(Material.GOLD_INGOT).setName("&fPosiadasz:  &d" + user.getTimeMoney() + " ⭐").build());

        for (TimeShop shop : this.pluginConfiguration.timeShopSettings.timeShops) {
            inventory.addItem(shop.getIcon());
        }

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }

            if (!event.getCurrentItem().hasItemMeta()) {
                return;
            }

            for (TimeShop shop : this.pluginConfiguration.timeShopSettings.timeShops) {
                if (event.getCurrentItem().equals(shop.getIcon())) {
                    if (user.getSpentTime() >= shop.getPrice()) {
                        user.setSpentTime(user.getSpentTime() - shop.getPrice());
                        String command = shop.getCommand();
                        command = command.replace("{PLAYER}", player.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                        player.closeInventory();
                        MessageUtil.sendTitle(player, "", "&azakupiono.",20,50,20);
                    } else {
                        MessageUtil.sendTitle(player, "", "&cnie stać cię!",20,50,20);
                        player.closeInventory();
                    }
                }
            }
        });
        player.openInventory(inventory);
    }

}
