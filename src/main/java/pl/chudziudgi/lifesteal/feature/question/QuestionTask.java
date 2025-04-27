package pl.chudziudgi.lifesteal.feature.question;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.configuration.implementation.QuestionConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class QuestionTask extends BukkitRunnable {

    private final QuestionManager questionManager;
    private final QuestionConfiguration configuration;
    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;

    public QuestionTask(QuestionManager questionManager, QuestionConfiguration configuration, SurvivalPlugin survivalPlugin, UserService userService) {
        this.questionManager = questionManager;
        this.configuration = configuration;
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
        this.runTaskTimerAsynchronously(survivalPlugin, 60, (60 * 60) * 20);
    }


    @Override
    public void run() {
        Question question = questionManager.getRandomQuestion(this.configuration.questionList);
        questionManager.setQuestion(question);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            User user = this.userService.findUserByUUID(onlinePlayer.getUniqueId());
            onlinePlayer.playSound(onlinePlayer, Sound.BLOCK_END_GATEWAY_SPAWN, 10, 10);
            onlinePlayer.sendMessage("");
            MessageUtil.sendMessage(onlinePlayer, "&3ⓅⓎⓉⓐⓃⒾⒺ &7" + question.getQuestion());
            onlinePlayer.sendMessage("");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(questionManager.getQuestion() == null)) {
                    questionManager.setQuestion(null);
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendMessage("");
                        MessageUtil.sendMessage(player, "&3ⓅⓎⓉⓐⓃⒾⒺ &7Nikt nie odpowiedział. Odpowiedź: &f" + question.getAnswer());
                        player.sendMessage("");
                        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 10, 10);
                    });
                }
            }
        }.runTaskLaterAsynchronously(this.survivalPlugin, 30 * 20);
    }
}