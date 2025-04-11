package pl.chudziudgi.lifesteal.feature.enderchest;

import de.rapha149.signgui.exception.SignGUIVersionException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.*;

public class EnderChestInventory {

    private final SurvivalPlugin survivalPlugin;
    private final EnderChestSignGui enderChestSignGui;


    public EnderChestInventory(SurvivalPlugin survivalPlugin, EnderChestSignGui enderChestSignGui) {
        this.survivalPlugin = survivalPlugin;
        this.enderChestSignGui = enderChestSignGui;
    }

    public void showEnderChest(final Player player, EnderChest enderChest, User user) {
        player.playSound(player, Sound.BLOCK_ENDER_CHEST_OPEN, 5 ,5);
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6,
                MessageUtil.smallText("&f" + enderChest.getName() + " &8| &f" + user.getNickName()));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = {45, 46, 47, 48, 49, 50, 51, 52, 53};
        Set<Integer> protectedSlots = new HashSet<>(Arrays.asList(glassBlueSlots));

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(49, new ItemBuilder(Material.BARRIER)
                .setName("&4cofnij")
                .build());

        Map<Integer, String> serializedItems = enderChest.getItemStackSerializableMap();
        for (Map.Entry<Integer, String> entry : serializedItems.entrySet()) {
            ItemStack itemStack = ItemStackSerializable.readItemStack(entry.getValue());
            if (itemStack != null) {
                inventory.setItem(entry.getKey(), itemStack);
            }
        }

        simpleInventory.click(event -> {
            if (protectedSlots.contains(event.getSlot())) {
                event.setCancelled(true);
                if (event.getSlot() == 49) {
                    showMainPage(player, user);
                }
            }
        });

        simpleInventory.close(inventoryCloseEvent -> {
            Map<Integer, String> updatedItems = new HashMap<>();
            for (int i = 0; i < inventory.getSize(); i++) {
                if (!protectedSlots.contains(i)) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack != null && itemStack.getType() != Material.AIR) {
                        updatedItems.put(i, ItemStackSerializable.write(itemStack));
                    }
                }
            }
            enderChest.setItemStackSerializableMap(updatedItems);
            player.playSound(player, Sound.BLOCK_ENDER_CHEST_CLOSE, 5 ,5);
        });
        player.openInventory(inventory);
    }


    public void showMainPage(final Player player, User user) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&DEnderChesty: &f" + user.getNickName()));
        Inventory inventory = simpleInventory.getInventory();
        NamespacedKey key = new NamespacedKey(this.survivalPlugin, "enderchest_id");

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(48, new ItemBuilder(Material.BARRIER)
                .setName("&4zamknij")
                .build());

        inventory.setItem(50, new ItemBuilder(Material.EMERALD)
                .setName("&anowy &denderchest &2$")
                .setLore(
                        "",
                        "&fꑅ 4 sloty",
                        "&fꑇ 6 sloty",
                        "&fꑍ 9 sloty",
                        "",
                        "&8| &7koszt: &f80 tyś &amonet",
                        "",
                        "&akliknij aby kupić."
                )
                .build());

        for (EnderChest enderChest : user.getEnderChests()) {
            ItemStack itemStack = new ItemBuilder(Material.ENDER_CHEST)
                    .setName(enderChest.getName())
                    .setLore(
                            "",
                            "&2&lLPM &f- &akliknij aby otworzyć.",
                            "&9&lPPM &f- &3kliknij aby zmienić nazwe."
                    )
                    .build();

            ItemMeta meta = itemStack.getItemMeta();
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, enderChest.getUuid().toString());
            itemStack.setItemMeta(meta);
            inventory.addItem(itemStack);

        }

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (event.getSlot() == 48) {
                player.closeInventory();
                return;
            }

            if (event.getSlot() == 50) {
                int maxEnderChests = 2;

                if (player.hasPermission("cebulasmp.ec.vip")) {
                    maxEnderChests = 4;
                }
                if (player.hasPermission("cebulasmp.ec.mvip")) {
                    maxEnderChests = 6;
                }
                if (player.hasPermission("cebulasmp.ec.cebulak")) {
                    maxEnderChests = 9;
                }

                if (user.getEnderChests().size() >= maxEnderChests) {
                    MessageUtil.sendTitle(player, "", "&cOsiągnięto limit ender chestów. &8/pomoc", 20, 50, 20);
                    player.closeInventory();
                    return;
                }

                if (user.getMoney() >= 80000) {
                    user.removeMoney(80000);
                    user.getEnderChests().add(new EnderChest("EnderChest" + (user.getEnderChests().size() + 1), new HashMap<>()));
                    this.showMainPage(player, user);
                } else {
                    MessageUtil.sendTitle(player, "", "&cNie stać cię.", 20, 50, 20);
                    player.closeInventory();
                }
                return;
            }

            ItemMeta meta = event.getCurrentItem().getItemMeta();
            if (meta == null) return;

            PersistentDataContainer container = meta.getPersistentDataContainer();

            if (!container.has(key, PersistentDataType.STRING)) return;

            String enderChestId = container.get(key, PersistentDataType.STRING);
            if (enderChestId == null) return;

            Optional<EnderChest> optionalChest = user.getEnderChests().stream()
                    .filter(ec -> ec.getUuid().toString().equals(enderChestId))
                    .findAny();

            if (optionalChest.isEmpty()) return;

            EnderChest clickedEnderChest = optionalChest.get();

            if (event.getClick().equals(ClickType.LEFT)) {
                this.showEnderChest(player, clickedEnderChest, user);
            } else if (event.getClick().equals(ClickType.RIGHT)) {
                try {
                    this.enderChestSignGui.open(player, clickedEnderChest);
                } catch (SignGUIVersionException e) {
                    e.printStackTrace();
                }
            }
        });

        player.openInventory(inventory);
    }
}
