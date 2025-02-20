package pl.chudziudgi.lifesteal.util;

import com.google.common.collect.ImmutableMap;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class MessageUtil {

    private static final Map<Character, Character> charMap = createCharMap();

    private static final Map<String, String> PLACEHOLDERS = new ImmutableMap.Builder<String, String>()
            .put("<<", "«")
            .build();

    private MessageUtil() {
    }

    private static Map<Character, Character> createCharMap() {
        Map<Character, Character> map = new HashMap<>();
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String formattedLetters = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ";

        for (int i = 0; i < lowercaseLetters.length(); i++) {
            map.put(lowercaseLetters.charAt(i), formattedLetters.charAt(i));
        }

        return map;
    }

    public static String smallText(String text) {
        StringBuilder formattedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '&' && i + 1 < text.length() && "0123456789abcdefklmnor".indexOf(Character.toLowerCase(text.charAt(i + 1))) != -1) {
                formattedText.append(c);
                formattedText.append(text.charAt(i + 1));
                i++;
                continue;
            }

            if (charMap.containsKey(Character.toLowerCase(c))) {
                formattedText.append(charMap.get(Character.toLowerCase(c)));
            } else {
                formattedText.append(c);
            }
        }

        return smallTextToColor(formattedText.toString());
    }

    public static String smallTextToColor(String text) {
        StringBuilder formattedText = new StringBuilder();
        boolean insideVariable = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '{') {
                insideVariable = true;
                formattedText.append(c);
                continue;
            }

            if (c == '}') {
                insideVariable = false;
                formattedText.append(c);
                continue;
            }

            if (insideVariable) {
                formattedText.append(c);
                continue;
            }

            if (c == '&' && i + 1 < text.length() && "0123456789abcdefklmnor".indexOf(Character.toLowerCase(text.charAt(i + 1))) != -1) {
                formattedText.append(c);
                formattedText.append(text.charAt(i + 1));
                i++;
                continue;
            }

            if (charMap.containsKey(Character.toLowerCase(c))) {
                formattedText.append(charMap.get(Character.toLowerCase(c)));
            } else {
                formattedText.append(c);
            }
        }

        return colored(formattedText.toString());
    }

    public static String colored(String text) {
        return apply(text);
    }

    public static List<String> colored(List<String> text) {
        return text.stream()
                .map(MessageUtil::apply)
                .collect(Collectors.toList());
    }

    static String apply(String entity) {
        return ChatColor.translateAlternateColorCodes('&', PLACEHOLDERS.entrySet().stream()
                .reduce(entity, (string, entry) -> string.replace(entry.getKey(), entry.getValue()), (a, b) -> a));
    }


    public static void sendMessage(Player player, String message) {
        player.sendMessage(smallText(message));
    }

    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(smallText(message));
    }

    public static void sendTitle(Player player, String title, String subTitle, int fadeInTime, int stayTime, int fadeOutTime) {
        player.sendTitle(smallText(title), smallText(subTitle), fadeInTime, stayTime, fadeOutTime);
    }

    public static void sendActionbar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(smallTextToColor(message)));
    }
}