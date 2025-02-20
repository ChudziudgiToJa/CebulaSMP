package pl.chudziudgi.lifesteal.feature.blocker;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobAiSpawnerController implements Listener {

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            LivingEntity entity = event.getEntity();
            entity.setAI(false);
        }
    }
}
