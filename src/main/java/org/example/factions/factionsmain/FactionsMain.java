package org.example.factions.factionsmain;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.Logger;
import org.example.factions.factionsmain.commands.CommandBuilder;
import org.example.factions.factionsmain.factions.FactionContainer;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.advancement.*;
import org.spongepowered.api.advancement.criteria.AdvancementCriterion;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.advancement.AdvancementTreeEvent;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The main class of your Sponge plugin.
 *
 * <p>All methods are optional -- some common event registrations are included as a jumping-off point.</p>
 */
@Plugin("factionsmain")
public class FactionsMain {

    private final PluginContainer container;
    private final Logger logger;

    @Inject
    FactionsMain(final PluginContainer container, final Logger logger) {
        this.container = container;
        this.logger = logger;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        // Perform any one-time setup
        this.logger.info("Constructing factionsmain");
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        // Any setup per-game instance. This can run multiple times when
        // using the integrated (singleplayer) server.
        logger.info("Hello world from the factions plugin!");
        FactionContainer.loadFactions();
    }

    @Listener
    public void onAdvancementGenerate(final RegisterDataPackValueEvent<Advancement> event) {
        ItemStack stoneSword = ItemStack.builder().itemType(ItemTypes.STONE_SWORD).build();
        DisplayInfo adv_info = DisplayInfo.builder()
                .announceToChat(true)
                .description(Component.text("Enter a faction"))
                .hidden(false)
                .icon(stoneSword)
                .showToast(true)
                .title(Component.text("Faction Hopper"))
                .type(AdvancementTypes.GOAL).build();
        AdvancementCriterion crit = AdvancementCriterion.dummy();
        ResourceKey key = ResourceKey.builder().value("entry").namespace("factions").build();
        Advancement adv = Advancement.builder()
                .displayInfo(adv_info)
                .criterion(crit)
                .root()
                .background(ResourceKey.minecraft("textures/gui/advancements/backgrounds/stone.png"))
                //.key(ResourceKey.of(this.container, "root"))
                .key(key)
                .build();
        event.register(adv);
    }

    @Listener
    public void onServerStopping(final StoppingEngineEvent<Server> event) {
        // Any tear down per-game instance. This can run multiple times when
        // using the integrated (singleplayer) server.
        FactionContainer.saveFactions();
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        // Register a simple command
        // When possible, all commands should be registered within a command register event
        CommandBuilder builder = new CommandBuilder();
        event.register(this.container, builder.buildFactionCommand(), "factions", "faction", "f");
    }
}
