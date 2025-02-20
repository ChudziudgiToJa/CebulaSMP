package pl.chudziudgi.lifesteal.feature.crafting;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "craftingi")
public class CraftingCommand {

    private final CraftingInventory craftingInventory;

    public CraftingCommand(CraftingInventory craftingInventory) {
        this.craftingInventory = craftingInventory;
    }

    @Execute
    void openInv(@Context Player player) {
        this.craftingInventory.showItems(player);
    }

}
