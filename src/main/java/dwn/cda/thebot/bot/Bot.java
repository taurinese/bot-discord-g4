package dwn.cda.thebot.bot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Bot extends ListenerAdapter {
    private Guild guild;
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("hello", "Say Hello")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "hello":
                event.reply("Hello World").queue();
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


    public void player(MessageReceivedEvent event) {
        var opponent = event.getMessage().getMentions().getMembers().get(0);
        var player = event.getMember();

        if (opponent.equals(player)) {
            event.getChannel().sendMessage("Vous ne pouvez pas vous battre contre vous-même !").queue();
            return;
        }

        int hpPlayer = 1000;
        int hpOpponent = 1000;
        int hitPlayer = getRandomNumberInRange(1, 100);
        int hitOpponent = getRandomNumberInRange(1, 100);
    }
}
