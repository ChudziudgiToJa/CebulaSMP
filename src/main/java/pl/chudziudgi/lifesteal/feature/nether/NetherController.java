package pl.chudziudgi.lifesteal.feature.nether;

import com.eternalcode.core.EternalCoreApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import pl.chudziudgi.lifesteal.configuration.implementation.WorldsSettings;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.concurrent.CompletableFuture;

public class NetherController implements Listener {

    private final WorldsSettings worldsSettings;
    private final NetherManager netherManager;
    private final EternalCoreApi eternalCoreApi;


    public NetherController(WorldsSettings worldsSettings, NetherManager netherManager, EternalCoreApi eternalCoreApi) {
        this.worldsSettings = worldsSettings;
        this.netherManager = netherManager;
        this.eternalCoreApi = eternalCoreApi;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.worldsSettings.netherJoinStatus) {
            netherManager.addBossBar(event.getPlayer());
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.worldsSettings.netherJoinStatus) {
            netherManager.removeBossBar(event.getPlayer());
        }
    }

    @EventHandler
    public void onJoinPortalOnWorld(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if (!event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) return;
        event.setCancelled(true);

        if (!this.worldsSettings.netherJoinStatus) {
            MessageUtil.sendTitle(player, "", "&cNether aktualnie jest wyłączony", 20, 50, 20);
            return;
        }

        World normalWorld = Bukkit.getWorld("world_nether");
        if (normalWorld == null) return;
        if (!player.getWorld().equals(normalWorld)) {
            CompletableFuture<Location> randomLocationFuture = this.eternalCoreApi.getRandomTeleportService().getSafeRandomLocationInWorldBorder(normalWorld, 30);

            randomLocationFuture.thenAccept(randomLocation -> {
                if (randomLocation != null) {
                    player.teleport(randomLocation);
                    MessageUtil.sendTitle(player, "", "&aZostałeś przeteleportowany w losowe miejsce!", 20, 50, 20);
                } else {
                    MessageUtil.sendTitle(player, "", "&cNie udało się znaleźć bezpiecznej lokalizacji do teleportacji.", 20, 50, 20);
                }
            });
        } else {
            CompletableFuture<Location> randomLocationFuture = this.eternalCoreApi.getRandomTeleportService().getSafeRandomLocationInWorldBorder(Bukkit.getWorlds().getFirst(), 30);

            randomLocationFuture.thenAccept(randomLocation -> {
                if (randomLocation != null) {
                    player.teleport(randomLocation);
                    MessageUtil.sendTitle(player, "", "&aZostałeś przeteleportowany w losowe miejsce!", 20, 50, 20);
                } else {
                    MessageUtil.sendTitle(player, "", "&cNie udało się znaleźć bezpiecznej lokalizacji do teleportacji.", 20, 50, 20);
                }
            });
        }
    }

    @EventHandler
    public void breakSpawnerBlock(BlockBreakEvent event) {
        if (event.getBlock().getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (event.getBlock().getType().equals(Material.SPAWNER)) {
            MessageUtil.sendMessage(event.getPlayer(), "&cNiszczenie spawnerów jest zakazane.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceWitcherHead(BlockPlaceEvent event) {
        if (event.getBlock().getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (event.getBlock().getType().equals(Material.WITHER_SKELETON_SKULL)) {
            event.setCancelled(true);
            MessageUtil.sendMessage(event.getPlayer(), "&cStawianie tego przedmiotu jest tylko możliwe w netherze.");
        }
    }
}
