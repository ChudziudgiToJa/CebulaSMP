package pl.chudziudgi.lifesteal.feature.itemshop;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "itemshop")
public class ItemShopCommand {

    private final ItemShopInventory itemShopInventory;

    public ItemShopCommand(ItemShopInventory itemShopInventory) {
        this.itemShopInventory = itemShopInventory;
    }

    @Execute
    void execute(@Context Player player) {
        this.itemShopInventory.show(player);
    }
}
