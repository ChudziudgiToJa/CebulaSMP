package pl.chudziudgi.lifesteal.feature.nether;

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


@Command(name = "nether")
@Permission("cebulasmp.nether.admin")
public class NetherCommand {

    private final NetherManager netherManager;
    private final WorldsSettings worldsSettings;

    public NetherCommand(NetherManager netherManager, WorldsSettings worldsSettings) {
        this.netherManager = netherManager;
        this.worldsSettings = worldsSettings;
    }

    @Execute(name = "status")
    @Permission("cebulasmp.nether.admin")
    void toggleNetherStatus(@Context CommandSender player, @Arg boolean b) {
        this.worldsSettings.netherJoinStatus = b;
        this.worldsSettings.save();
        this.netherManager.toggleNetherBossBar();
        MessageUtil.sendMessage(player, "&fUstawiono nether na: " + (b ? "&awłączony" : "&cwyłączony"));
    }

    @Execute(name = "tp")
    @Permission("cebulasmp.nether.admin")
    void tpNether(@Context Player player, @OptionalArg Player target) {
        if (this.worldsSettings.netherSpawnLocation == null) {
            MessageUtil.sendMessage(player, "&cLokalizacja spawnu w nether nie jest ustawiona");
            return;
        }
        Location netherSpawnLocation = this.worldsSettings.getNetherSpawnLocation();
        Player toTeleport = target != null ? target : player;
        toTeleport.teleport(netherSpawnLocation);
        MessageUtil.sendMessage(toTeleport, "Zostałeś teleportowany do spawn Nether!");
        if (target != null && !target.equals(player)) {
            MessageUtil.sendMessage(player, "Teleportowano gracza " + target.getName() + " do spawn Nether.");
        }
    }

    @Execute(name = "setspawn nether")
    @Permission("cebulasmp.nether.admin")
    void setNetherSpawn(@Context Player player) {
        if (!player.getWorld().getName().equalsIgnoreCase("world_nether")) {
            MessageUtil.sendMessage(player, "&cMusisz być w świecie Nether, aby ustawić spawn Nether!");
            return;
        }
        this.worldsSettings.setNetherSpawnLocation(player.getLocation());
        this.worldsSettings.save();
        MessageUtil.sendMessage(player, "&aSpawn Nether został ustawiony na twoją aktualną lokalizację!");
    }

    @Execute(name = "setspawn")
    @Permission("cebulasmp.end.admin")
    void setBackSpawn(@Context Player player) {
        this.worldsSettings.setBackSpawnLocation(player.getLocation());
        this.worldsSettings.save();
        MessageUtil.sendMessage(player, "&aLokalizacja spawna, ustawiona pomyślnie!");
    }
}