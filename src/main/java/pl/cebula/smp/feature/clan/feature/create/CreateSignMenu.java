package pl.cebula.smp.feature.clan.feature.create;

import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import de.rapha149.signgui.SignGUIBuilder;
import de.rapha149.signgui.exception.SignGUIVersionException;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.user.User;

import java.util.List;

public class CreateSignMenu {

    private final SurvivalPlugin survivalPlugin;
    private final CreatePurchaseMenu createPurchaseMenu;

    public CreateSignMenu(SurvivalPlugin survivalPlugin, CreatePurchaseMenu createPurchaseMenu) {
        this.survivalPlugin = survivalPlugin;
        this.createPurchaseMenu = createPurchaseMenu;
    }

    public void open(Player player, User user) throws SignGUIVersionException {
        SignGUIBuilder builder = SignGUI.builder();

        builder.setLines("", "↖↗", "podaj tag klanu");
        builder.setType(Material.OAK_SIGN);
        builder.setColor(DyeColor.RED);

        builder.setHandler((none, result) -> {
            String lineSearchIndex = result.getLineWithoutColor(0);

            if (lineSearchIndex == null) {
                return List.of(SignGUIAction.displayNewLines("", "↖↗", "musisz wpisać tag klanu"));
            }

            if (lineSearchIndex.isBlank() && lineSearchIndex.isEmpty()) {
                return List.of(SignGUIAction.displayNewLines("", "↖↗", "musisz wpisać tag klanu"));
            }

            if (lineSearchIndex.length() < 2 || lineSearchIndex.length() > 5) {
                return List.of(SignGUIAction.displayNewLines("", "↖↗", "tag musi mieć od 2-5 znaków"));
            }

            if (!lineSearchIndex.matches("[a-zA-Z]+")) {
                return List.of(SignGUIAction.displayNewLines("", "↖↗", "tylko litery alfabetu"));
            }

            return List.of(SignGUIAction.runSync(this.survivalPlugin, () -> this.createPurchaseMenu.open(player, user, new Clan(player, lineSearchIndex))));
        });
        SignGUI gui = builder.build();
        gui.open(player);
    }

}
