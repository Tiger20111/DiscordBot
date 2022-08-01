package naraka.events;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

import static naraka.events.TypeEvent.*;

@Service
@NoArgsConstructor
public class MessageHandler {
  private List<EventHandler> eventHandlers;

  @Autowired
  public MessageHandler(List<EventHandler> eventHandlers) {
    this.eventHandlers = eventHandlers;
  }

  public void handleMessage(MessageReceivedEvent event) {
    var typeCommand = defineTypeCommand(event.getMessage());
    eventHandlers.forEach(c -> c.handleEvent(typeCommand, event));
  }

  private TypeEvent defineTypeCommand(Message message) {
    var contentMessage = message.getContentRaw().toLowerCase();
    if (contentMessage.contains("tig.hello")) {
      return HELLO;
    }
    if (contentMessage.contains("tig.guide")) {
      return GUIDE;
    }
    return NONE;
  }
}
