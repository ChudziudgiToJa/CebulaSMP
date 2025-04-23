package pl.chudziudgi.lifesteal.feature.casino;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;

public class CasinoInventory {
    private final SurvivalPlugin survivalPlugin;
    private final CasinoManager casinoManager;
    private final Integer[] glassBlueSlots = new Integer[]{1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49};
    private final int[] slotColumns = {12, 13, 14};
    private final int addMoneySlot = 50;
    private final int playSlot = 49;
    private final int removeMoneySlot = 48;
    private BukkitTask spinningTask;
    private int currentBet = 100;
    private final ItemStack spinItem = new ItemBuilder(Material.LEVER)
            .setName("&e&lSTART")
            .addLore("",
                    "&7Kwota: " + currentBet,
                    "",
                    "&7Kliknij aby rozpocząć grę!"
            )
            .build();
    private boolean isSpinning = false;

    public CasinoInventory(SurvivalPlugin survivalPlugin, CasinoManager casinoManager) {
        this.survivalPlugin = survivalPlugin;
        this.casinoManager = casinoManager;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fKasyno"));
        Inventory inventory = simpleInventory.getInventory();

        setupUI(inventory);

        simpleInventory.click(event -> {
            event.setCancelled(true);
            handleClick(event.getSlot(), player, inventory);
        });

        player.openInventory(inventory);
    }

    private void setupUI(Inventory inventory) {
        Arrays.stream(glassBlueSlots).forEach(slot ->
                inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()));

        inventory.setItem(addMoneySlot, new ItemBuilder(Material.ENCHANTED_BOOK)
                .setName("&a&l+ 100 monet")
                .build()
        );

        updateBetDisplay(inventory);

        inventory.setItem(removeMoneySlot, new ItemBuilder(Material.ENCHANTED_BOOK)
                .setName("&c&l- 100 monet")
                .build()
        );

        inventory.setItem(playSlot, spinItem);

        for (int column : slotColumns) {
            for (int row = 0; row < 3; row++) {
                inventory.setItem(column + (9 * row), new ItemBuilder(Material.AIR).build());
            }
        }
    }

    private void handleClick(int slot, Player player, Inventory inventory) {
        if (isSpinning) {
            return;
        }

        if (slot == addMoneySlot) {
            currentBet += 100;
            updateBetDisplay(inventory);
            return;
        }
        if (slot == removeMoneySlot) {
            if (!(currentBet > 100)) return;
            currentBet -= 100;
            updateBetDisplay(inventory);
            return;
        }
        if (slot == playSlot) {
            startSpinning(player, inventory);
        }
    }

    private void updateBetDisplay(Inventory inventory) {
        ItemStack updatedSpinItem = new ItemBuilder(Material.LEVER)
                .setName("&e&lSTART")
                .addLore("",
                        "&7Kwota: " + currentBet,
                        "",
                        "&7Kliknij aby rozpocząć grę!"
                )
                .build();
        inventory.setItem(playSlot, updatedSpinItem);
        spinItem.setItemMeta(updatedSpinItem.getItemMeta());
    }

    private void startSpinning(Player player, Inventory inventory) {
        Arrays.stream(glassBlueSlots).forEach(slot ->
                inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()));
        if (!casinoManager.canAfford(player, currentBet)) {
            MessageUtil.sendTitle(player, "", "&cNie masz wystarczająco monet!", 20, 50, 20);
            return;
        }
        casinoManager.processBet(player, currentBet);

        isSpinning = true;
        inventory.setItem(playSlot, new ItemBuilder(Material.BARRIER)
                .setName("&c&lTRWA LOSOWANIE...")
                .build()
        );

        final Material[] results = new Material[slotColumns.length];
        final int[] currentRows = new int[slotColumns.length];
        Arrays.fill(currentRows, 0);

        spinningTask = new BukkitRunnable() {
            final int[] stoppingCounters = new int[slotColumns.length];
            final boolean[] columnStopped = new boolean[slotColumns.length];
            int ticks = 0;

            {
                for (int i = 0; i < stoppingCounters.length; i++) {
                    stoppingCounters[i] = 20 + (i * 10);
                }
            }

            @Override
            public void run() {
                ticks++;
                for (int i = 0; i < slotColumns.length; i++) {
                    if (columnStopped[i]) continue;
                    for (int row = 3; row > 0; row--) {
                        ItemStack above = inventory.getItem(slotColumns[i] + (9 * (row - 1)));
                        inventory.setItem(slotColumns[i] + (9 * row), above != null ? above : new ItemStack(Material.AIR));
                    }
                    Material newMaterial = casinoManager.getRandomItem();
                    inventory.setItem(slotColumns[i], new ItemBuilder(newMaterial).setName(" ").build());
                    if (ticks >= stoppingCounters[i]) {
                        columnStopped[i] = true;
                        results[i] = newMaterial;
                    }
                }
                boolean allStopped = true;
                for (boolean stopped : columnStopped) {
                    if (!stopped) {
                        allStopped = false;
                        break;
                    }
                }

                if (allStopped) {
                    spinningTask.cancel();
                    spinningTask = null;
                    finishSpin(player, inventory, results);
                }
            }
        }.runTaskTimer(survivalPlugin, 0L, 2L);
    }

    private void finishSpin(Player player, Inventory inventory, Material[] results) {
        isSpinning = false;
        for (int i = 0; i < slotColumns.length; i++) {
            inventory.setItem(slotColumns[i] + (9 * 3), new ItemBuilder(results[i]).setName(" ").build());
            for (int row = 0; row < 3; row++) {
                inventory.setItem(slotColumns[i] + (9 * row), new ItemStack(Material.AIR));
            }
        }

        double multiplier = casinoManager.calculateMultiplier(results);
        int winnings = (int) (currentBet * multiplier);

        if (winnings > 0) {
            casinoManager.addWinnings(player, winnings);
            MessageUtil.sendTitle(player, "", "&aWygrałeś &e" + winnings + " &amonet! (x" + multiplier + ")", 20, 70, 20);
            Arrays.stream(glassBlueSlots).forEach(slot ->
                    inventory.setItem(slot, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(" ").build()));
        } else {
            Arrays.stream(glassBlueSlots).forEach(slot ->
                    inventory.setItem(slot, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(" ").build()));
        }
        inventory.setItem(playSlot, spinItem);
    }
}