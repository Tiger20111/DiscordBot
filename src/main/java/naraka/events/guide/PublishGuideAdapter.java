package naraka.events.guide;

import naraka.settings.Settings;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublishGuideAdapter {
  @Autowired
  private Settings settings;

  public void publish(TextChannel textChannel) {
    delete(textChannel);
    push(textChannel);
  }

  private void delete(TextChannel textChannel) {
    List<Message> deleteMessages = new ArrayList<>();
    var messageHistory = MessageHistory.getHistoryFromBeginning(textChannel).complete();
    for (Message message : messageHistory.getRetrievedHistory()) {
      if (settings.isAllowDeleteMessage(message)) {
        message.delete().queue();
        deleteMessages.add(message);
      }
    }
    if (deleteMessages.isEmpty()) {
      return;
    }
    textChannel.deleteMessages(deleteMessages).queue();
  }

  private void push(TextChannel textChannel) {
    File[] folders = null;
    try {
      folders =  (new File(getClass().getClassLoader().getResource("data").toURI())).listFiles();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    if (folders == null) return;
    for (File folder : folders) {
      try {
        listFilesForFolder(folder, textChannel);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void listFilesForFolder(final File folder, TextChannel textChannel) throws IOException {
    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory()) {
        listFilesForFolder(fileEntry, textChannel);
      } else {

        var typeFile = fileEntry.getName();
        var typeFileIndex = typeFile.lastIndexOf('.');
        typeFile = typeFile.substring(typeFileIndex + 1);

        if (typeFile.equals("md")) {
          var message = Files.readString(fileEntry.toPath());
          if (!message.isEmpty()) {
            textChannel.sendMessage(message).queue();
          }
        }
        if (typeFile.equals("mp4")) {
          textChannel.sendFile(fileEntry).queue();
        }
      }
    }
  }
}
