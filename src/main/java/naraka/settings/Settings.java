package naraka.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import naraka.channel.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.List;

import static naraka.channel.ChannelType.GUIDE_CHANNEL;

public class Settings {
  private final String token;
  private final HashMap<ChannelType, String> channels;
  private final String boss;

  public Settings(String token, HashMap<ChannelType, String> channels, String boss) {
    this.token = token;
    this.channels = channels;
    this.boss = boss;
  }

  public boolean isListen(MessageReceivedEvent event) {
    return event.getAuthor().getId().equals(boss)
            && channels.containsValue(event.getChannel().getId());
  }

  public String getToken() {
    return token;
  }

  public String getGuideChannelId() {
    return channels.get(GUIDE_CHANNEL);
  }

  public boolean isAllowDeleteMessage(Message message) {
    return message.getAuthor().isBot();
  }
}
