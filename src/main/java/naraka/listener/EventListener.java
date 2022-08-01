package naraka.listener;

import lombok.NoArgsConstructor;
import naraka.events.MessageHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
public class EventListener extends ListenerAdapter {
  @Autowired
  private MessageHandler messageHandler;

  public EventListener(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;
    if (!event.getAuthor().getId().equals("329619790413037569") || !event.getChannel().getId().equals("1002112703583756318")) {
      return;
    }
    System.out.println("Message recived: " + event.getMessage().getContentStripped());
    System.out.println("Author is : " + event.getMessage().getAuthor().getName());
    handleEvent(event);
  }

  public void handleEvent(MessageReceivedEvent event) {
    messageHandler.handleMessage(event);
  }


}
