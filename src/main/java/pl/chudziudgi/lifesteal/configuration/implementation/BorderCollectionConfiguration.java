package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorderCollectionConfiguration extends OkaeriConfig {

    public String npcId = "";
    public int worldSize = 20000;
    public double depositMoney = 0;
}
