package pl.chudziudgi.lifesteal.feature.shop.npc.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.NpcShopConfiguration;
import pl.chudziudgi.lifesteal.feature.shop.npc.object.NpcShopItemToInteract;
import pl.chudziudgi.lifesteal.feature.shop.npc.object.NpcShop;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;
import java.util.HashMap;

public class NpcShopInventory {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;
    private final NpcShopConfiguration npcShopConfiguration;

    public NpcShopInventory(SurvivalPlugin survivalPlugin, UserService userService, NpcShopConfiguration npcShopConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
        this.npcShopConfiguration = npcShopConfiguration;
    }

    public void showAllShops(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9*6, MessageUtil.smallText("&flista sklepów"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        for (NpcShop npcShop : this.npcShopConfiguration.npcShops) {
            inventory.addItem(new ItemBuilder(npcShop.getNpcShopItemToInteracts().getFirst().getItemStack().getType())
                    .setName("&fSklep u: &a" + npcShop.getName())
                    .setLore("", "&akliknij aby otworzyć")
                    .build()
            );
        }

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            for (NpcShop npcShop : this.npcShopConfiguration.npcShops) {
                if (event.getCurrentItem().getType().equals(npcShop.getNpcShopItemToInteracts().getFirst().getItemStack().getType())) {
                    show(player, npcShop);
                }
            }
        });

