package pl.chudziudgi.lifesteal.feature.statistic;


import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "resetstatystyk")
public class StatisticCommand {

    private final StatisticInventory statisticInventory;

    public StatisticCommand(StatisticInventory statisticInventory) {
        this.statisticInventory = statisticInventory;
    }


    @Execute
    void execute(@Context Player player) {
        this.statisticInventory.showAreYouSure(player);
    }
}
