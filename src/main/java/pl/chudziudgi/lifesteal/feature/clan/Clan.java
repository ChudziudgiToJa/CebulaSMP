package pl.chudziudgi.lifesteal.feature.clan;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.database.repository.Identifiable;

import java.io.Serializable;
import java.util.ArrayList;


@Getter
@Setter
public class Clan implements Serializable, Identifiable<String> {

    private String uuid;
    private String tag;
    private String ownerName;

    private ArrayList<String> memberArrayList;

    private boolean pvp;

    public Clan(Player player, String tag) {
        this.uuid = player.getUniqueId().toString();
        this.ownerName = player.getName();
        this.tag = tag.toUpperCase();

        this.memberArrayList = new ArrayList<>();
        this.memberArrayList.add(player.getName());

        this.pvp = false;
    }


    @Override
    public String getId() {
        return this.uuid;
    }
}
