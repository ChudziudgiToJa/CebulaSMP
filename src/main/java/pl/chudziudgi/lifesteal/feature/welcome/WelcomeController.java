package pl.chudziudgi.lifesteal.feature.welcome;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WelcomeController implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final UserService userService;

    private final Set<UUID> newPlayers = ConcurrentHashMap.newKeySet();
    private final Map<UUID, Long> welcomeTime = new ConcurrentHashMap<>();
    private final Map<UUID, Set<UUID>> alreadyWelcomed = new ConcurrentHashMap<>();

    public WelcomeController(PluginConfiguration pluginConfiguration, UserService userService) {
        this.pluginConfiguration = pluginConfiguration;
        this.userService = userService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            UUID uuid = player.getUniqueId();
            newPlayers.add(uuid);
            welcomeTime.put(uuid, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        String rawMessage = ChatColor.stripColor(event.getMessage()).toLowerCase().trim();

        for (UUID newPlayerUUID : newPlayers) {
            Player newPlayer = Bukkit.getPlayer(newPlayerUUID);
            if (newPlayer == null || !newPlayer.isOnline()) continue;

            String targetName = newPlayer.getName().toLowerCase();
            long joinedAt = welcomeTime.getOrDefault(newPlayerUUID, 0L);
            long now = System.currentTimeMillis();

            if (now - joinedAt > 2 * 60 * 1000) continue;

            if (isWelcomeMessage(rawMessage, targetName)) {
                UUID senderUUID = sender.getUniqueId();
                Set<UUID> welcomed = alreadyWelcomed.computeIfAbsent(senderUUID, k -> new HashSet<>());

                if (welcomed.contains(newPlayerUUID)) return;

                welcomed.add(newPlayerUUID);
                User user = this.userService.findUserByUUID(senderUUID);
                if (user == null) return;

                double prize = pluginConfiguration.welcomeSettings.prizeFromWelcomeNewPlayer;
                user.addMoney(prize);

                MessageUtil.sendTitle(sender, "", "&aOtrzymałeś " + prize + " monet za przywitanie nowego gracza", 20, 50, 20);

                return;
            }
        }
    }

    private boolean isWelcomeMessage(String message, String targetName) {
        return pluginConfiguration.welcomeSettings.welcomeMessagesList.stream()
                .map(s -> s.replace("{PLAYER}", targetName).toLowerCase())
                .anyMatch(expected -> expected.equals(message));
    }
}
