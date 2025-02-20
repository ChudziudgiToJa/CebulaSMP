package pl.chudziudgi.lifesteal.feature.economy;


import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "monety")
public class MoneyCommand {

    private final UserService userService;

    public MoneyCommand(UserService userService) {
        this.userService = userService;
    }

    @Execute
    void execute(@Context Player player) {
        User user = this.userService.findUserByNickName(player.getName());

        if (user ==null) return;
        MessageUtil.sendMessage(player, "&aStan konta: &2" + DecimalUtil.getFormat(user.getMoney()));
    }
}
