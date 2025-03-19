package pl.chudziudgi.lifesteal.feature.check;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "sprawdzanie")
@Permission("cebulasmp.command.sprawdzanie.admin")
public class CheckCommand {

    private final PluginConfiguration pluginConfiguration;
    private final CheckService checkService;

    public CheckCommand(PluginConfiguration pluginConfiguration, CheckService checkService) {
        this.pluginConfiguration = pluginConfiguration;
        this.checkService = checkService;
    }

    @Execute(name = "sprawdz")
    void checkPlayer(@Context Player player, @Arg("Gracz") Player target) {
        if (player.equals(target)) {
            MessageUtil.sendMessage(player, "&cNie możesz zrobić tego na sobie.");
            return;
        }

        if (target.hasPermission("cebulasmp.command.sprawdzanie.admin")) {
            MessageUtil.sendMessage(player, "&cNie możesz sprawdzać admina.");
            return;
        }

        if (checkService.contains(target.getUniqueId())) {
            MessageUtil.sendMessage(player, "&c%s jest już sprawdzany".formatted(target.getName()));
            return;
        }

        target.teleport(this.pluginConfiguration.checkSettings.jailLocation);
        this.checkService.add(target.getUniqueId());
        MessageUtil.sendMessage(player, "&asprawdzasz " + target.getName());
    }

    @Execute(name = "czysty")
    void unCheckPlayer(@Context Player player, @Arg("Gracz") Player target) {
        if (player.equals(target)) {
            MessageUtil.sendMessage(player, "&cNie możesz zrobić tego na sobie.");
            return;
        }
        if (!this.checkService.contains(target.getUniqueId())) {
            MessageUtil.sendMessage(player, "&cGracz nie jest podczas sprawdzania.");
            return;
        }

        this.checkService.remove(target.getUniqueId());
        MessageUtil.sendMessage(player, "&agracz %s jest czysty".formatted(target.getName()));
    }

    @Execute
    void setJailLocation(@Context Player player) {
        this.pluginConfiguration.checkSettings.jailLocation = player.getLocation();
        MessageUtil.sendMessage(player, "&aUstawiono lokalizację sprawdzania");
    }
}
