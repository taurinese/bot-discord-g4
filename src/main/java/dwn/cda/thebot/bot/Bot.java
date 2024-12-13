package dwn.cda.thebot.bot;

import dwn.cda.thebot.services.ScoreService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Bot extends ListenerAdapter {
    private Guild guild;
    @Autowired
    private ScoreService scoreService;
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("hello", "Say Hello"),
                Commands.slash("score", "Get the user score")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "hello":
                event.reply("Hello World").queue();
                break;
            case "score":
                String userId = event.getUser().getId();
                event.reply("Score : " + scoreService.getScore(userId)).queue();
                break;
            default:
                event.reply("I'm a teapot").setEphemeral(true).queue();
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
