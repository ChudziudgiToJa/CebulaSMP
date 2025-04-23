package pl.chudziudgi.lifesteal.feature.enchanter;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.*;


public class EnchanterInventory {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;
    private final Random random;

    public EnchanterInventory(SurvivalPlugin survivalPlugin, UserService userService, Random random) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
        this.random = random;
    }

    public void show(final Player player) {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType() == Material.AIR) {
            MessageUtil.sendTitle(player, "", "&cMusisz trzymać przedmiot z enchantami.", 20, 50, 20);
            return;
        }

        Map<Enchantment, Integer> enchantments = itemInHand.getEnchantments();
        if (enchantments.isEmpty()) {
            MessageUtil.sendTitle(player, "", "&cTen przedmiot nie ma enchantów.", 20, 50, 20);
            return;
        }

        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fMag"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49};

        Arrays.stream(glassBlueSlots).forEach(slot ->
                inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())
        );

        inventory.setItem(20, player.getInventory().getItemInMainHand());

        inventory.setItem(22, new ItemBuilder(Material.PAPER)
                .setName("&7Rangi posiadają większe szanse na powodzenie")
                .addLore("",
                        "&f⚙ 10%",
                        "&fꑅ 20%",
                        "&fꑇ 30% ",
                        "&fꑍ 50% "
                )
                .build()
        );

        inventory.setItem(24, new ItemBuilder(Material.ENCHANTED_BOOK)
                .setName("&b&lZdejmij enchant")
                .addLore(
                        "",
                        "&7Zdejmij zaklęcia ze swojego przedmiotu!",
                        "",
                        "&e➤ &fTwój przedmiot &7zostanie",
                        "&7pozbawiony wszystkich enchantów.",
                        "",
                        "&e➤ &fZaklęcia &7zostaną zapisane",
                        "&7na &dzaklętej książce&7.",
                        "",
                        "&c⚠ &7Upewnij się, że trzymasz",
                        "&7odpowiedni przedmiot w ręce!",
                        "",
                        "&aKoszt: " + DecimalUtil.getFormat(10000) + " monet"
                )
                .build()
        );

        simpleInventory.click(event -> {
            event.setCancelled(true);
            int slot = event.getSlot();
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user == null) return;

            if (slot == 24) {
                double cost = 10000;
                if (user.getMoney() < cost) {
                    MessageUtil.sendTitle(player, "", "&cNie masz wystarczająco monet!", 20, 50, 20);
                    return;
                }

                double chance = 0.10;
                if (player.hasPermission("mag.vip")) chance = 0.20;
                if (player.hasPermission("mag.mvip")) chance = 0.30;
                if (player.hasPermission("mag.cebulak")) chance = 0.50;

                user.setMoney(user.getMoney() - cost);

                if (Math.random() <= chance) {
                    List<Enchantment> keys = new ArrayList<>(enchantments.keySet());
                    Enchantment randomEnchant = keys.get(this.random.nextInt(keys.size()));
                    int level = enchantments.get(randomEnchant);

                    itemInHand.removeEnchantment(randomEnchant);

                    ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                    meta.addStoredEnchant(randomEnchant, level, true);
                    enchantedBook.setItemMeta(meta);

                    HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(enchantedBook);
                    if (!leftover.isEmpty()) {
                        leftover.values().forEach(item ->
                                player.getWorld().dropItemNaturally(player.getLocation(), item)
                        );
                        MessageUtil.sendMessage(player, "&eTwój ekwipunek był pełny, zaklęta książka została wyrzucona na ziemię.");
                    }

                    for (Enchantment enchant : new HashMap<>(itemInHand.getEnchantments()).keySet()) {
                        itemInHand.removeEnchantment(enchant);
                    }
                    MessageUtil.sendTitle(player, "", "&aUdało się uratować enchant!", 20, 50, 20);
                } else {
                    MessageUtil.sendTitle(player, "", "&cPrzedmiot spalił się.", 20, 50, 20);
                    player.getInventory().remove(player.getInventory().getItemInMainHand());
                }
                player.closeInventory();
            }
        });
        player.openInventory(inventory);
    }
}
