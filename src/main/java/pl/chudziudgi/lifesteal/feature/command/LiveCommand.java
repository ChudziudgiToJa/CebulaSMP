package pl.chudziudgi.lifesteal.feature.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "live")
@Permission("cebulasmp.media.live.command")
public class LiveCommand {

    @Execute
    void onCommand(@Context Player player, @Arg String s) {
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            player1.sendMessage("");
            MessageUtil.sendMessage(player1, "&d&lLIVE: &7gracza &f" + player.getName() + " &4&l⬇");
            player1.sendMessage(s);
            player1.sendMessage("");
        });
    }

}
