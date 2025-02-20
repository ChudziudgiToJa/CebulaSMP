package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorderCollectionConfiguration extends OkaeriConfig {

    public int npcId = 33;
    public int worldSize = 5000;
    public double depositMoney = 0;
}
