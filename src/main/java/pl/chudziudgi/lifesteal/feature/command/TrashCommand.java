package pl.chudziudgi.lifesteal.feature.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

@Command(name = "kosz")
public class TrashCommand {

    private final SurvivalPlugin survivalPlugin;

    public TrashCommand(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    @Execute
    void execute(@Context Player sender) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin,9 * 6, MessageUtil.smallText(MessageUtil.smallText("&e&lkosz")));
        Inventory inventory = simpleInventory.getInventory();
        sender.openInventory(inventory);
    }
}
