package pl.chudziudgi.lifesteal.feature.disco;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

public class DiscoInventory {

    private final SurvivalPlugin survivalPlugin;

    public DiscoInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void show(final Player player, User user) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9, MessageUtil.smallText("&fDisco:"));
        Inventory inventory = simpleInventory.getInventory();

        inventory.setItem(0, new ItemBuilder(Material.EMERALD_BLOCK)
                .setName("&ftryb: &aturbo")
                .setLore("", "", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(1, new ItemBuilder(Material.REDSTONE)
                .setName("&ftryb: &apłynny")
                .setLore("", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(2, new ItemBuilder(Material.DIAMOND_BLOCK)
                .setName("&ftryb: &alosowy")
                .setLore("", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(3, new ItemBuilder(Material.REDSTONE_LAMP)
                .setName("&ftryb: &cdevil &8(private by chudziudgi)")
                .setLore("", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(8, new ItemBuilder(Material.BARRIER)
                .setName("&ckliknij aby wyłączyć.")
                .build());


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (!player.hasPermission("cebulasmp.disco")) {
                MessageUtil.sendTitle(player, "&a", "&cnie posiadasz wykupionej disco zbroi &8/itemshop", 20, 50, 20);
                return;
            }

            switch (event.getSlot()) {
                case 0 -> {
                    player.closeInventory();
                    user.setDiscoType(DiscoType.TURBO);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &fturbo", 20, 50, 20);
                }
                case 1 -> {
                    player.closeInventory();
                    user.setDiscoType(DiscoType.SMOOTH);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &fpłynny", 20, 50, 20);
                }
                case 2 -> {
                    player.closeInventory();
                    user.setDiscoType(DiscoType.RANDOM);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &flosowy", 20, 50, 20);
                }
                case 3 -> {
                    if (!player.getName().equals("Chudziudgi")) {
                        player.closeInventory();
                        return;
                    }
                    player.closeInventory();
                    user.setDiscoType(DiscoType.DEVIL);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &cdevil", 20, 50, 20);
                }
                case 8 -> {
                    player.closeInventory();
                    user.setDiscoType(DiscoType.CLEAR);
                    MessageUtil.sendTitle(player, "&a", "&cwyłączono disco", 20, 50, 20);
                    Bukkit.getScheduler().runTaskLaterAsynchronously(this.survivalPlugin, () -> {
                        Bukkit.getOnlinePlayers().forEach(player1 -> {
                            DiscoPackethandler.refreshArmorPacket(player ,player1);
                        });
                    },20);
                }
            }
        });
        player.openInventory(inventory);
    }
}
