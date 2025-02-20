package pl.chudziudgi.lifesteal.feature.dailyvpln;

import org.bukkit.entity.Player;

import java.util.Random;

public class DailyVplnManager {

    private final Random random;

    public DailyVplnManager(Random random) {
        this.random = random;
    }


    public double getRandomValueForPlayer(Player player) {
        if (player.hasPermission("cebulasmp.freepln.cebulak")) {
            return getRandomDouble(0.30, 0.60);
        } else if (player.hasPermission("cebulasmp.freepln.mvip")) {
            return getRandomDouble(0.10, 0.30);
        } else if (player.hasPermission("cebulasmp.freepln.vip")) {
            return getRandomDouble(0.05, 0.10);
        }
        return getRandomDouble(0.10, 0.50);
    }

    private double getRandomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
