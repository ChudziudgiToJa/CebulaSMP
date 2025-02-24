package pl.chudziudgi.lifesteal.feature.clan;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ClanMember implements Serializable {

    private UUID uuid;
    private String name;

    public ClanMember(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }
}
