package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

@Getter
@Setter
public class WorldsSettings extends OkaeriConfig {

    public boolean netherJoinStatus;
    public Location netherSpawnLocation = new Location(Bukkit.getWorld("world_nether"), 0 ,100 ,0);
    public List<String> blockedCommandsOnNether = List.of("spawn", "warp", "tpa", "tpaccept", "tpaccept", "home", "sethome");

    public boolean endJoinStatus;
    public Location endSpawnLocation = new Location(Bukkit.getWorld("world_the_end"), 0 ,100 ,0);
    public List<String> blockedCommandsOnEnd = List.of("tpaccept", "tpaccept", "home", "sethome");
}
