package naraka.events;

import lombok.NoArgsConstructor;
import naraka.events.EventHandler;
import naraka.events.TypeEvent;
import naraka.settings.Settings;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import static naraka.events.TypeEvent.BAN;

@NoArgsConstructor
@Service
public class BanHandler implements EventHandler {
    private Settings settings;

    public BanHandler(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void handleEvent(TypeEvent typeEvent, MessageReceivedEvent event) {
        if (typeEvent != BAN) {
            return;
        }
        var idPerson = "372729513316057099";
        var reasonBan = "Шмоню спросить забыли";

        Guild guild = event.getJDA().getGuildById(settings.getGuildId());
        Member member = guild.retrieveMemberById(idPerson).complete();
        guild.ban(member, 0, reasonBan).queue();
    }
}
