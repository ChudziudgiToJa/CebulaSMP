package pl.chudziudgi.lifesteal.feature.lootcase;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import pl.chudziudgi.lifesteal.configuration.implementation.LootCaseConfiguration;

import java.util.stream.Collectors;

public class LootCaseCommandArgument extends ArgumentResolver<CommandSender, LootCase> {

    private final LootCaseConfiguration lootCaseConfiguration;

    public LootCaseCommandArgument(LootCaseConfiguration lootCaseConfiguration) {
        this.lootCaseConfiguration = lootCaseConfiguration;
    }


    @Override
    protected ParseResult<LootCase> parse(Invocation<CommandSender> invocation, Argument<LootCase> context, String argument) {
        for (LootCase lootCase : this.lootCaseConfiguration.lootCases) {
            if (lootCase.getName() != null && lootCase.getName().contains(argument)) {
                return ParseResult.success(lootCase);
            }
        }
        return ParseResult.failure("&cNie ma takiego chesta");
    }

    @Override
    public SuggestionResult suggest
            (Invocation<CommandSender> invocation, Argument<LootCase> argument, SuggestionContext context) {
        return SuggestionResult.of(this.lootCaseConfiguration.lootCases
                .stream()
                .map(LootCase::getName)
                .collect(Collectors.toSet()));
    }
}
