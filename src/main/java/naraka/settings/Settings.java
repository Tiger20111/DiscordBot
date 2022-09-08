package naraka.settings;

import naraka.channel.ChannelType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

import static naraka.channel.ChannelType.COMMAND;
import static naraka.channel.ChannelType.GUIDE_CHANNEL;

public class Settings {
  private final String token;
  private final HashMap<ChannelType, String> channels;
  private final String boss;
  private final String guildId;

  public Settings(String token, HashMap<ChannelType, String> channels, String boss, String guildId) {
    this.token = token;
    this.channels = channels;
    this.boss = boss;
    this.guildId = guildId;
  }

  public boolean isListen(MessageReceivedEvent event) {
    return event.getAuthor().getId().equals(boss)
            && channels.get(COMMAND).equals(event.getChannel().getId());
  }

  public String getToken() {
    return token;
  }

  public String getGuildId() {
    return guildId;
  }

  public TextChannel getGuideChannel(JDA jda) {
    var guild = jda.getGuildById(this.guildId);
    var channelId = getGuideChannelId();
    return guild.getTextChannelById(channelId);
  }

  public String getGuideChannelId() {
    return channels.get(GUIDE_CHANNEL);
  }

  public boolean isAllowDeleteMessage(Message message) {
    return message.getAuthor().isBot();
  }
}
