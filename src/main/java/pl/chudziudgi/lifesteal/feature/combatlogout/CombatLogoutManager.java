package pl.chudziudgi.lifesteal.feature.combatlogout;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;

public class CombatLogoutManager {

    private final Set<CombatLogout> combatSet;

    public CombatLogoutManager() {
        this.combatSet = Sets.newLinkedHashSet();
    }

    public void createCombat(final Player player, final int time) {
        final CombatLogout combat = this.getCombat(player);
        if (combat != null) {
            this.combatSet.remove(combat);
        }
        this.combatSet.add(new CombatLogout(player.getUniqueId(), System.currentTimeMillis() + time));
    }

    public boolean inCombat(Player player) {
        final CombatLogout combat = getCombat(player);
        if (combat == null) {
            return false;
        }
        return combat.getLeftTime() >= System.currentTimeMillis();
    }

    public void removeCombat(CombatLogout combat) {
        if (this.getCombat(combat.getPlayer()) != null) {
            combatSet.remove(combat);
        }
    }

    public CombatLogout getCombat(final Player player) {
        return this.combatSet.stream().filter(combat -> combat.getIdentifier().equals(player.getUniqueId())).findFirst().orElse(null);
    }
}