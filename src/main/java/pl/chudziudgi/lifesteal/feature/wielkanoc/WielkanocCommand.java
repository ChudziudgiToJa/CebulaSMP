package pl.chudziudgi.lifesteal.feature.wielkanoc;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "wielkanoc")
@Permission("cebulasmp.command.wielkanoc")
public class WielkanocCommand {

    private final PluginConfiguration pluginConfiguration;

    public WielkanocCommand(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute(name = "ustaw-key")
    public void setKey(@Context Player player) {
        this.pluginConfiguration.wielkanocItemStack = ItemStackSerializable.write(player.getInventory().getItemInMainHand());
        MessageUtil.sendMessage(player, "Ustawiono nowy item na drop z trway wielkanocnej");
        return;
    }
}
