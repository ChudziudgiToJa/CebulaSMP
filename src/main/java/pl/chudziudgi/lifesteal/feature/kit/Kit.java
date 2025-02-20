package pl.chudziudgi.lifesteal.feature.kit;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

@Getter
public class Kit implements Serializable {
    private final String name;
    private final long coolDownTime;
    private final ItemStack icon;
    private final String permission;
    private final List<ItemStack> itemStackArrayList;
    private final List<String> customItemList;

    public Kit(String name, long coolDownTime, ItemStack icon, String permission, List<ItemStack> itemStackArrayList, List<String> customItemList) {
        this.name = name;
        this.coolDownTime = coolDownTime;
        this.icon = icon;
        this.permission = permission;
        this.itemStackArrayList = itemStackArrayList;
        this.customItemList = customItemList;
    }
}
