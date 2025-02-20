package pl.chudziudgi.lifesteal.feature.lootcase;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.*;

public class LootCaseInventory {

    private final SurvivalPlugin survivalPlugin;

    public LootCaseInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void showPreview(final Player player, final LootCase lootCase) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText(lootCase.getString()));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        Integer[] openCaseItem = new Integer[]{
                48, 49, 50,
        };

        Arrays.stream(openCaseItem).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.LIME_DYE)
                        .setName("&akliknij aby otworzyć")
                        .build()));

        List<LootCaseChance> sortedLootItems = new ArrayList<>(lootCase.getDropItems());
        sortedLootItems.sort(Comparator.comparingDouble(LootCaseChance::getChance));

        List<LootCaseChance> originalLootItems = lootCase.getDropItems();

        for (LootCaseChance lootCaseChance : sortedLootItems) {
            ItemStack lootItemStack = ItemStackSerializable.readItemStack(lootCaseChance.getItemStackInString());
            ItemMeta itemMeta = lootItemStack.getItemMeta();

            if (itemMeta != null) {
                List<String> lore = itemMeta.hasLore() ? new ArrayList<>(itemMeta.getLore()) : new ArrayList<>();
                lore.add(" ");
                lore.add(MessageUtil.smallText("&7szansa na wylosowanie: &a" + lootCaseChance.getChance() + "%"));

                if (player.hasPermission("cebulasmp.case.admin")) {
                    int index = 0;
                    for (LootCaseChance originalChance : originalLootItems) {
                        if (originalChance.equals(lootCaseChance)) {
                            lore.add(" ");
                            lore.add(MessageUtil.smallText("&4ADMIN &fnr itemku: " + (index + 1)));
                            break;
                        }
                        index++;
                    }
                }

                itemMeta.setLore(lore);
                lootItemStack.setItemMeta(itemMeta);
            }

            inventory.addItem(lootItemStack);
        }


        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (lootCase.getDropItems().isEmpty()) return;

            if (Arrays.asList(openCaseItem).contains(event.getSlot())) {
                if (!player.getInventory().containsAtLeast(ItemStackSerializable.readItemStack(lootCase.getKeyItemStack()), 1)) {
                    MessageUtil.sendTitle(player, "", "&cNie posiadasz klucza zakup pod &7/itemshop", 20, 50, 20);
                    player.setVelocity(player.getLocation().toVector().subtract(lootCase.getLocation().toVector()).normalize().multiply(1.5));
                    player.closeInventory();
                    return;
                }

                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    ItemStack slotItem = player.getInventory().getItem(i);
                    if (slotItem != null && slotItem.isSimilar(ItemStackSerializable.readItemStack(lootCase.getKeyItemStack()))) {
                        if (slotItem.getAmount() > 1) {
                            slotItem.setAmount(slotItem.getAmount() - 1);
                        } else {
                            player.getInventory().setItem(i, null);
                        }
                        break;
                    }
                }

                ItemStack lootedItemStack = ItemStackSerializable.readItemStack(LootCaseManager.pickRandomItem(lootCase.getDropItems()).getItemStackInString());

                if (lootedItemStack == null) return;
                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(lootedItemStack);
                leftover.values().forEach(remaining ->
                        player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                );
            }
        });
        player.openInventory(inventory);
    }
}