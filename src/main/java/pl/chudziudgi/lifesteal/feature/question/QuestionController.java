package pl.chudziudgi.lifesteal.feature.question;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class QuestionController implements Listener {

    private final QuestionManager questionManager;
    private final UserService userService;

    public QuestionController(QuestionManager questionManager, UserService userService) {
        this.questionManager = questionManager;
        this.userService = userService;
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        Question question = questionManager.getQuestion();
        if (event.isCancelled()) return;
        if (question == null) return;
        if (!message.toLowerCase().contains(question.getAnswer().toLowerCase())) return;
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user == null) return;
            onlinePlayer.sendMessage("");
            MessageUtil.sendMessage(onlinePlayer, "&3ⓅⓎⓉⓐⓝⒾⒺ &7Gracz &b%s&7 odpowiedział jako pierwszy! &8(&7otrzymał/a 1000 monet&8)".formatted(player.getName()));
            onlinePlayer.sendMessage("");
            user.addMoney(1000);
            onlinePlayer.playSound(onlinePlayer, Sound.BLOCK_PORTAL_TRIGGER, 10, 10);
        });
        questionManager.setQuestion(null);
        event.setCancelled(true);
    }
}