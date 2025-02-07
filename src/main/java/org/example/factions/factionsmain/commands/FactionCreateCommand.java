package org.example.factions.factionsmain.commands;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.example.factions.factionsmain.factions.Faction;
import org.example.factions.factionsmain.factions.FactionContainer;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;

public class FactionCreateCommand implements CommandExecutor {
    public FactionCreateCommand(Parameter.Value<String> param) {
        this.factionNameParam = param;
    }

    @Override
    public CommandResult execute(CommandContext context) throws CommandException {
        String factionName = context.requireOne(factionNameParam);
        FactionContainer.createFaction(factionName, new Faction());
        context.sendMessage(Identity.nil(), Component.text("Created faction: " + factionName));
        return CommandResult.success();
    }

    private final Parameter.Value<String> factionNameParam;
}
