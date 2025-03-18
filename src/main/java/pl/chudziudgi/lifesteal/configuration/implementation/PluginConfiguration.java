package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.feature.job.JobDropChance;
import pl.chudziudgi.lifesteal.feature.job.JobType;
import pl.chudziudgi.lifesteal.feature.livesteal.LifeStealCommand;
import pl.chudziudgi.lifesteal.feature.rabatecode.RabateCode;
import pl.chudziudgi.lifesteal.feature.shop.time.TimeShop;
import pl.chudziudgi.lifesteal.util.ItemBuilder;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PluginConfiguration extends OkaeriConfig {


    public BlockerSettings BlockerSettings = new BlockerSettings();
    public RandomTeleportSettings randomTeleportSettings = new RandomTeleportSettings();
    public JobSettings jobSettings = new JobSettings();
    public LifeStealSettings lifeStealSettings = new LifeStealSettings();
    public TimeShopSettings timeShopSettings = new TimeShopSettings();
    public RabateCodeSettings rabateCodeSettings = new RabateCodeSettings();
    public CheckSettings checkSettings = new CheckSettings();


    public int freePlnNpcID = 16;
    public int blackSmithID = 31;
    public String discordUrl = "https://dc.cebulasmp.pl/";

    public static class CheckSettings extends OkaeriConfig {
        public String commandToExeciute = "tempban {PLAYER} logaut podczas sprawdzania 3d";
        public Location jailLocation;
    }

    public static class RabateCodeSettings extends OkaeriConfig {
        public List<RabateCode> codeList = List.of(
                new RabateCode(
                 "tiktok",
                 "lp user {PLAYER} parent addtemp vip 6h"
                )
        );
    }

    public static class TimeShopSettings extends OkaeriConfig {
        public List<TimeShop> timeShops = List.of(
                new TimeShop(
                        new ItemBuilder(Material.PAPER)
                                .setName("&7Ranga: &dVIP 1 tyg")
                                .addLore("")
                                .addLore("&8| &7koszt&8: &f10m")
                                .addLore("")
                                .addLore("&bppm &f- &aaby kupić.")
                                .build(),
                        600,
                        "lp user {PLAYER} parent addtemp vip 1w "
                ),
                new TimeShop(
                        new ItemBuilder(Material.PAPER)
                                .setName("&7Ranga: &dCEBULAK 1m (test)")
                                .addLore("")
                                .addLore("&8| &7koszt&8: &f60s")
                                .addLore("")
                                .addLore("&bppm &f- &aaby kupić.")
                                .build(),
                        60,
                        "lp user {PLAYER} parent addtemp cebulak 1m "
                )
        );
    }

    public static class LifeStealSettings extends  OkaeriConfig {
        public ItemStack heartItemStack = new ItemBuilder(Material.RED_DYE)
                .setName("&4połówka serca")
                .setLore("","&akliknij aby dodać")
                .build();

        public List<String> commandList = List.of(
                "",
                ""
        );
    }

    public static class BlockerSettings extends OkaeriConfig {
        @Comment("Lista zablokowanych przedmiotów do craftingów  interackjci")
        public List<Material> materials = List.of(
                Material.WRITABLE_BOOK,
                Material.ARMOR_STAND,
                Material.TNT_MINECART,
                Material.ARMOR_STAND
        );
    }



    public static class RandomTeleportSettings extends OkaeriConfig {
        public ArrayList<Location> buttonsLocations = new ArrayList<>();
        public List<Material> allowButtonsToSetRtp = List.of(Material.STONE_BUTTON);
    }

    public static class JobSettings extends OkaeriConfig {
        public Map<JobType, List<JobDropChance>> jobItems = new EnumMap<>(JobType.class);

        public JobSettings() {
            if (jobItems.isEmpty()) {
                for (JobType jobType : JobType.values()) {
                    jobItems.put(jobType, new ArrayList<>());
                }
            }
        }

        public void addItem(JobType jobType, final JobDropChance item) {
            if (item == null) {
                return;
            }
            jobItems.get(jobType).add(item);
        }

        public void removeItem(JobType jobType, int i) {
            jobItems.get(jobType).remove(i + 1);
        }
    }
}
