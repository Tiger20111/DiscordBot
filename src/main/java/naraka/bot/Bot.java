package naraka.bot;

import naraka.listener.EventListener;
import naraka.settings.Settings;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
@ComponentScan
@EnableAutoConfiguration
@PropertySource("application.properties")
public class Bot {
  private final JDA bot;

  Bot(EventListener listener, Settings settings) throws LoginException {
    bot = JDABuilder.createDefault(settings.getToken())
            .setActivity(Activity.playing("Naraka Guides"))
            .addEventListeners(listener)
            .build();
    try {
      bot.awaitReady();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}