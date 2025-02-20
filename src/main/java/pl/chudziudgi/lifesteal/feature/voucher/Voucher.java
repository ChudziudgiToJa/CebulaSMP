package pl.chudziudgi.lifesteal.feature.voucher;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
public class Voucher implements Serializable {
    private final String itemStackString;
    private final String command;

    public Voucher(String itemStackString, String command) {
        this.itemStackString = itemStackString;
        this.command = command;
    }
}
