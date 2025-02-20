package pl.chudziudgi.lifesteal.feature.disco;


import org.bukkit.Color;

import java.util.Random;

public class DiscoColorHandler {

    public static Color getRandomColor(Random random) {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return Color.fromRGB(red, green, blue);
    }
}
