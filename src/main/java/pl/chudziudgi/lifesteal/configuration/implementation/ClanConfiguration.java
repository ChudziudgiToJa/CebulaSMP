package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClanConfiguration extends OkaeriConfig {

    public int clanPrice = 3500;

    public List<String> blockCommandListForClan = List.of("tpaaccept","tpaccept");
    public List<String> blockCommandList = List.of("tpaaccept","tpaccept", "sethome", "ec", "enderchest");
}

