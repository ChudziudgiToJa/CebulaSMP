package pl.chudziudgi.lifesteal.feature.customitem;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
public class CustomItemData implements Serializable {

    private final ItemStack itemStack;
    private final long time;

    public CustomItemData(ItemStack itemStack, long time) {
        this.itemStack = itemStack;
        this.time = time;
    }
}
