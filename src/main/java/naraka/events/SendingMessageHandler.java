package naraka.events;

import lombok.NoArgsConstructor;
import naraka.settings.Settings;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import static naraka.events.TypeEvent.MESSAGE;

@Service
@NoArgsConstructor
public class SendingMessageHandler implements EventHandler {
    private Settings settings;

    public SendingMessageHandler(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void handleEvent(TypeEvent typeEvent, MessageReceivedEvent event) {
        if (typeEvent != MESSAGE) {
            return;
        }
        var guild = event.getJDA().getGuildById("412963092214054912");
        var channel = guild.getTextChannelById("875489094233378916");
        channel.sendMessage(getMessage()).queue();
    }

    private String getMessage() {
        var messageText = "Провожаем эти последнии минуты этого дня, помня, сколько всего хорошего нам сделал Хика. Спасибо. Двигайся вперед и будь таким же душкой! Еще раз: С Днем Рождения Тебя!";
        return messageText;
    }
}
