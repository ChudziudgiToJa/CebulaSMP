package pl.chudziudgi.lifesteal.feature.shop.time;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
@Setter
public class TimeShop implements Serializable {
    private final String icon;
    private final int price;
    private final String command;

    public TimeShop(String icon, int price, String command) {
        this.icon = icon;
        this.price = price;
        this.command = command;
    }
}
