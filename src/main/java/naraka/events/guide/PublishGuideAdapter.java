package naraka.events.guide;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

@Service
public class PublishGuideAdapter {
  public void publish(MessageChannelUnion messageChannelUnion) {
    delete(messageChannelUnion);
    push(messageChannelUnion);
  }

  private void delete(MessageChannelUnion messageChannelUnion) {
    var messageHistory = MessageHistory.getHistoryFromBeginning(messageChannelUnion).complete();
    for (Message message : messageHistory.getRetrievedHistory()) {
      if (message.getAuthor().isBot() || message.getAuthor().getId().equals("329619790413037569")) {
        message.delete().queue();
      }
    }
  }

  private void push(MessageChannelUnion messageChannelUnion) {
    File[] folders = null;
    try {
      folders =  (new File(getClass().getClassLoader().getResource("data").toURI())).listFiles();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    if (folders == null) return;
    for (File folder : folders) {
      try {
        listFilesForFolder(folder, messageChannelUnion);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void listFilesForFolder(final File folder, MessageChannelUnion messageChannelUnion) throws IOException {
    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory()) {
        listFilesForFolder(fileEntry, messageChannelUnion);
      } else {

        var typeFile = fileEntry.getName();
        var typeFileIndex = typeFile.lastIndexOf('.');
        typeFile = typeFile.substring(typeFileIndex + 1);

        if (typeFile.equals("md")) {
          var message = Files.readString(fileEntry.toPath());
          if (!message.isEmpty()) {
            messageChannelUnion.sendMessage(message).queue();
          }
        }
        if (typeFile.equals("mp4")) {
          messageChannelUnion.sendFile(fileEntry).queue();
        }
      }
    }
  }
}
