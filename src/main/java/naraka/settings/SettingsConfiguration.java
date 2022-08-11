package naraka.settings;

import naraka.channel.ChannelType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static naraka.channel.ChannelType.GUIDE_CHANNEL;

@Configuration
public class SettingsConfiguration {
  private final String token = "";
  private final String boss = "329619790413037569";
  private final String guideChannelId = "1002112703583756318";
  private final String guideTestChannelId = "1003612455341129799";

//  @Bean
//  public Settings getTestSettings() {
//    HashMap<ChannelType, String> channels = new HashMap<>();
//    channels.put(GUIDE_CHANNEL, guideTestChannelId);
//    return new Settings(token, channels, boss);
//  }

  @Bean
  public Settings getSettings() {
    HashMap<ChannelType, String> channels = new HashMap<>();
    channels.put(GUIDE_CHANNEL, guideChannelId);
    return new Settings(token, channels, boss);
  }
}
