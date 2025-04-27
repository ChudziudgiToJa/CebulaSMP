package pl.chudziudgi.lifesteal.feature.casino;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;
import java.util.Random;

public class CasinoInventory {
    private final SurvivalPlugin survivalPlugin;
    private final CasinoManager casinoManager;
    private final Integer[] glassBlueSlots = new Integer[]{10,16,1,3,5,7,9,17,27,35,47,51,2,4,6,18,26,36,44,46,48,50,52,0,8,45,53};
    private final int[] slotColumns = {11,12, 13, 14,15};
    private final int playSlot = 49;
    @Getter
    @Setter
    private BukkitTask spinningTask;
    private int currentBet = 100;
    private boolean isSpinning = false;

    private ItemStack spinItem = new ItemBuilder(Material.LEVER)
            .setName("&e&lSTART")
            .addLore("",
                    "&7Kwota: " + currentBet + " monet",
                    "",
                    "&2&llpm &7- &a&lgraj",
                    "&flpm + shift &7- &2+ 100 monet",
                    "&fppm + shift &7- &c- 100 monet"
            )
            .build();

    public CasinoInventory(SurvivalPlugin survivalPlugin, CasinoManager casinoManager) {
        this.survivalPlugin = survivalPlugin;
        this.casinoManager = casinoManager;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fKasyno"));
        Inventory inventory = simpleInventory.getInventory();

        Arrays.stream(glassBlueSlots).forEach(slot ->
                inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()));

        inventory.setItem(playSlot, spinItem);

        for (int column : slotColumns) {
            for (int row = 0; row < 4; row++) {
                inventory.setItem(column + (9 * row), new ItemStack(Material.AIR));
            }
        }

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (isSpinning) return;
            player.playSound(player, Sound.UI_BUTTON_CLICK, 1,1);

            if (event.getSlot() != playSlot) return;
            if (event.getClick() == ClickType.SHIFT_LEFT) {
                currentBet += 100;
                updateBetDisplay(inventory);
                return;
            }

            if (event.getClick() == ClickType.SHIFT_RIGHT) {
                if (currentBet > 100) {
                    currentBet -= 100;
                    updateBetDisplay(inventory);
                }
                return;
            }

            if (event.getClick() == ClickType.LEFT) {
                startSpinning(player, inventory);
            }
        });

        player.openInventory(inventory);
    }

    private void updateBetDisplay(Inventory inventory) {
        ItemStack updatedSpinItem = new ItemBuilder(Material.LEVER)
                .setName("&e&lSTART")
                .addLore("",
                        "&7Kwota: " + currentBet + " monet",
                        "",
                        "&2&llpm &7- &a&lgraj",
                        "&flpm + shift &7- &2+ 100 monet",
                        "&fppm + shift &7- &c- 100 monet"
                )
                .build();
        inventory.setItem(playSlot, updatedSpinItem);
    }


    private void startSpinning(Player player, Inventory inventory) {
        if (!casinoManager.canAfford(player, currentBet)) {
            MessageUtil.sendTitle(player, "", "&cNie masz wystarczająco monet!", 20, 50, 20);
            return;
        }

        Arrays.stream(glassBlueSlots).forEach(slot ->
                inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()));

        casinoManager.processBet(player, currentBet);
        isSpinning = true;

        inventory.setItem(playSlot, new ItemBuilder(Material.BARRIER)
                .setName("&c&lTRWA LOSOWANIE...")
                .build());

        final Material[] results = new Material[slotColumns.length];

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
                if (ticks % 2 == 0) {
                    int randomIndex = new Random().nextInt(glassBlueSlots.length);
                    int slot = glassBlueSlots[randomIndex];
                    inventory.setItem(slot, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").build());
                }

                for (int i = 0; i < slotColumns.length; i++) {
                    if (columnStopped[i]) continue;

                    // przesuwanie w dół
                    for (int row = 3; row > 0; row--) {
                        ItemStack above = inventory.getItem(slotColumns[i] + (9 * (row - 1)));
                        inventory.setItem(slotColumns[i] + (9 * row), above != null ? above.clone() : new ItemStack(Material.AIR));
                    }

                    Material newMaterial = casinoManager.getRandomItem();
                    inventory.setItem(slotColumns[i], new ItemBuilder(newMaterial).setName("&fx" + casinoManager.getMultipliers().get(newMaterial)).build());
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 1,1);

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
                    cancel();
                    finishSpin(player, inventory, results);
                    spinningTask = null;
                }
            }
        }.runTaskTimer(survivalPlugin, 0L, 2L);
    }

    private void finishSpin(Player player, Inventory inventory, Material[] results) {
        isSpinning = false;

        double multiplier = casinoManager.calculateMultiplier(results);
        boolean diagonalWin = isDiagonalMatch(inventory);

        if (diagonalWin) {
            multiplier = Math.max(multiplier, 2.5);
        }

        int winnings = (int) (currentBet * multiplier);

        if (winnings > 0) {
            casinoManager.addWinnings(player, winnings);
            inventory.setItem(4, new ItemBuilder(Material.GOLD_BLOCK).setName("&awygrana: &f" + winnings + " monet").build());
            Bukkit.getOnlinePlayers().forEach(o -> {
                MessageUtil.sendMessage(o, player.getName() + " &awydropił/a z &8(&7/kasyno&8) &a" + winnings + " monet");
            });
            player.playSound(player, Sound.ENTITY_VILLAGER_YES, 1,1);
        } else {
            Arrays.stream(glassBlueSlots).forEach(slot ->
                    inventory.setItem(slot, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(" ").build()));
            player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1,1);
        }

        updateBetDisplay(inventory);
    }

    private boolean isDiagonalMatch(Inventory inventory) {
        // skos lewo-góra -> prawo-dół (środek)
        Material a = getMaterial(inventory, 12);
        Material b = getMaterial(inventory, 13 + 9);
        Material c = getMaterial(inventory, 14 + 9 * 2);
        if (a != null && a == b && a == c) return true;

        // skos prawo-góra -> lewo-dół (środek)
        a = getMaterial(inventory, 14);
        b = getMaterial(inventory, 13 + 9);
        c = getMaterial(inventory, 12 + 9 * 2);
        if (a != null && a == b && a == c) return true;

        // skos lewo-góra -> prawo-dół (skrajnie lewa kolumna)
        a = getMaterial(inventory, 11);
        b = getMaterial(inventory, 12 + 9);
        c = getMaterial(inventory, 13 + 9 * 2);
        if (a != null && a == b && a == c) return true;

        // skos prawo-góra -> lewo-dół (skrajnie prawa kolumna)
        a = getMaterial(inventory, 15);
        b = getMaterial(inventory, 14 + 9);
        c = getMaterial(inventory, 13 + 9 * 2);
        return a != null && a == b && a == c;
    }


    private Material getMaterial(Inventory inventory, int slot) {
        ItemStack item = inventory.getItem(slot);
        return item != null && item.getType() != Material.AIR ? item.getType() : null;
    }
}
