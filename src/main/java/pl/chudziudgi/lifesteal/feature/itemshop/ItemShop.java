package pl.chudziudgi.lifesteal.feature.itemshop;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

@Getter
public class ItemShop implements Serializable {

    private final ItemStack itemStack;
    private final double price;
    private final List<String> commandList;

    public ItemShop(ItemStack itemStack, double price, List<String> commandList) {
        this.itemStack = itemStack;
        this.price = price;
        this.commandList = commandList;
    }
}
