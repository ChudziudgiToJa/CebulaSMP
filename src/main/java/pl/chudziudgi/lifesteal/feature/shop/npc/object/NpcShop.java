package pl.chudziudgi.lifesteal.feature.shop.npc.object;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class NpcShop implements Serializable {
    private final String name;
    private final String npcId;
    private final List<NpcShopItemToInteract> npcShopItemToInteracts;

    public NpcShop(String name, String npcId, List<NpcShopItemToInteract> npcShopItemToInteracts) {
        this.name = name;
        this.npcId = npcId;
        this.npcShopItemToInteracts = npcShopItemToInteracts;
    }
}
