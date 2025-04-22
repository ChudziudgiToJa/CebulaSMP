package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

import static org.bukkit.Material.*;

@Getter
@Setter
public class ClanConfiguration extends OkaeriConfig {

    public int clanPrice = 3500;

    public List<String> blockCommandListForClan = List.of("tpaaccept","tpaccept");
    public List<String> blockCommandList = List.of("tpaaccept","tpaccept", "sethome", "ec", "enderchest");

    public List<Material> blockerdInteracktMaterialOnOtherClan = List.of(
            CHEST, TRAPPED_CHEST, BARREL, FURNACE, BLAST_FURNACE, SMOKER,
            OAK_DOOR, BIRCH_DOOR, SPRUCE_DOOR, JUNGLE_DOOR, ACACIA_DOOR, DARK_OAK_DOOR,
            IRON_DOOR, LEVER, STONE_BUTTON, OAK_BUTTON, BIRCH_BUTTON, SPRUCE_BUTTON,
            ACACIA_BUTTON, JUNGLE_BUTTON, DARK_OAK_BUTTON, CRIMSON_BUTTON, WARPED_BUTTON,
            CRIMSON_DOOR, WARPED_DOOR, NOTE_BLOCK, COMPARATOR, REPEATER,
            SHULKER_BOX, WHITE_SHULKER_BOX, ORANGE_SHULKER_BOX, MAGENTA_SHULKER_BOX,
            LIGHT_BLUE_SHULKER_BOX, YELLOW_SHULKER_BOX, LIME_SHULKER_BOX, PINK_SHULKER_BOX,
            GRAY_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, CYAN_SHULKER_BOX, PURPLE_SHULKER_BOX,
            BLUE_SHULKER_BOX, BROWN_SHULKER_BOX, GREEN_SHULKER_BOX, RED_SHULKER_BOX, BLACK_SHULKER_BOX,
            ANVIL, DAMAGED_ANVIL, CHIPPED_ANVIL, HOPPER
    );

}

