package naraka.events.guide;

import lombok.NoArgsConstructor;
import naraka.events.EventHandler;
import naraka.events.TypeEvent;
import naraka.settings.Settings;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static naraka.events.TypeEvent.GUIDE;
import static naraka.events.guide.GuideCommand.PUBLISH;
import static java.util.Objects.requireNonNull;

@Service
@NoArgsConstructor
public class GuideHandler implements EventHandler {
  private PublishGuideAdapter publishGuideAdapter;
  private Settings settings;

  @Autowired
  public GuideHandler(PublishGuideAdapter publishGuideAdapter, Settings settings) {
    this.publishGuideAdapter = publishGuideAdapter;
    this.settings = settings;
  }

  @Override
  public void handleEvent(TypeEvent typeEvent, MessageReceivedEvent event) {
    if (typeEvent != GUIDE) {
      return;
    }
    switch (requireNonNull(getCommand(event))) {
      case PUBLISH -> publishGuideAdapter.publish(event.getGuild().getTextChannelById(settings.getGuideChannelId()));
    }
  }

  private GuideCommand getCommand(MessageReceivedEvent event) {
    return PUBLISH;
//    var contentMessage = event.getMessage().getContentRaw().toLowerCase();
//    if (contentMessage.contains("tig.guide/publish")) {
//      return PUBLISH;
//    }
//    return null;
  }
}
