package naraka.settings;

import naraka.channel.ChannelType;
import naraka.token.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static naraka.channel.ChannelType.COMMAND;
import static naraka.channel.ChannelType.GUIDE_CHANNEL;
import static naraka.token.Token.getToken;

@Configuration
public class SettingsConfiguration {
  private final String token = getToken();
  private final String boss = "329619790413037569";
  private final String guideChannelId = "1002112703583756318";
  private final String guideTestChannelId = "1003612455341129799";
  private final String guildId = "412963092214054912";
  private final String guildTestId = "1002844797112958976";
  private final String commandTestChannelId = "1008725045624975360";
  private final String commandChannelId = "1008725045624975360";

//  @Bean
//  public Settings getTestSettings() {
//    HashMap<ChannelType, String> channels = new HashMap<>();
//    channels.put(GUIDE_CHANNEL, guideTestChannelId);
//    channels.put(COMMAND, commandTestChannelId);
//    return new Settings(token, channels, boss, guildTestId);
//  }

  @Bean
  public Settings getSettings() {
    HashMap<ChannelType, String> channels = new HashMap<>();
    channels.put(GUIDE_CHANNEL, guideChannelId);
    channels.put(COMMAND, commandChannelId);
    return new Settings(token, channels, boss, guildId);
  }
}
