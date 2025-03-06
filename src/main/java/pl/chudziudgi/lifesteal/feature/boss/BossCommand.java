package pl.chudziudgi.lifesteal.feature.boss;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "boss")
public class BossCommand {

    private final BossManager bossManager;

    public BossCommand(BossManager bossManager) {
        this.bossManager = bossManager;
    }

    @Execute(name = "stwórz")
    @Permission("cebulasmp.spawn.boss")
    public void spawn(@Context Player player) {
        if (bossManager.getBoss() != null) {
            MessageUtil.sendMessage(player, "&cboss już istnieje");
            return;
        }
        bossManager.spawn(player.getLocation());
        MessageUtil.sendMessage(player, "&aboss został stworzony w twojej lokalizacji.");
    }

    @Execute(name = "zabij")
    @Permission("cebulasmp.spawn.boss")
    public void kill(@Context Player player) {
        if (bossManager.getBoss() == null) {
            MessageUtil.sendMessage(player, "&cboss nie istnieje");
            return;
        }
        bossManager.spawn(player.getLocation());
        MessageUtil.sendMessage(player, "&aboss został zabity.");
    }
}
