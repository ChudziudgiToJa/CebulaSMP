package pl.chudziudgi.lifesteal.feature.vanish;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Permission("cebulasmp.command.vanish")
@Command(name = "vanish", aliases = {"v"})
public class VanishCommand {

    private final UserService userService;
    private final VanishHandler vanishHandler;
    private final SurvivalPlugin survivalPlugin;

    public VanishCommand(UserService userService, VanishHandler vanishHandler, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.vanishHandler = vanishHandler;
        this.survivalPlugin = survivalPlugin;
    }

    @Execute
    void own(@Context Player player) {
        User user = userService.findUserByUUID(player.getUniqueId());
        if (user == null) {
            MessageUtil.sendMessage(player, "&cNie odnaleziono Twojego użytkownika w bazie danych.");
            return;
        }
        vanishHandler.toggleVanish(player, user, survivalPlugin);
    }

    @Execute
    void target(@Context Player player, @OptionalArg String targetName) {
        if (targetName == null || targetName.isEmpty()) {
            MessageUtil.sendMessage(player, "&cPodaj nick gracza, aby zmienić jego status vanish.");
            return;
        }
        User user = userService.findUserByNickName(targetName);
        if (user == null) {
            MessageUtil.sendMessage(player, "&cNie odnaleziono użytkownika w bazie danych.");
            return;
        }
        vanishHandler.toggleVanish(player, user, survivalPlugin);
    }
}