package org.example.factions.factionsmain.commands;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

import java.util.UUID;

public class CommandBuilder {
    public CommandBuilder() {
        factionNameParam = Parameter.string().key("faction_name").build();
        userNameParam = Parameter.user().key("user_name").build();
    }

    public Command.Parameterized buildFactionCommand() {
        Command.Parameterized factionCreateCommand = Command.builder()
                .executor(new FactionCreateCommand(factionNameParam))
                .permission("faction.create")
                .shortDescription(Component.text("Create a new faction with given name"))
                .addParameter(factionNameParam)
                .build();

        Command.Parameterized factionAddCommand = Command.builder()
                .executor(new FactionAddCommand(userNameParam, factionNameParam))
                .permission("faction.add")
                .shortDescription(Component.text("Adds the given user to the given faction"))
                .addParameters(userNameParam, factionNameParam)
                .build();

        return Command.builder()
                .executor(new FactionCommand())
                .addChild(factionCreateCommand, "create")
                .addChild(factionAddCommand, "add")
                .shortDescription(Component.text("The main command of the faction plugin"))
                .build();
    }
    private static Parameter.Value<String> factionNameParam;
    private static Parameter.Value<UUID> userNameParam;
}
