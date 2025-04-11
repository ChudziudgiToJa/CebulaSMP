package pl.chudziudgi.lifesteal.feature.end.world;

import com.eternalcode.core.EternalCoreApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;
import pl.chudziudgi.lifesteal.configuration.implementation.WorldsSettings;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.concurrent.CompletableFuture;

public class EndController implements Listener {

    private final WorldsSettings worldsSettings;
    private final EndManager endManager;
    private final EternalCoreApi eternalCoreApi;


    public EndController(WorldsSettings worldsSettings, EndManager endManager, EternalCoreApi eternalCoreApi) {
        this.worldsSettings = worldsSettings;
        this.endManager = endManager;
        this.eternalCoreApi = eternalCoreApi;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.worldsSettings.endJoinStatus) {
            endManager.addBossBar(event.getPlayer());
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.worldsSettings.endJoinStatus) {
            endManager.removeBossBar(event.getPlayer());
        }
    }

    @EventHandler
    public void onJoinPortalOnWorld(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.END_PORTAL) return;
        event.setCancelled(true);

        if (!this.worldsSettings.endJoinStatus) {
            MessageUtil.sendTitle(player, "", "&cEnd aktualnie jest wyłączony", 20, 50, 20);
            player.setVelocity(new Vector(0, 1, 0));
            return;
        }
        World normalWorld = Bukkit.getWorld("world_the_end");
        if (normalWorld == null) return;
        if (!player.getWorld().equals(normalWorld)) {
            if (this.worldsSettings.endSpawnLocation == null) {
                MessageUtil.sendMessage(player, "&cLokalizacja spawnu w Endzie nie jest ustawiona");
                return;
            }
            player.teleport(this.worldsSettings.endSpawnLocation);
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
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.ENDERMAN) {
            World world = event.getEntity().getWorld();
            if (world.getName().equalsIgnoreCase("world_the_end")) {
                event.getEntity().setAI(false);
            }
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equals("world_the_end")) return;

        if (player.hasPermission("cebulasmp.end.break")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equals("world_the_end")) return;

        if (player.hasPermission("cebulasmp.end.break")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equals("world_the_end")) return;

        if (player.hasPermission("cebulasmp.end.break")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreakBlock(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equals("world_the_end")) return;

        if (player.hasPermission("cebulasmp.end.break")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onSendCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase();
        if (player.hasPermission("cebulasmp.end.admin")) return;
        if (player.getWorld().getName().equals("world_the_end")) {
            if (this.worldsSettings.blockedCommandsOnEnd.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendTitle(player, "", "&ckomenda jest zablokowana w endzie.", 20, 50, 20);
            }
        }
    }
}
