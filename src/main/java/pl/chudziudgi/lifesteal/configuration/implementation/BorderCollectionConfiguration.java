package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorderCollectionConfiguration extends OkaeriConfig {

    public String npcId = "4d54e06d-3773-4df2-90ac-37ca981b99b3";
    public int worldSize = 20000;
    public double depositMoney = 0;
}
