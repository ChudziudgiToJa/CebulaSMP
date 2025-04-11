package pl.chudziudgi.lifesteal.feature.end.world;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.configuration.implementation.WorldsSettings;
import pl.chudziudgi.lifesteal.util.MessageUtil;


@Command(name = "end")
@Permission("cebulasmp.end.admin")
public class EndCommand {

    private final EndManager endManager;
    private final WorldsSettings worldsSettings;

    public EndCommand(EndManager endManager, WorldsSettings worldsSettings) {
        this.endManager = endManager;
        this.worldsSettings = worldsSettings;
    }

    @Execute(name = "status")
    @Permission("cebulasmp.end.admin")
    void toggleNetherStatus(@Context CommandSender player, @Arg boolean b) {
        this.worldsSettings.endJoinStatus = b;
        this.worldsSettings.save();
        this.endManager.toggleNetherBossBar();
        MessageUtil.sendMessage(player, "&fUstawiono end na: " + (b ? "&awłączony" : "&cwyłączony"));
    }

    @Execute(name = "tp")
    @Permission("cebulasmp.end.admin")
    void tpNether(@Context Player player, @OptionalArg Player target) {
        if (this.worldsSettings.netherSpawnLocation == null) {
            MessageUtil.sendMessage(player, "&cLokalizacja spawnu w nether nie jest ustawiona");
            return;
        }
        Location netherSpawnLocation = this.worldsSettings.getEndSpawnLocation();
        Player toTeleport = target != null ? target : player;
        toTeleport.teleport(netherSpawnLocation);
        MessageUtil.sendMessage(toTeleport, "Zostałeś teleportowany do spawn end!");
        if (target != null && !target.equals(player)) {
            MessageUtil.sendMessage(player, "Teleportowano gracza " + target.getName() + " do spawn end.");
        }
    }

    @Execute(name = "setspawn")
    @Permission("cebulasmp.end.admin")
    void setNetherSpawn(@Context Player player) {
        if (!player.getWorld().getName().equalsIgnoreCase("world_the_end")) {
            MessageUtil.sendMessage(player, "&cMusisz być w świecie end, aby ustawić spawn end!");
            return;
        }
        this.worldsSettings.setEndSpawnLocation(player.getLocation());
        this.worldsSettings.save();
        MessageUtil.sendMessage(player, "&aSpawn end został ustawiony na twoją aktualną lokalizację!");
    }
}