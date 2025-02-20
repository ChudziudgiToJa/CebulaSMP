package pl.chudziudgi.lifesteal.feature.backup;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Backup implements Serializable {
    private final Instant instant;
    private final int lvl;
    private final float exp;
    private final String itemStackArrayList;

    public Backup(Instant instant, int lvl, float exp, String itemStackArrayList) {
        this.instant = instant;
        this.lvl = lvl;
        this.exp = exp;
        this.itemStackArrayList = itemStackArrayList;
    }


    public String getInstantFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }
}
