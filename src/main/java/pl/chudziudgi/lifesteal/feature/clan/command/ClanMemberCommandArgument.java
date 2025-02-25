package pl.chudziudgi.lifesteal.feature.clan.command;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.ClanMember;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;

import java.util.stream.Collectors;

public class ClanMemberCommandArgument extends ArgumentResolver<CommandSender, ClanMember> {

    private final ClanService clanService;

    public ClanMemberCommandArgument(ClanService clanService) {
        this.clanService = clanService;
    }

    @Override
    protected ParseResult<ClanMember> parse(Invocation<CommandSender> invocation, Argument<ClanMember> context, String argument) {
        ClanMember clanMember = this.clanService.findClanMemberByName(argument);
        Clan clan = this.clanService.findClanByMember(clanMember);

        if (clanMember == null) {
            return ParseResult.failure("Nie znaleziono takiego gracza.");
        }

        if (clanMember.getName().equals(clan.getOwnerName())) {
            return ParseResult.failure("nie możesz wyrzucić się z swojego klanu");
        }

        return ParseResult.success(clanMember);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<ClanMember> argument, SuggestionContext context) {
        return SuggestionResult.of(this.clanService.getAllClans()
                .stream()
                .flatMap(clan -> clan.getClanMemberArrayList().stream()
                        .filter(member -> !member.getName().equals(clan.getOwnerName())))
                .map(ClanMember::getName)
                .collect(Collectors.toSet()));
    }

}
