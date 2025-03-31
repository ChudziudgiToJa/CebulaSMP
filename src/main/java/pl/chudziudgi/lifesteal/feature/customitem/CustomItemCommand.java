package pl.chudziudgi.lifesteal.feature.customitem;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.CustomItemConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.Optional;

@Command(name = "custom-itemy")
@Permission("cebulasmp.command.customitemy.admin")
public class CustomItemCommand {

    private final CustomItemGui customItemGui;

    public CustomItemCommand(CustomItemGui customItemGui) {
        this.customItemGui = customItemGui;
    }

    @Execute()
    public void gui(@Context Player player) {
        this.customItemGui.show(player);
    }
}
