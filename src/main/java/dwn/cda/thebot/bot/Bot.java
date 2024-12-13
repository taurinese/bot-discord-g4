package dwn.cda.thebot.bot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Bot extends ListenerAdapter {
    private Guild guild;
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("hello", "Say Hello"),
                Commands.slash("duel", "Challenge someone to a duel")
                        .addOptions(
                                new OptionData(OptionType.USER, "opponent", "The user you want to challenge", true) // Paramètre obligatoire pour l'utilisateur
                        )

        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "hello":
                event.reply("Hello World").queue();
                break;
            case "duel":
                //  l'utilisateur à défier
                User opponent = Objects.requireNonNull(event.getOption("opponent")).getAsUser();

                // Message
                event.reply(String.format("The duel between <@%s> and <@%s> is about to begin!", event.getUser().getId(), opponent.getId())).queue();
                break;
            default:
                event.reply("I'm a teapot").setEphemeral(true).queue();
        }
    }
}
