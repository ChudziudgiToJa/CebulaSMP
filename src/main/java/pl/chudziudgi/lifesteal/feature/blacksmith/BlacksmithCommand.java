package pl.chudziudgi.lifesteal.feature.blacksmith;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "kowal")
@Permission("cebulasmp.kowal.command")
public class BlacksmithCommand {

    private final BlacksmithInventory blacksmithInventory;

    public BlacksmithCommand(BlacksmithInventory blacksmithInventory) {
        this.blacksmithInventory = blacksmithInventory;
    }

    @Execute
    public void  execute(@Context Player player) {
        this.blacksmithInventory.show(player);
    }
}
