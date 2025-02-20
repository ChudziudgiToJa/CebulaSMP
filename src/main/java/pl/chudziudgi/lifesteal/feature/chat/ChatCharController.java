package pl.chudziudgi.lifesteal.feature.chat;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class ChatCharController implements Listener {

    @EventHandler
    public void onSendMessageOnChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        String regex = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ0-9 ,.?!-/_':;<>()!@#$%^&*]*$";
        if (!message.matches(regex)) {
            event.setCancelled(true);
            MessageUtil.sendMessage(event.getPlayer(),"&cWiadomość zawiera zakazane znaki.");
            event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_VILLAGER_NO, 5 ,5);
        }
    }

}
