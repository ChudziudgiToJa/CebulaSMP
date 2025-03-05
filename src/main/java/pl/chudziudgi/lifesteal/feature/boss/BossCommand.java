package pl.chudziudgi.lifesteal.feature.boss;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "boss")
@Permission("cebulasmp.spawn.boss")
public class BossCommand {

    private final BossManager bossManager;

    public BossCommand(BossManager bossManager) {
        this.bossManager = bossManager;
    }


    @Execute
    void spawn(@Context Player player) {
        if (bossManager.getBoss() != null) {
            MessageUtil.sendMessage(player, "&cboss już istnieje");
            return;
        }
        bossManager.spawn(player.getLocation());
        MessageUtil.sendMessage(player, "&aboss został stworzony w twojej lokalizacji.");
    }
}
