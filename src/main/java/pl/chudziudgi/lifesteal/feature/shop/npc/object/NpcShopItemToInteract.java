package pl.chudziudgi.lifesteal.feature.shop.npc.object;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
public class NpcShopItemToInteract implements Serializable {
    private final ItemStack itemStack;
    private final int itemSlot;
    private final double buyPrice;
    private final double sellPrice;

    public NpcShopItemToInteract(ItemStack itemStack, int itemSlot, double buyPrice, double sellPrice) {
        this.itemStack = itemStack;
        this.itemSlot = itemSlot;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
