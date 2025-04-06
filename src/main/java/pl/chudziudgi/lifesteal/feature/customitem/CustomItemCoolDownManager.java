package pl.chudziudgi.lifesteal.feature.customitem;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomItemCoolDownManager {
    private final ConcurrentHashMap<UUID, CustomItemData> uuidCustomItemDataHashMap = new ConcurrentHashMap<>();

    public void addCoolDown(final Player player, long time) {
        this.uuidCustomItemDataHashMap.put(player.getUniqueId(), new CustomItemData(player.getInventory().getItemInMainHand(), time + System.currentTimeMillis()));
    }

    public boolean isCoolDown(final Player player) {
        if (!this.uuidCustomItemDataHashMap.containsKey(player.getUniqueId())) {
            return false;
        }
        CustomItemData customItemData = this.uuidCustomItemDataHashMap.get(player.getUniqueId());
        return customItemData.getTime() > System.currentTimeMillis();
    }

    public CustomItemData getData(final Player player) {
        return this.uuidCustomItemDataHashMap.get(player.getUniqueId());
    }
}