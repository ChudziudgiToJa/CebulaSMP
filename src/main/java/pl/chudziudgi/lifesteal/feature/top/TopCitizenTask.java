package pl.chudziudgi.lifesteal.feature.top;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.HologramTrait;
import org.bukkit.Bukkit;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

public class TopCitizenTask implements Runnable {

    private final TopManager topManager;

    public TopCitizenTask(final SurvivalPlugin survivalPlugin, TopManager topManager) {
        this.topManager = topManager;
        Bukkit.getScheduler().runTaskTimer(survivalPlugin, this, 0, 20*600);
    }

    @Override
    public void run() {
        List<Supplier<List<User>>> operations = List.of(
                topManager::get16UsersSpendTime,
                topManager::get16UsersMoneyTop
        );

        for (int i = 0; i < operations.size(); i++) {
            updateTopNPCs(operations.get(i).get(), i * 3, i);
        }
    }

    private void updateTopNPCs(List<User> entries, int startingNPCId, int operationIndex) {
        List<String> symbols = List.of("➊", "➋", "➌");
        List<String> labelsTitle = List.of(
                MessageUtil.smallText("&lczasu"),
                MessageUtil.smallText("&lmonet")
        );
        List<String> labelSubtitles = List.of(
                MessageUtil.smallText(" &2czasu"),
                MessageUtil.smallText(" &2monet")
        );

        for (int i = 0; i < 3; i++) {
            NPC npc = CitizensAPI.getNPCRegistry().getById(startingNPCId + i);
            if (npc == null) continue;

            User entry = entries.size() > i ? entries.get(i) : null;
            String topSymbol = symbols.get(i);
            String labelTitle = labelsTitle.get(operationIndex);
            String labelSubtitle = labelSubtitles.get(operationIndex);
            String pointsDisplay;

            if (operationIndex == 0) {
                pointsDisplay = entry != null ? DurationUtil.format(Duration.ofSeconds(entry.getSpentTime())) : "0";
            } else {
                pointsDisplay = entry != null ? DecimalUtil.getFormat(entry.getMoney()) : "0";
            }

            npc.getOrAddTrait(HologramTrait.class).setLine(0, MessageUtil.colored(entry != null ? "&2• &7" + pointsDisplay + labelSubtitle + " &2•" : "&3• &7 0" + labelSubtitle + " &3•"));
            npc.getOrAddTrait(HologramTrait.class).setLine(1, MessageUtil.colored(entry != null ? entry.getNickName() : MessageUtil.smallText("brak")));
            npc.getOrAddTrait(HologramTrait.class).setLine(2, MessageUtil.colored("&f&l⬇ &a&l" + topSymbol + " &lᴛᴏᴘᴋᴀ " + labelTitle + " &f&l⬇"));
        }
    }
}