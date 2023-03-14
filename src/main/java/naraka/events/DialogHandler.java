package naraka.events;

import lombok.NoArgsConstructor;
import naraka.settings.Settings;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.requests.Route;
import org.springframework.stereotype.Service;

import static naraka.events.TypeEvent.DIALOG;

@NoArgsConstructor
@Service
public class DialogHandler implements EventHandler {
    private Settings settings;

    public DialogHandler(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void handleEvent(TypeEvent typeEvent, MessageReceivedEvent event) {
        if (typeEvent != DIALOG) {
            return;
        }
        var author = event.getAuthor();
        var channel = event.getMessage().getChannel();
        event.getMessage().reply(buildReply(author.getName())).queue();
    }

    private Message buildReply(String nameAuthor) {
        var messageBuilder = new MessageBuilder();
        messageBuilder.append("омг, " + nameAuthor);
        return messageBuilder.build();
    }

}
