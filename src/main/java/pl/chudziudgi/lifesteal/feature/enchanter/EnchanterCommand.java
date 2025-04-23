package pl.chudziudgi.lifesteal.feature.enchanter;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.blacksmith.BlacksmithInventory;

@Command(name = "mag")
@Permission("cebulasmp.mag.command")
public class EnchanterCommand {

    private final EnchanterInventory enchanterInventory;

    public EnchanterCommand(EnchanterInventory enchanterInventory) {
        this.enchanterInventory = enchanterInventory;
    }

    @Execute
    public void  execute(@Context Player player) {
        this.enchanterInventory.show(player);
    }
}
