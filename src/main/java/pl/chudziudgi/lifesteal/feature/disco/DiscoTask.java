package pl.chudziudgi.lifesteal.feature.disco;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

import java.util.Collection;
import java.util.Random;

public class DiscoTask extends BukkitRunnable {

    private final Random random;
    private final ClanService clanService;
    private final UserService userService;

    public DiscoTask(final SurvivalPlugin survivalPlugin ,Random random, ClanService clanService, UserService userService) {
        this.random = random;
        this.clanService = clanService;
        this.userService = userService;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 1);
    }


    @Override
    public void run() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        onlinePlayers.forEach(player -> {
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user == null || !player.hasPermission("cebulasmp.disco")) {
                return;
            }

            if (player.isSneaking()) {
                handleDiscoForPlayer(player, player, user);
            } else {
                DiscoPackethandler.refreshArmorPacket(player, player);
            }

            Clan playerClan = this.clanService.findClanByMember(player.getUniqueId());
            onlinePlayers.stream()
                    .filter(nearbyPlayer -> !nearbyPlayer.equals(player))
                    .filter(nearbyPlayer -> nearbyPlayer.getWorld().equals(player.getWorld()))
                    .filter(nearbyPlayer -> nearbyPlayer.getLocation().distance(player.getLocation()) <= 20)
                    .filter(nearbyPlayer -> {
                        Clan nearbyPlayerClan = this.clanService.findClanByMember(nearbyPlayer.getUniqueId());
                        return playerClan == null || !playerClan.equals(nearbyPlayerClan);
                    })
                    .forEach(nearbyPlayer -> handleDiscoForPlayer(player, nearbyPlayer, user));
        });
    }

    private void handleDiscoForPlayer(Player sourcePlayer, Player targetPlayer, User user) {
        switch (user.getDiscoType()) {
            case TURBO -> {
                DiscoPackethandler.sendArmorPacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(random));
            }
            case SMOOTH -> {
                int r = (int) (Math.sin(System.currentTimeMillis() / 1000.0) * 127 + 128);
                int g = (int) (Math.sin(System.currentTimeMillis() / 1000.0 + 2 * Math.PI / 3) * 127 + 128);
                int b = (int) (Math.sin(System.currentTimeMillis() / 1000.0 + 4 * Math.PI / 3) * 127 + 128);
                Color smoothColor = Color.fromRGB(r, g, b);
                DiscoPackethandler.sendArmorPacket(sourcePlayer, targetPlayer, smoothColor);
            }
            case DEVIL -> {
                long currentTime = System.currentTimeMillis();
                int red1 = (int) (Math.sin((currentTime) / 500.0) * 127 + 128);
                int red2 = (int) (Math.sin((currentTime + 500) / 500.0) * 127 + 128);
                int red3 = (int) (Math.sin((currentTime + 1000) / 500.0) * 127 + 128);
                int red4 = (int) (Math.sin((currentTime + 1500) / 500.0) * 127 + 128);
                DiscoPackethandler.sendHelmetPacket(sourcePlayer, targetPlayer, Color.fromRGB(red1, 0, 0));
                DiscoPackethandler.sendChestPlatePacket(sourcePlayer, targetPlayer, Color.fromRGB(red2, 0, 0));
                DiscoPackethandler.sendLeggingsPacket(sourcePlayer, targetPlayer, Color.fromRGB(red3, 0, 0));
                DiscoPackethandler.sendBootsPacket(sourcePlayer, targetPlayer, Color.fromRGB(red4, 0, 0));
            }
            case RANDOM -> {
                DiscoPackethandler.sendHelmetPacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(this.random));
                DiscoPackethandler.sendChestPlatePacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(this.random));
                DiscoPackethandler.sendLeggingsPacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(this.random));
                DiscoPackethandler.sendBootsPacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(this.random));
            }
        }
    }
}