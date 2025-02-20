package pl.chudziudgi.lifesteal.feature.shop.npc.controller;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.chudziudgi.lifesteal.configuration.implementation.NpcShopConfiguration;
import pl.chudziudgi.lifesteal.feature.shop.npc.inventory.NpcShopInventory;
import pl.chudziudgi.lifesteal.feature.shop.npc.object.NpcShop;

public class NpcShopController implements Listener {

    private final NpcShopConfiguration npcShopConfiguration;
    private final NpcShopInventory npcShopInventory;

    public NpcShopController(NpcShopConfiguration npcShopConfiguration, NpcShopInventory npcShopInventory) {
        this.npcShopConfiguration = npcShopConfiguration;
        this.npcShopInventory = npcShopInventory;
    }

    @EventHandler
    public void click(NPCRightClickEvent event) {
        Player player = event.getClicker();

        for (NpcShop npcShop : this.npcShopConfiguration.npcShops) {
            if (event.getNPC().getId() == npcShop.getNpcId()) {
                npcShopInventory.show(player, npcShop);
                player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5 ,5);
                event.setCancelled(true);
            }
        }
    }
}
