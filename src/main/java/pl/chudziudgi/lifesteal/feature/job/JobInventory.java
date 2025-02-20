package pl.chudziudgi.lifesteal.feature.job;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JobInventory {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;

    public JobInventory(SurvivalPlugin survivalPlugin, UserService userService, PluginConfiguration pluginConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fprace:"));
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByNickName(player.getName());

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.addItem(itemStack(user, JobType.KILLER, Material.NETHERITE_SWORD));
        inventory.addItem(itemStack(user, JobType.LUMBERJACK, Material.STONE_AXE));
        inventory.addItem(itemStack(user, JobType.FISHER, Material.FISHING_ROD));
        inventory.addItem(itemStack(user, JobType.MINER, Material.DIAMOND_PICKAXE));
        inventory.setItem(49, itemStack(user, JobType.CLEAR, Material.BARRIER));

        simpleInventory.click(event -> {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta() || !clickedItem.getItemMeta().hasDisplayName()) {
                return;
            }

            String displayName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            JobType selectedJob = JobType.CLEAR;

            for (JobType jobType : JobType.values()) {
                String jobTypeScs = MessageUtil.smallText(jobType.getPolishName());
                if (jobTypeScs.equals(displayName)) {
                    selectedJob = jobType;
                    break;
                }
            }

            if (event.getClick().equals(ClickType.LEFT)) {
                if (user.getJobType().equals(selectedJob)) {
                    return;
                }
                if (selectedJob.equals(JobType.CLEAR)) {
                    user.setJobType(JobType.CLEAR);
                    MessageUtil.sendTitle(player, "", "&aTwój zawód został usunięty.", 20, 50, 20);
                } else {
                    if (user.getJobCoolDown() > System.currentTimeMillis()) {
                        MessageUtil.sendTitle(player, "", "&cmożesz zmienić prace za: " + DurationUtil.getTimeFormat(user.getJobCoolDown() - System.currentTimeMillis()), 20, 50, 20);
                        player.closeInventory();
                        player.playSound(player, Sound.ENTITY_BEE_HURT, 5, 5);
                        return;
                    }
                    user.setJobCoolDown(21600000 + System.currentTimeMillis());
                    user.setJobType(selectedJob);
                    MessageUtil.sendTitle(player, "", "&aTwój nowy zawód to: &f" + selectedJob.getPolishName() + "&a.", 20, 50, 20);
                }
                player.closeInventory();
                return;
            }

            if (event.getClick().equals(ClickType.RIGHT)) {
                if (selectedJob.equals(JobType.CLEAR)) return;
                showDrop(player, selectedJob);
            }
        });

        player.openInventory(inventory);
    }

    public void showDrop(final Player player, JobType jobType) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallText("&fDrop z pracy: " + jobType.getPolishName()));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));


        for (JobDropChance jobDropChance : this.pluginConfiguration.jobSettings.jobItems.get(jobType)) {
            ItemStack jobDropChanceItemStack = ItemStackSerializable.readItemStack(jobDropChance.getItemStackString());
            if (jobDropChanceItemStack == null) {
                return;
            }

            ItemMeta itemMeta = jobDropChanceItemStack.getItemMeta();
            if (itemMeta == null) {
                return;
            }

            List<String> lore = itemMeta.hasLore() ? new ArrayList<>(Objects.requireNonNull(itemMeta.getLore())) : new ArrayList<>();
            lore.add("");
            lore.add(MessageUtil.smallText("&7szansa na drop: &a" + jobDropChance.getChance()));
            itemMeta.setLore(lore);
            jobDropChanceItemStack.setItemMeta(itemMeta);
            inventory.addItem(jobDropChanceItemStack);
        }

        inventory.setItem(49, new ItemBuilder(Material.BARRIER)
                .setName("&4cofnij")
                .build()
        );

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;

            if (event.getSlot() == 49) {
                this.show(player);
            }
        });

        player.openInventory(inventory);
    }


    public ItemStack itemStack(User user, JobType jobType, Material material) {
        ItemBuilder itemBuilder = new ItemBuilder(material)
                .setName("&f&l" + jobType.getPolishName());

        if (user.getJobType().equals(jobType)) {
            if (jobType.equals(JobType.CLEAR)) {
                itemBuilder.setLore(
                        "",
                        "&9LPM &8- &cPosiadasz już ten zawód"
                );
            } else {
                for (String string : jobType.getDescriptionStringList()) {
                    itemBuilder.addLore(string);
                }
                itemBuilder.addLore(
                        "",
                        "&9LPM &8- &cPosiadasz już ten zawód",
                        "&6PPM &8- &fLista dropu z pracy."
                );
            }
        } else {
            if (jobType.equals(JobType.CLEAR)) {
                itemBuilder.setLore(
                        "",
                        "&9LPM &8- &aKliknij aby zatrudnić się"
                );
            } else {
                for (String string : jobType.getDescriptionStringList()) {
                    itemBuilder.addLore(string);
                }
                itemBuilder.addLore(
                        "",
                        "&9LPM &8- &aKliknij aby zatrudnić się",
                        "&6PPM &8- &fLista dropu z pracy."
                );
            }
        }
        return itemBuilder.build();
    }
}

