package pl.chudziudgi.lifesteal.feature.economy;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "eco")
@Permission("cebulasmp.command.eco")
public class EconomyCommand {

    private final UserService userService;

    public EconomyCommand(UserService userService) {
        this.userService = userService;
    }

    @Execute
    void execute(@Context CommandSender player, @Arg User user, @Arg EconomyCommandType economyCommandType, @Arg Double amount) {
        double currentMoney = user.getMoney();
        String formattedAmount = String.format("%.2f", amount);

        switch (economyCommandType) {
            case ADD -> {
                user.setMoney(currentMoney + amount);
                MessageUtil.sendMessage(player, "&aDodano &f" + formattedAmount + " &ado konta gracza &f" + user.getNickName() + "&a.");
            }
            case SET -> {
                user.setMoney(amount);
                MessageUtil.sendMessage(player, "&aUstawiono saldo gracza &f" + user.getNickName() + " &ana &f" + formattedAmount + "&a.");
            }
            case REMOVE -> {
                double newAmount = Math.max(0, currentMoney - amount);
                user.setMoney(newAmount);

                if (newAmount == 0) {
                    MessageUtil.sendMessage(player, "&aSaldo gracza &f" + user.getNickName() + " &azostało wyzerowane.");
                } else {
                    MessageUtil.sendMessage(player, "&aOdjęto &f" + formattedAmount + " &az konta gracza &f" + user.getNickName() + "&a.");
                }
            }
        }
    }
}
