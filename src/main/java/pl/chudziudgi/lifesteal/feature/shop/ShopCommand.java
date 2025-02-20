package pl.chudziudgi.lifesteal.feature.shop;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "sklep")
public class ShopCommand {

    private final ShopInventory shopInventory;

    public ShopCommand(ShopInventory shopInventory) {
        this.shopInventory = shopInventory;
    }

    @Execute
    void openShops(@Context Player player) {
        this.shopInventory.show(player);
    }
}
