package pl.chudziudgi.lifesteal.feature.backup;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;
import java.util.HashMap;

public class BackupInventory {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;

    public BackupInventory(SurvivalPlugin survivalPlugin, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
    }


    public void show(final Player player, final Player target) {
        SimpleInventory simpleInventory = new SimpleInventory(
                this.survivalPlugin,
                9 * 6,
                MessageUtil.smallText("&fBackupy: " + target.getName())
        );
        Inventory inventory = simpleInventory.getInventory();

        User user = this.userService.findUserByNickName(target.getName());
        if (user == null) {
            player.sendMessage(MessageUtil.smallText("&cNie znaleziono użytkownika!"));
            return;
        }
        if (user.getBackups().isEmpty()) {
            player.sendMessage(MessageUtil.smallText("&cGracz nie posiada backapów"));
            return;
        }

        Integer[] glassBlueSlots = {
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()
        ));

        user.getBackups().forEach(backup -> {
            inventory.addItem(
                    new ItemBuilder(Material.BOOK)
                            .setName(backup.getInstantFormat())
                            .setLore("", "&aKliknij, aby otworzyć podgląd.")
                            .build()
            );
        });

        simpleInventory.click(event -> {
            event.setCancelled(true);

            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null || !currentItem.hasItemMeta() || !currentItem.getItemMeta().hasDisplayName()) {
                return;
            }

            String clickedName = currentItem.getItemMeta().getDisplayName();
            for (Backup backup : user.getBackups()) {
                if (clickedName.equals(backup.getInstantFormat())) {
                    showPreview(player, target, backup);
                    return;
                }
            }
        });

        player.openInventory(inventory);
    }


    public void showPreview(final Player player, final Player target, final Backup backup) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fBackap z: " + backup.getInstantFormat()));
        Inventory inventory = simpleInventory.getInventory();
        ItemStack[] itemStackArrayList = ItemStackSerializable.readItemStackList(backup.getItemStackArrayList());
        User user = this.userService.findUserByNickName(target.getName());

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        for (ItemStack itemStack : itemStackArrayList) {
            inventory.addItem(itemStack);
        }

        inventory.setItem(45,
                new ItemBuilder(Material.RED_DYE)
                        .setName("&acofnij")
                        .build()
        );

        inventory.setItem(49,
                new ItemBuilder(Material.PAPER)
                        .setName("&aInformacje poboczne")
                        .setLore(
                                "&7lvl: &7" + backup.getLvl(),
                                "&7exp: &7" + backup.getExp())
                        .build()
        );

        inventory.setItem(53,
                new ItemBuilder(Material.GREEN_DYE)
                        .setName("&aNadaj zestaw")
                        .setLore(
                                "",
                                "&aKliknij aby nadać graczu backup")
                        .build()
        );

        inventory.setItem(45,
                new ItemBuilder(Material.BARRIER)
                        .setName("&4wyjdz")
                        .build()
        );

        inventory.setItem(46,
                new ItemBuilder(Material.REDSTONE)
                        .setName("&cUsuń zapis ekwipunku")
                        .setLore(
                                "",
                                "&aKliknij aby usunąc.")
                        .build()
        );

        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (event.getSlot() == 45) {
                show(player, target);
                return;
            }

            if (event.getSlot() == 46) {
                user.getBackups().remove(backup);
                show(player, target);
                return;
            }

            if (event.getSlot() == 53) {
                for (ItemStack itemStack : itemStackArrayList) {
                    HashMap<Integer, ItemStack> leftover = target.getInventory().addItem(itemStack);
                    leftover.values().forEach(remaining ->
                            target.getWorld().dropItemNaturally(target.getLocation(), remaining)
                    );
                }

                user.getBackups().remove(backup);
                MessageUtil.sendTitle(player, "", "&abackup dla " + target.getName() + " z dnia: &f" + backup.getInstantFormat(), 20, 50, 20);
                MessageUtil.sendTitle(target, "", "&aotrzymałeś/aś backup z dnia: &f" + backup.getInstantFormat(), 20, 50, 20);
                player.closeInventory();
            }
        });

        player.openInventory(inventory);
    }
}
