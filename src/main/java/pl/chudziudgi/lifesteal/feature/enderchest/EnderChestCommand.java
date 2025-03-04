package pl.chudziudgi.lifesteal.feature.enderchest;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

@Command(name = "enderchest", aliases = "ec")
public class EnderChestCommand {

    private final UserService userService;
    private final EnderChestIventory enderChestIventory;

    public EnderChestCommand(UserService userService, EnderChestIventory enderChestIventory) {
        this.userService = userService;
        this.enderChestIventory = enderChestIventory;
    }

    @Execute
    @Permission("cebulasmp.enderchest")
    void openOwn(@Context Player player) {
        User user = this.userService.findUserByUUID(player.getUniqueId());
        if (user == null) return;
        this.enderChestIventory.showMainPage(player, user);
    }

    @Execute
    @Permission("cebulasmp.enderchest.other")
    void openOwn(@Context Player player, @Arg("Gracz") User user) {
        this.enderChestIventory.showMainPage(player, user);
    }

}
