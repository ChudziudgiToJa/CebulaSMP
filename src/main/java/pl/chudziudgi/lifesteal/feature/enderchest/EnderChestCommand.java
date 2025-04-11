package pl.chudziudgi.lifesteal.feature.enderchest;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "enderchest", aliases = "ec")
public class EnderChestCommand {

    private final UserService userService;
    private final EnderChestInventory enderChestInventory;

    public EnderChestCommand(UserService userService, EnderChestInventory enderChestInventory) {
        this.userService = userService;
        this.enderChestInventory = enderChestInventory;
    }

    @Execute
    @Permission("cebulasmp.enderchest")
    void openOwn(@Context Player player) {
        User user = this.userService.findUserByUUID(player.getUniqueId());
        if (user == null) return;
        this.enderChestInventory.showMainPage(player, user);
    }

    @Execute
    @Permission("cebulasmp.enderchest.other")
    void openOther(@Context Player player, @Arg("Gracz") User user) {
        this.enderChestInventory.showMainPage(player, user);
    }

    @Execute(name = "add")
    @Permission("cebulasmp.enderchest.add")
    void add(@Context CommandSender player, @Arg("Gracz") User user, @Arg("czy ma sparwdzać limit") boolean b) {
    }
}
