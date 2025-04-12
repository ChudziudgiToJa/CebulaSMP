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

import java.util.Optional;
import java.util.stream.Collectors;

public class ClanMemberCommandArgument extends ArgumentResolver<CommandSender, ClanMember> {

    private final ClanService clanService;

    public ClanMemberCommandArgument(ClanService clanService) {
        this.clanService = clanService;
    }

    @Override
    protected ParseResult<ClanMember> parse(Invocation<CommandSender> invocation, Argument<ClanMember> context, String argument) {
        Clan clan = this.clanService.findClanByMember(context.getName());
        Optional<ClanMember> clanMember = clan.getMember(context.getName());

        if (clanMember.isEmpty()) {
            return ParseResult.failure("Nie znaleziono takiego gracza.");
        }

        if (clanMember.get().getUuid().equals(clan.getOwner().getUuid())) {
            return ParseResult.failure("nie możesz wyrzucić się z swojego klanu");
        }

        return ParseResult.success(clanMember.get());
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<ClanMember> argument, SuggestionContext context) {
        return SuggestionResult.of(this.clanService.getAllClans()
                .stream()
                .flatMap(clan -> clan.getMembers().stream()
                        .filter(member -> !member.getUuid().equals(clan.getOwner().getUuid())))
                .map(ClanMember::getName)
                .collect(Collectors.toSet()));
    }
}
