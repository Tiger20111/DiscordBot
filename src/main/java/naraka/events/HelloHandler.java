package naraka.events;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import naraka.stickers.StickerManager;
import org.springframework.stereotype.Service;

import static naraka.events.TypeEvent.HELLO;

@NoArgsConstructor
@Service
public class HelloHandler implements EventHandler {
  private StickerManager stickerManager;

  @Override
  public void handleEvent(TypeEvent typeEvent, MessageReceivedEvent event) {
    if (typeEvent != HELLO) return;
    hello(typeEvent, event);
  }

  private void hello(TypeEvent typeEvent, MessageReceivedEvent event) {
    var author = event.getAuthor();

    var channel = event.getMessage().getChannel();
    var message = channel.sendMessage("Hello, " + author.getName());
    message.queue();
  }
}
