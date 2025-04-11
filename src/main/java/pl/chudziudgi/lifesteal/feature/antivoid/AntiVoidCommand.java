package pl.chudziudgi.lifesteal.feature.antivoid;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "anti-void")
@Permission("cebulasmp.command.antivoid")
public class AntiVoidCommand {


    private final PluginConfiguration pluginConfiguration;

    public AntiVoidCommand(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute(name = "set")
    void setSpawn(@Context Player player) {
        this.pluginConfiguration.setLocation(player.getLocation());
        this.pluginConfiguration.save();
        MessageUtil.sendMessage(player, "&aUstawiono lokalizacje tp antivoid");
    }

}
