package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
public class ClanConfiguration extends OkaeriConfig {

    public int clanPrice = 3500;

    public List<String> blockCommandListForClan = List.of("tpaaccept","tpaccept");
    public List<String> blockCommandList = List.of("tpaaccept","tpaccept", "sethome", "ec", "enderchest");

    public List<Material> blockBreakList = List.of(Material.OBSIDIAN, Material.CRYING_OBSIDIAN, Material.ENCHANTING_TABLE, Material.ANCIENT_DEBRIS, Material.NETHERITE_BLOCK, Material.RESPAWN_ANCHOR, Material.ENDER_CHEST, Material.WATER, Material.LAVA);
}

