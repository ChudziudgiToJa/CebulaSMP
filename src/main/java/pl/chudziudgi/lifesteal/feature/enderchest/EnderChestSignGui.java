package pl.chudziudgi.lifesteal.feature.enderchest;

import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import de.rapha149.signgui.SignGUIBuilder;
import de.rapha149.signgui.exception.SignGUIVersionException;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.List;

public class EnderChestSignGui {

    private final SurvivalPlugin survivalPlugin;

    public EnderChestSignGui(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }


    public void open(Player player, EnderChest enderChest) throws SignGUIVersionException {
        SignGUIBuilder builder = SignGUI.builder();

        builder.setLines("", "↖↗", "podaj nową nazwe");
        builder.setType(Material.OAK_SIGN);
        builder.setColor(DyeColor.RED);

        builder.setHandler((none, result) -> {
            String lineSearchIndex = result.getLineWithoutColor(0);

            if (lineSearchIndex == null) {
                return List.of(SignGUIAction.displayNewLines("", "↖↗", "musisz wpisać nazwe"));
            }

            if (lineSearchIndex.isEmpty()) {
                return List.of(SignGUIAction.displayNewLines("", "↖↗", "musisz wpisać nazwe"));
            }

            if (lineSearchIndex.length() > 8) {
                return List.of(SignGUIAction.displayNewLines("", "↖↗", "nazwa musi mieć od 1-8 znaków"));
            }

            if (!lineSearchIndex.matches("[a-zA-Z]+")) {
                return List.of(SignGUIAction.displayNewLines("", "↖↗", "tylko litery alfabetu"));
            }

            return List.of(SignGUIAction.runSync(this.survivalPlugin, () -> {
                enderChest.setName(lineSearchIndex);
                player.closeInventory();
                MessageUtil.sendTitle(player, "", "&aZmieniono nazwe na: &f" + lineSearchIndex, 20, 50, 20);
            }));
        });
        SignGUI gui = builder.build();
        gui.open(player);
    }

}
