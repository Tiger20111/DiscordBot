package naraka.events;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.requests.Route;
import org.springframework.stereotype.Service;

import static naraka.events.TypeEvent.DIALOG;

@NoArgsConstructor
@Service
public class DialogHandler implements EventHandler {
    @Override
    public void handleEvent(TypeEvent typeEvent, MessageReceivedEvent event) {
        if (typeEvent == DIALOG) {
            return;
        }
        var author = event.getAuthor();

        if (author.getId().equals("372729513316057099")) {
            var channel = event.getMessage().getChannel();
            event.getMessage().reply(buildReply()).queue();
        }
    }

    private Message buildReply() {
        var messageBuilder = new MessageBuilder();
        messageBuilder.append("омг");
        return messageBuilder.build();
    }

}
