package pl.chudziudgi.lifesteal.feature.lootcase;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import pl.chudziudgi.lifesteal.configuration.implementation.LootCaseConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import java.util.Optional;

public class LootCaseHandler {

    private final LootCaseConfiguration pluginConfiguration;
    private final HologramManager manager;

    public LootCaseHandler(LootCaseConfiguration pluginConfiguration, HologramManager manager) {
        this.pluginConfiguration = pluginConfiguration;
        this.manager = manager;
    }


    public void createLootCaseHolograms() {
        this.pluginConfiguration.lootCases.forEach(lootCase -> {
            Location location = lootCase.getLocation().clone().add(0.5, 2, 0.5);
            TextHologramData hologramData = new TextHologramData(lootCase.getName(), location);
            hologramData.setBillboard(Display.Billboard.CENTER);
            hologramData.removeLine(0);
            hologramData.addLine(MessageUtil.smallText(lootCase.getString()));
            hologramData.addLine(MessageUtil.smallText("&7klucze znajdziesz na &8(&7/itemshop&8)"));
            hologramData.addLine("");
            hologramData.addLine(MessageUtil.smallText("&aKliknij, aby otworzyć"));
            hologramData.setPersistent(false);

            Hologram hologram = manager.create(hologramData);
            manager.addHologram(hologram);
            createLootCaseKeyHolograms();
        });
    }

    public void createLootCaseKeyHolograms() {
        this.pluginConfiguration.lootCases.forEach(lootCase -> {
            Location location = lootCase.getLocation().clone().add(-0.5, 4.5, -0.5);
            ItemHologramData hologramData = new ItemHologramData(lootCase.getName() + "_key", location);
            hologramData.setItemStack(ItemStackSerializable.readItemStack(lootCase.getKeyItemStack()));
            hologramData.setBillboard(Display.Billboard.FIXED);
            hologramData.setPersistent(false);

            Hologram hologram = manager.create(hologramData);
            manager.addHologram(hologram);
        });
    }

    public void reloadLootCaseHolograms() {
        this.pluginConfiguration.lootCases.forEach(lootCase -> {
            Optional<Hologram> hologram = this.manager.getHologram(lootCase.getName());
            hologram.ifPresent(manager::removeHologram);
            createLootCaseHolograms();
        });
    }
}
