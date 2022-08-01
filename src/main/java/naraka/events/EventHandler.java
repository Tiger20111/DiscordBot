package naraka.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

public interface EventHandler {
  public void handleEvent(TypeEvent typeEvent, MessageReceivedEvent event);
}