        player.openInventory(inventory);
    }

    public void show(final Player player, final NpcShop npcShop) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 54, MessageUtil.smallText("&fsklep: " + npcShop.getName()));
        Inventory inventory = simpleInventory.getInventory();
        User user = userService.findUserByNickName(player.getName());


        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(49, new ItemBuilder(Material.BARRIER).setName("&cOtwórz wszystkie sklepy.").setLore("", "&akliknij aby otworzyć").build());

        for (NpcShopItemToInteract npcShopItemToInteract : npcShop.getNpcShopItemToInteracts()) {

            ItemStack itemBuilder = new ItemBuilder(npcShopItemToInteract.getItemStack().getType())
                    .setName("&l&f" + npcShopItemToInteract.getItemStack().getType())
                    .setLore(
                            " ",
                            "&e&lppm &7- &ckliknij aby sprzedać za &f" + DecimalUtil.getFormat(npcShopItemToInteract.getSellPrice()) + " monet",
                            "&6&lppm + shift &7- &4kliknij aby sprzedać " + npcShopItemToInteract.getItemStack().getMaxStackSize() + " za &f" + DecimalUtil.getFormat(npcShopItemToInteract.getItemStack().getMaxStackSize() * npcShopItemToInteract.getSellPrice()) + " monet",
                            "&6&lq &7- &dkliknij aby sprzedać całe eq",
                            "&a&llpm &7- &akliknij aby kupić za &f" + DecimalUtil.getFormat(npcShopItemToInteract.getBuyPrice()) + " monet",
                            "&2&llpm + shift &7- &2kliknij aby kupić " + npcShopItemToInteract.getItemStack().getMaxStackSize() + " za &f" + DecimalUtil.getFormat(npcShopItemToInteract.getItemStack().getMaxStackSize() * npcShopItemToInteract.getBuyPrice()) + " monet"
                    )
                    .build();

            inventory.setItem(npcShopItemToInteract.getItemSlot(), itemBuilder);
        }

        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (event.getSlot() == 49) {
                showAllShops(player);
                return;
            }

            for (NpcShopItemToInteract npcShopItemToInteract : npcShop.getNpcShopItemToInteracts()) {

                if (event.getSlot() == npcShopItemToInteract.getItemSlot()) {

                    if (event.getClick().equals(ClickType.LEFT)) {
                        if (user.getMoney() >= npcShopItemToInteract.getBuyPrice()) {
                            user.setMoney(user.getMoney() - npcShopItemToInteract.getBuyPrice());
                            ItemStack item = new ItemStack(npcShopItemToInteract.getItemStack().getType(), 1);

                            HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);
                            if (!leftover.isEmpty()) {
                                player.getWorld().dropItemNaturally(player.getLocation(), item);
                            }

                            MessageUtil.sendMessage(player, "&aKupiłeś przedmiot: " + item.getType() + " za " + npcShopItemToInteract.getBuyPrice() + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz wystarczająco pieniędzy, aby kupić ten przedmiot.", 20, 50, 20);
                            player.closeInventory();
                        }
                        return;
                    }

                    if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
                        double totalPrice = npcShopItemToInteract.getBuyPrice() * npcShopItemToInteract.getItemStack().getMaxStackSize();
                        if (user.getMoney() >= totalPrice) {
                            user.setMoney(user.getMoney() - totalPrice);
                            ItemStack item = new ItemStack(npcShopItemToInteract.getItemStack().getType(), npcShopItemToInteract.getItemStack().getMaxStackSize());

                            HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);
                            if (!leftover.isEmpty()) {
                                leftover.values().forEach(remaining -> player.getWorld().dropItemNaturally(player.getLocation(), remaining));
                            }

                            MessageUtil.sendMessage(player, "&aKupiłeś " + npcShopItemToInteract.getItemStack().getMaxStackSize() + " przedmioty: " + item.getType() + " za " + totalPrice + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz wystarczająco pieniędzy, aby kupić " + npcShopItemToInteract.getItemStack().getMaxStackSize() + " przedmioty.", 20, 50, 20);
                            player.closeInventory();
                        }
                        return;
                    }

                    if (event.getClick().equals(ClickType.RIGHT)) {
                        ItemStack itemToSell = new ItemStack(npcShopItemToInteract.getItemStack().getType(), 1);
                        if (player.getInventory().containsAtLeast(itemToSell, 1)) {
                            player.getInventory().removeItem(itemToSell);
                            user.setMoney(user.getMoney() + npcShopItemToInteract.getSellPrice());
                            MessageUtil.sendMessage(player, "&aSprzedałeś przedmiot: " + itemToSell.getType() + " za " + npcShopItemToInteract.getSellPrice() + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz tego przedmiotu w ekwipunku, aby go sprzedać.", 20, 50, 20);
                            player.closeInventory();
                        }
                        return;
                    }

                    if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                        ItemStack itemToSell = new ItemStack(npcShopItemToInteract.getItemStack().getType(), npcShopItemToInteract.getItemStack().getMaxStackSize());
                        if (player.getInventory().containsAtLeast(itemToSell, npcShopItemToInteract.getItemStack().getMaxStackSize())) {
                            player.getInventory().removeItem(itemToSell);
                            double totalPrice = npcShopItemToInteract.getSellPrice() * npcShopItemToInteract.getItemStack().getMaxStackSize();
                            user.setMoney(user.getMoney() + totalPrice);
                            MessageUtil.sendMessage(player, "&aSprzedałeś " + npcShopItemToInteract.getItemStack().getMaxStackSize() + " przedmioty: " + itemToSell.getType() + " za " + totalPrice + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz " + npcShopItemToInteract.getItemStack().getMaxStackSize() + " tych przedmiotów w ekwipunku, aby je sprzedać.", 20, 50, 20);
                            player.closeInventory();
                        }
                        return;
                    }
                    if (event.getClick().equals(ClickType.DROP)) {
                        ItemStack itemStackToSell = npcShopItemToInteract.getItemStack();
                        Material itemTypeToSell = itemStackToSell.getType();
                        int totalAmount = 0;
                        for (ItemStack item : player.getInventory().getContents()) {
                            if (item != null && item.getType() == itemTypeToSell) {
                                totalAmount += item.getAmount();
                            }
                        }
                        if (totalAmount > 0) {
                            player.getInventory().remove(itemTypeToSell);
                            double totalPrice = npcShopItemToInteract.getSellPrice() * totalAmount;
                            user.setMoney(user.getMoney() + totalPrice);
                            MessageUtil.sendMessage(player, "&aSprzedałeś " + totalAmount + " przedmiotów: " + itemTypeToSell + " za " + totalPrice + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz tych przedmiotów w ekwipunku, aby je sprzedać.", 20, 50, 20);
                            player.closeInventory();
                        }
                    }
                }
            }
        });
        player.openInventory(inventory);
    }
}
