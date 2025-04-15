package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.feature.job.JobDropChance;
import pl.chudziudgi.lifesteal.feature.job.JobType;
import pl.chudziudgi.lifesteal.feature.rabatecode.RabateCode;
import pl.chudziudgi.lifesteal.feature.shop.time.TimeShop;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PluginConfiguration extends OkaeriConfig {


    public BlockerSettings BlockerSettings = new BlockerSettings();
    public RandomTeleportSettings randomTeleportSettings = new RandomTeleportSettings();
    public JobSettings jobSettings = new JobSettings();
    public LifeStealSettings lifeStealSettings = new LifeStealSettings();
    public TimeShopSettings timeShopSettings = new TimeShopSettings();
    public RabateCodeSettings rabateCodeSettings = new RabateCodeSettings();
    public CheckSettings checkSettings = new CheckSettings();
    public WelcomeSettings welcomeSettings = new WelcomeSettings();

    public Location location = new Location(Bukkit.getWorld("spawn"), 0, 100, 0);
    public String freePlnNpcID = "0f69258a-26ab-4a28-9473-74e8deb1806c";
    public String blackSmithID = "82116773-7f92-49a9-83cf-75d16525d3d8";
    public String discordUrl = "https://dc.cebulasmp.pl/";

    public static class CheckSettings extends OkaeriConfig {
        public String commandToExeciute = "tempban {PLAYER} logaut podczas sprawdzania 3d";
        public Location jailLocation;
    }

    public static class WelcomeSettings extends OkaeriConfig {
        public int prizeFromWelcomeNewPlayer = 100;
        public List<String> welcomeMessagesList = List.of(
                "hej {PLAYER}",
                "{PLAYER} hej",
                "{PLAYER} siema",
                "siema {PLAYER}",
                "{PLAYER} elo",
                "{PLAYER} elo",
                "siemano {PLAYER}",
                "dzień dobry {PLAYER}",
                "{PLAYER} dzień dobry"
        );
    }

    public static class RabateCodeSettings extends OkaeriConfig {
        public List<RabateCode> codeList = List.of(
                new RabateCode(
                        "_purpis_",
                        "lp user {PLAYER} parent addtemp vip 1h"
                ),
                new RabateCode(
                        "malutk0",
                        "lp user {PLAYER} parent addtemp vip 1h"
                ),
                new RabateCode(
                        "k4lisko_",
                        "lp user {PLAYER} parent addtemp vip 1h"
                ),
                new RabateCode(
                        "yaku",
                        "lp user {PLAYER} parent addtemp vip 1h"
                )

        );
    }

    public static class TimeShopSettings extends OkaeriConfig {
        public String npcId = "b5387c7d-1527-4f66-b3a0-7dded0f99d26";
        public List<TimeShop> timeShops = List.of(
                new TimeShop(
                        ItemStackSerializable.write(new ItemBuilder(Material.PAPER)
                                .setName("&7Ranga: &fVIP 1 dzień")
                                .addLore("")
                                .addLore("&8| &7koszt&8: &d86.400 ⭐")
                                .addLore("")
                                .addLore("&bppm &f- &aaby kupić.")
                                .build()),
                        86400,
                        "lp user {PLAYER} parent addtemp vip 1d"
                ),
                new TimeShop(
                        ItemStackSerializable.write(new ItemBuilder(Material.PAPER)
                                .setName("&7Ranga: &fSVIP 1 dzień")
                                .addLore("")
                                .addLore("&8| &7koszt&8: &d259.200 ⭐")
                                .addLore("")
                                .addLore("&bppm &f- &aaby kupić.")
                                .build()),
                        259200,
                        "lp user {PLAYER} parent addtemp svip 1d"
                ),
                new TimeShop(
                        ItemStackSerializable.write(new ItemBuilder(Material.PAPER)
                                .setName("&7Ranga: &fCEBULAK 1 dzień")
                                .addLore("")
                                .addLore("&8| &7koszt&8: &d604.800 ⭐")
                                .addLore("")
                                .addLore("&bppm &f- &aaby kupić.")
                                .build()),
                        604800,
                        "lp user {PLAYER} parent addtemp svip 1d"
                ),
                new TimeShop(
                        ItemStackSerializable.write(new ItemBuilder(Material.PAPER)
                                .setName("&7Ranga: &fDISCO ZBROJA &8(/disco) &f5 godzin")
                                .addLore("")
                                .addLore("&8| &7koszt&8: &d14.400 ⭐")
                                .addLore("")
                                .addLore("&bppm &f- &aaby kupić.")
                                .build()),
                        14400,
                        "lp user {PLAYER} permission settemp cebulasmp.disco true 5h"
                )
        );
    }

    public static class LifeStealSettings extends OkaeriConfig {
        public ItemStack heartItemStack = new ItemBuilder(Material.RED_DYE)
                .setName("&4połówka serca")
                .setLore("", "&akliknij aby dodać")
                .build();

        public List<String> commandList = List.of(
                "tempban {PLAYER} śmierć z 1 sercem wróć do nas po banie 5m"
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
