package pl.chudziudgi.lifesteal.feature.job;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JobDropChance implements Serializable {
    private final String itemStackString;
    private final double chance;

    public JobDropChance(String itemStackString, double chance) {
        this.itemStackString = itemStackString;
        this.chance = chance;
    }
}
