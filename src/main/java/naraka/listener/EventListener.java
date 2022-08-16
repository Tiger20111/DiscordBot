package naraka.listener;

import naraka.events.MessageHandler;
import naraka.settings.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.requests.restaction.MessageActionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventListener extends ListenerAdapter {
  @Autowired
  private MessageHandler messageHandler;

  @Autowired
  private Settings settings;

  public EventListener(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot() || !settings.isListen(event)) return;

    System.out.println("Message recived: " + event.getMessage().getContentStripped());
    System.out.println("Author is : " + event.getMessage().getAuthor().getName());
    handleEvent(event);
  }

  public void handleEvent(MessageReceivedEvent event) {
    messageHandler.handleMessage(event);
  }


}
