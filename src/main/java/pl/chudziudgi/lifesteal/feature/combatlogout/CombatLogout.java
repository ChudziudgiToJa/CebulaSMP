package pl.chudziudgi.lifesteal.feature.combatlogout;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class CombatLogout {

    private final UUID identifier;
    private long leftTime;

    public CombatLogout(final UUID identifier, final long time){
        this.identifier = identifier;
        this.leftTime = time;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(this.identifier);
    }
}