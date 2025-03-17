package pl.chudziudgi.lifesteal.feature.user;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.database.repository.Identifiable;
import pl.chudziudgi.lifesteal.feature.backup.Backup;
import pl.chudziudgi.lifesteal.feature.disco.DiscoType;
import pl.chudziudgi.lifesteal.feature.enderchest.EnderChest;
import pl.chudziudgi.lifesteal.feature.job.JobType;
import pl.chudziudgi.lifesteal.feature.kit.KitData;
import pl.chudziudgi.lifesteal.feature.pet.object.Pet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


@Getter
@Setter
public class User implements Serializable, Identifiable<String> {

    private String uuid;
    private String nickName;
    private double vPln;
    private Double money;
    private int timeMoney;
    private Integer spentTime;
    private Integer progress;
    private JobType jobType;
    private long jobCoolDown;
    private long dailyFreeVpln;
    private int dead;
    private int kill;
    private int breakBlock;
    private int placeBlock;
    private boolean vanish;
    private DiscoType discoType;
    private ArrayList<KitData> kits;
    private ArrayList<Backup> backups;
    private ArrayList<Pet> petDataArrayList;
    private ArrayList<EnderChest> enderChests;
    private ArrayList<String> rabateCode;


    public User(Player player) {
        this.uuid = String.valueOf(player.getUniqueId());
        this.nickName = player.getName();
        this.vPln = 0.0;
        this.money = 0.0;
        this.timeMoney = 0;
        this.spentTime = 0;
        this.progress = 0;
        this.jobType = JobType.CLEAR;
        this.jobCoolDown = 0;
        this.dailyFreeVpln = 0;
        this.dead = 0;
        this.kill = 0;
        this.breakBlock = 0;
        this.placeBlock = 0;
        this.vanish = false;
        this.discoType = DiscoType.CLEAR;
        this.kits = new ArrayList<>();
        this.backups = new ArrayList<>();
        this.petDataArrayList = new ArrayList<>();
        this.enderChests = new ArrayList<>();
        this.rabateCode = new ArrayList<>();
    }


    @Override
    public String getId() {
        return this.uuid;
    }

    public void addOnlineTime(int onlineTime) {
        this.spentTime += onlineTime;
    }

    public void addMoney(double money) {
        this.money += money;
    }


    public void removeMoney(double money) {
        this.money -= money;
    }

    public void addProgress(int progress) {
        this.progress += progress;
    }
}
