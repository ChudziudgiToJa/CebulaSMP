package pl.chudziudgi.lifesteal.feature.disco;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

@Command(name = "disco")
public class DiscoCommand {

    private final DiscoInventory discoInventory;
    private final UserService userService;

    public DiscoCommand(DiscoInventory discoInventory, UserService userService) {
        this.discoInventory = discoInventory;
        this.userService = userService;
    }


    @Execute
    void openInventory(@Context Player player) {
        User user = this.userService.findUserByUUID(player.getUniqueId());
        if (user == null) return;
        this.discoInventory.show(player, user);
    }

}
