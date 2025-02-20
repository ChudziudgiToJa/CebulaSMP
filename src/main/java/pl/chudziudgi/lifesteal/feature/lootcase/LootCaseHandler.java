package pl.chudziudgi.lifesteal.feature.lootcase;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Location;
import pl.chudziudgi.lifesteal.configuration.implementation.LootCaseConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LootCaseHandler {

    private final LootCaseConfiguration pluginConfiguration;

    public LootCaseHandler(LootCaseConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }


    public void createLootCaseHolograms() {
        List<String> description = new ArrayList<>();
        this.pluginConfiguration.lootCases.forEach(lootCase -> {
            description.add(MessageUtil.smallText(lootCase.getString()));
            description.add(MessageUtil.smallText("&akliknij aby otworzyć"));
            description.add("");
            DHAPI.createHologram(UUID.randomUUID().toString(), new Location(lootCase.getLocation().getWorld(), lootCase.getLocation().getX(), lootCase.getLocation().getY(), lootCase.getLocation().getZ()).add(0.500, 2.5,0.500), false, description);
            description.clear();
        });
    }
}
