package pl.chudziudgi.lifesteal.feature.clan.command;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;

import java.util.stream.Collectors;

public class ClanCommandArgument extends ArgumentResolver<CommandSender, Clan> {

    private final ClanService clanService;

    public ClanCommandArgument(ClanService clanService) {
        this.clanService = clanService;
    }

    @Override
    protected ParseResult<Clan> parse(Invocation<CommandSender> invocation, Argument<Clan> context, String
            argument) {
        Clan clan = this.clanService.findClanByTag(argument);

        if (clan == null) {
            return ParseResult.failure("Nie znaleziono takiego klanu.");
        }

        return ParseResult.success(clan);
    }

    @Override
    public SuggestionResult suggest
            (Invocation<CommandSender> invocation, Argument<Clan> argument, SuggestionContext context) {
        return SuggestionResult.of(this.clanService.getAllClans()
                .stream()
                .map(Clan::getTag)
                .collect(Collectors.toSet()));
    }
}
