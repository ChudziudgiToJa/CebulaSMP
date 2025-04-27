package pl.chudziudgi.lifesteal.feature.user.controller;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.concurrent.CompletableFuture;

public class JoinQuitListener implements Listener {

    private final UserService userService;

    public JoinQuitListener(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(null);
        CompletableFuture.runAsync(() -> {
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user == null) {
                this.userService.createUser(new User(player));
            } else {
                if (!user.getNickName().equals(player.getName())) {
                    user.setNickName(player.getName());
                    player.kickPlayer(MessageUtil.smallText("&atwoje konto zostało odnowione."));
                    return;
                }
            }
        });
    }
}
