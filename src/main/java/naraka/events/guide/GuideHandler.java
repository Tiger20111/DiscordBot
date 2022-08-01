package naraka.events.guide;

import lombok.NoArgsConstructor;
import naraka.events.EventHandler;
import naraka.events.TypeEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static naraka.events.TypeEvent.GUIDE;
import static naraka.events.guide.GuideCommand.PUBLISH;
import static java.util.Objects.requireNonNull;

@Service
@NoArgsConstructor
public class GuideHandler implements EventHandler {
  private PublishGuideAdapter publishGuideAdapter;

  @Autowired
  public GuideHandler(PublishGuideAdapter publishGuideAdapter) {
    this.publishGuideAdapter = publishGuideAdapter;
  }

  @Override
  public void handleEvent(TypeEvent typeEvent, MessageReceivedEvent event) {
    if (typeEvent != GUIDE) {
      if (event.getAuthor().getId().contains("329619790413037569")) {
        publishGuideAdapter.publish(event.getChannel());
      }
      return;
    }
    switch (requireNonNull(getCommand(event))) {
      case PUBLISH -> publishGuideAdapter.publish(event.getChannel());
    }
  }

  private GuideCommand getCommand(MessageReceivedEvent event) {
    var contentMessage = event.getMessage().getContentRaw().toLowerCase();
    if (contentMessage.contains("tig.guide/publish")) {
      return PUBLISH;
    }
    return null;
  }
}
