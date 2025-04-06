package pl.chudziudgi.lifesteal.feature.customitem;


import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "custom-itemy")
@Permission("cebulasmp.customitemy.admin")
public class CustomItemCommand {

    private final CustomItemInventory customItemInventory;

    public CustomItemCommand(CustomItemInventory customItemInventory) {
        this.customItemInventory = customItemInventory;
    }


    @Execute
    public void execiute(@Context Player player) {
        this.customItemInventory.show(player);
    }
}
