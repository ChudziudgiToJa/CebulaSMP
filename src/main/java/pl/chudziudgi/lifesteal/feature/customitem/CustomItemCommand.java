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

    private final CustomItemConfiguration customItemConfiguration;
    private final CustomItemGui customItemGui;

    public CustomItemCommand(CustomItemConfiguration customItemConfiguration, CustomItemGui customItemGui) {
        this.customItemConfiguration = customItemConfiguration;
        this.customItemGui = customItemGui;
    }

    @Execute()
    public void gui(@Context Player player) {
        this.customItemGui.show(player);
    }

    @Execute(name = "ustaw")
    public void setItem(@Context Player player, @Arg CustomItemType customItemType) {
        ItemStack handItemStack = player.getInventory().getItemInMainHand();

        Optional<CustomItem> foundItem = this.customItemConfiguration.customItems.stream()
                .filter(customItem -> customItem.getCustomItemType() == customItemType)
                .findFirst();

        if (foundItem.isEmpty()) {
            MessageUtil.sendMessage(player, "&cNie ma takiego przedmiotu");
            return;
        }
        foundItem.get().setItemStack(ItemStackSerializable.write(handItemStack));
        MessageUtil.sendMessage(player, "&aUstawiono nowy itemstack dla: " + customItemType);
    }
}
