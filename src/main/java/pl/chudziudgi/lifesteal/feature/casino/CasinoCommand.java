package pl.chudziudgi.lifesteal.feature.casino;


import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.SurvivalPlugin;

@Command(name = "kasyno")
@Permission("cebulasmp.kasyno")
public class CasinoCommand {

    private final SurvivalPlugin survivalPlugin;
    private final CasinoManager casinoManager;

    public CasinoCommand(SurvivalPlugin survivalPlugin, CasinoManager casinoManager) {
        this.survivalPlugin = survivalPlugin;
        this.casinoManager = casinoManager;
    }


    @Execute
    public void execute(@Context Player player) {
        new CasinoInventory(this.survivalPlugin, casinoManager).show(player);
    }
}
