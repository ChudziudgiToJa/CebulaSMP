package pl.chudziudgi.lifesteal.feature.rabatecode;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "kod")
public class RabateCodeCommand {

    private final PluginConfiguration pluginConfiguration;
    private final UserService userService;

    public RabateCodeCommand(PluginConfiguration pluginConfiguration, UserService userService) {
        this.pluginConfiguration = pluginConfiguration;
        this.userService = userService;
    }

    @Execute
    void execute(@Context Player player, @Arg("kod") String code) {
        User user = this.userService.findUserByUUID(player.getUniqueId());
        if (user == null) return;
        this.pluginConfiguration.rabateCodeSettings.codeList.stream()
                .filter(rabateCode -> rabateCode.getCode().equalsIgnoreCase(code))
                .findFirst()
                .ifPresentOrElse(
                        rabateCode -> {
                            if (user.getRabateCode().contains(code)) {
                                MessageUtil.sendMessage(player, "&cUżyłeś/aś już tego kodu.");
                                return;
                            }

                            String command = rabateCode.getCommandToExeciute().replace("{PLAYER}", player.getName());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                            MessageUtil.sendMessage(player, "&aKod rabatowy został pomyślnie zastosowany!");
                            user.getRabateCode().add(code);
                        },
                        () -> MessageUtil.sendMessage(player, "&cNie znaleziono kodu")
                );
    }
}