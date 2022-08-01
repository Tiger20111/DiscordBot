package naraka.bot;

import naraka.listener.EventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Value;
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
  private final String token;

  Bot(EventListener listener) throws LoginException {
    token = readeToken();
    bot = JDABuilder.createDefault(token)
            .setActivity(Activity.playing("Loading..."))
            .addEventListeners(listener)
            .build();
    try {
      bot.awaitReady();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static String readeToken() {
    return "";
  }
}