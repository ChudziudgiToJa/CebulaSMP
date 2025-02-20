package pl.chudziudgi.lifesteal.feature.help;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.SurvivalPlugin;

@Command(name = "pomoc", aliases = {"cebulak", "vip", "mvip"})
public class HelpCommand {

    private final SurvivalPlugin survivalPlugin;
    private final HelpInventory helpInventory;

    public HelpCommand(SurvivalPlugin survivalPlugin, HelpInventory helpInventory) {
        this.survivalPlugin = survivalPlugin;
        this.helpInventory = helpInventory;
    }

    @Execute
    void execute(@Context Player sender) {
        this.helpInventory.show(sender.getPlayer());
    }
}
