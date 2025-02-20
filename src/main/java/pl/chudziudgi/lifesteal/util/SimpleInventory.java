package pl.chudziudgi.lifesteal.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class SimpleInventory implements Listener {

    private final SurvivalPlugin survivalPlugin;

    private static final List<SimpleInventory> INVENTORIES = new ArrayList<>();
    @Getter
    private final Inventory inventory;
    private Consumer<InventoryClickEvent> clickEventConsumer;
    private Consumer<InventoryCloseEvent> closeEventConsumer;

    public SimpleInventory(SurvivalPlugin survivalPlugin, int slot, String title) {
        this.survivalPlugin = survivalPlugin;
        this.inventory = Bukkit.createInventory(null, slot, MessageUtil.smallText(title));
        register(survivalPlugin);

        INVENTORIES.add(this);
    }

    public SimpleInventory(SurvivalPlugin survivalPlugin, InventoryType inventoryType, String title) {
        this.survivalPlugin = survivalPlugin;
        this.inventory = Bukkit.createInventory(null, inventoryType, MessageUtil.smallText(title));
        register(survivalPlugin);

        INVENTORIES.add(this);
    }

    public static SimpleInventory findInventory(Inventory inventory) {
        return INVENTORIES.stream()
                .filter(helper -> helper.inventory.equals(inventory))
                .findFirst()
                .orElse(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if (!event.isCancelled()) {
            SimpleInventory inventory = findInventory(event.getInventory());
            if (inventory != null && inventory.clickEventConsumer != null) {
                inventory.clickEventConsumer.accept(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent event) {
        SimpleInventory inventory = findInventory(event.getInventory());
        if (inventory != null && inventory.closeEventConsumer != null) {
            inventory.closeEventConsumer.accept(event);
        }
    }

    public void click(Consumer<InventoryClickEvent> clickEventConsumer) {
        this.clickEventConsumer = clickEventConsumer;
    }

    public void close(Consumer<InventoryCloseEvent> closeEventConsumer) {
        this.closeEventConsumer = closeEventConsumer;
    }

    private void register(SurvivalPlugin survivalPlugin) {
        if (INVENTORIES.isEmpty()) {
            Bukkit.getPluginManager().registerEvents(this, survivalPlugin);
        }
    }
}