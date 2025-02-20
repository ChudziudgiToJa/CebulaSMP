package pl.chudziudgi.lifesteal.feature.user.command;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

import java.util.stream.Collectors;

public class UserCommandArgument extends ArgumentResolver<CommandSender, User> {

    private final UserService userService;

    public UserCommandArgument(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected ParseResult<User> parse(Invocation<CommandSender> invocation, Argument<User> context, String
            argument) {
        User user = this.userService.findUserByNickName(argument);

        if (user == null) {
            return ParseResult.failure("Nie znaleziono takiego gracza.");
        }

        return ParseResult.success(user);
    }

    @Override
    public SuggestionResult suggest
            (Invocation<CommandSender> invocation, Argument<User> argument, SuggestionContext context) {
        return SuggestionResult.of(this.userService.users()
                .stream()
                .map(User::getNickName)
                .collect(Collectors.toSet()));
    }
}
