package org.example.factions.factionsmain.commands;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.example.factions.factionsmain.factions.FactionContainer;
import org.example.factions.factionsmain.factions.FactionPlayer;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;

import java.util.Arrays;
import java.util.UUID;

public class FactionAddCommand implements CommandExecutor {
    public FactionAddCommand(Parameter.Value<UUID> userNameParam, Parameter.Value<String> factionNameParam) {
        this.factionNameParam = factionNameParam;
        this.userNameParam = userNameParam;
    }

    @Override
    public CommandResult execute(CommandContext context) throws CommandException {
        String factionName = context.requireOne(factionNameParam);
        UUID userName = context.requireOne(userNameParam);
        if (userName == null) return CommandResult.error(Component.text("Player does not exist"));
        FactionContainer.getFaction(factionName).addUser(userName, new FactionPlayer(Arrays.asList("member", "new")));
        context.sendMessage(Identity.nil(), Component.text("Added " + userName.toString() + " to " + factionName));
        return CommandResult.success();
    }

    private final Parameter.Value<String> factionNameParam;
    private final Parameter.Value<UUID> userNameParam;
}
