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
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CasinoInventory {
    private final SurvivalPlugin survivalPlugin;
    private final CasinoManager casinoManager;
    private final Integer[] glassBlueSlots = new Integer[]{10,16,1,3,5,7,9,17,27,35,47,51,2,4,6,18,26,36,44,46,48,50,52,0,8,45,53};
    private final int[] slotColumns = {11,12,13,14,15};
    private final int playSlot = 49;
    @Getter @Setter
    private BukkitTask spinningTask;
    private int currentBet = 100;
    private boolean isSpinning = false;
    private final List<Material> spinItems = Arrays.asList(
            Material.DIAMOND,
            Material.EMERALD,
            Material.GOLD_INGOT,
            Material.IRON_INGOT,
            Material.REDSTONE,
            Material.LAPIS_LAZULI,
            Material.COAL
    );
    private final Random random = ThreadLocalRandom.current();

    private final ItemStack spinItem = new ItemBuilder(Material.LEVER)
            .setName("&e&lSTART")
            .addLore("",
                    "&7Kwota: " + currentBet + " monet",
                    "",
                    "&2&llpm &7- &a&lgraj",
                    "&flpm + shift &7- &2+ 100 monet",
                    "&fppm + shift &7- &c- 100 monet",
                    "&fdrop &7- &2+ 1000 monet",
                    "&fcontrol + drop &7- &c- 1000 monet"
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
                Material randomMat = spinItems.get(random.nextInt(spinItems.size()));
                inventory.setItem(column + (9 * row), new ItemBuilder(randomMat)
                        .setName("&fx" + casinoManager.getMultipliers().get(randomMat))
                        .build());
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

            if (event.getClick() == ClickType.CONTROL_DROP) {
                if (currentBet > 1000) {
                    currentBet -= 1000;
                    updateBetDisplay(inventory);
                }
                return;
            }

            if (event.getClick() == ClickType.DROP) {
                currentBet += 100;
                updateBetDisplay(inventory);
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

        casinoManager.processBet(player, currentBet);
        isSpinning = true;

        inventory.setItem(playSlot, new ItemBuilder(Material.BARRIER)
                .setName("&c&lTRWA LOSOWANIE...")
                .build());

        final Material[] results = new Material[slotColumns.length];

        spinningTask = new BukkitRunnable() {
            final int[] stoppingCounters = new int[slotColumns.length];
            final boolean[] columnStopped = new boolean[slotColumns.length];
            final int[] spinSpeeds = new int[slotColumns.length];
            int ticks = 0;

            {
                for (int i = 0; i < stoppingCounters.length; i++) {
                    stoppingCounters[i] = 20 + (i * 15) + random.nextInt(10);
                    spinSpeeds[i] = 2 + random.nextInt(3);
                }
            }

            @Override
            public void run() {
                ticks++;

                // Random flashing effect
                if (ticks % 5 == 0) {
                    int randomSlot = glassBlueSlots[random.nextInt(glassBlueSlots.length)];
                    inventory.setItem(randomSlot, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").build());
                    Bukkit.getScheduler().runTaskLater(survivalPlugin, () -> {
                        if (inventory.getItem(randomSlot) != null) {
                            inventory.setItem(randomSlot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
                        }
                    }, 5L);
                }

                for (int i = 0; i < slotColumns.length; i++) {
                    if (columnStopped[i]) continue;

                    if (ticks % spinSpeeds[i] != 0) continue;

                    for (int row = 3; row > 0; row--) {
                        ItemStack above = inventory.getItem(slotColumns[i] + (9 * (row - 1)));
                        inventory.setItem(slotColumns[i] + (9 * row), above != null ? above.clone() : new ItemStack(Material.AIR));
                    }

                    Material newMaterial = casinoManager.getRandomItem();
                    inventory.setItem(slotColumns[i], new ItemBuilder(newMaterial)
                            .setName("&fx" + casinoManager.getMultipliers().get(newMaterial))
                            .build());

                    float pitch = 0.5f + (1.0f / spinSpeeds[i]);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 0.5f, pitch);

                    if (ticks >= stoppingCounters[i]) {
                        columnStopped[i] = true;
                        results[i] = newMaterial;
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);
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
        }.runTaskTimer(survivalPlugin, 0L, 1L);
    }

    private void finishSpin(Player player, Inventory inventory, Material[] results) {
        isSpinning = false;

        double multiplier = casinoManager.calculateMultiplier(results);
        boolean diagonalWin = isDiagonalMatch(inventory);
        boolean horizontalWin = isHorizontalMatch(inventory);

        if (diagonalWin) {
            multiplier = Math.max(multiplier * 1.5, 2.5);
        }
        if (horizontalWin) {
            multiplier = Math.max(multiplier * 1.2, 1.5);
        }

        int winnings = (int) (currentBet * multiplier);

        if (winnings > 0) {
            casinoManager.addWinnings(player, winnings);
            inventory.setItem(4, new ItemBuilder(Material.GOLD_BLOCK)
                    .setName("&a&lWYGRANA: &f" + winnings + " monet")
                    .addLore("&7Mnożnik: &fx" + String.format("%.1f", multiplier))
                    .build());

            double finalMultiplier = multiplier;
            new BukkitRunnable() {
                int flashes = 0;
                @Override
                public void run() {
                    if (flashes++ >= 6) {
                        cancel();
                        return;
                    }

                    Material flashMat = flashes % 2 == 0 ? Material.GOLD_BLOCK : Material.EMERALD_BLOCK;
                    inventory.setItem(4, new ItemBuilder(flashMat)
                            .setName("&a&lWYGRANA: &f" + winnings + " monet")
                            .addLore("&7Mnożnik: &fx" + String.format("%.1f", finalMultiplier))
                            .build());

                    player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0f, 1.0f);
                }
            }.runTaskTimer(survivalPlugin, 0L, 2L);

            if (multiplier >= 5.0) {
                Bukkit.getOnlinePlayers().forEach(o -> {
                    MessageUtil.sendMessage(o, "&6&lJACKPOT! &e" + player.getName() + " &6wygrywa &e" + winnings + " monet &8(&7/kasyno&8)");
                });
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            } else {
                Bukkit.getOnlinePlayers().forEach(o -> {
                    MessageUtil.sendMessage(o, player.getName() + " &awygrywa &2" + winnings + " monet &8(&7/kasyno&8)");
                });
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
            }
        } else {
            Arrays.stream(glassBlueSlots).forEach(slot ->
                    inventory.setItem(slot, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(" ").build()));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);

            Bukkit.getScheduler().runTaskLater(survivalPlugin, () -> {
                if (!isSpinning) {
                    Arrays.stream(glassBlueSlots).forEach(slot ->
                            inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()));
                }
            }, 20L);
        }

        updateBetDisplay(inventory);
    }

    private boolean isDiagonalMatch(Inventory inventory) {
        return checkDiagonal(inventory, 11, 12, 13) || // Left to right top
                checkDiagonal(inventory, 12, 13, 14) || // Middle to right
                checkDiagonal(inventory, 14, 13, 12) || // Right to left
                checkDiagonal(inventory, 11+9, 12+9, 13+9) || // Middle row
                checkDiagonal(inventory, 11+18, 12+18, 13+18); // Bottom row
    }

    private boolean isHorizontalMatch(Inventory inventory) {
        for (int row = 0; row < 3; row++) {
            Material first = getMaterial(inventory, 11 + (9 * row));
            if (first == null) continue;

            int matches = 1;
            for (int col = 1; col < 5; col++) {
                Material current = getMaterial(inventory, 11 + col + (9 * row));
                if (current != null && current == first) {
                    matches++;
                    if (matches >= 3) return true;
                } else {
                    first = current;
                    matches = 1;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal(Inventory inventory, int slot1, int slot2, int slot3) {
        Material a = getMaterial(inventory, slot1);
        Material b = getMaterial(inventory, slot2);
        Material c = getMaterial(inventory, slot3);
        return a != null && a == b && a == c;
    }

    private Material getMaterial(Inventory inventory, int slot) {
        ItemStack item = inventory.getItem(slot);
        return item != null && item.getType() != Material.AIR ? item.getType() : null;
    }
}