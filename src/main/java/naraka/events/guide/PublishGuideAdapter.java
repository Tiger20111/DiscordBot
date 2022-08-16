package naraka.events.guide;

import naraka.settings.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;
import static naraka.events.guide.Item.isItem;
import static naraka.events.guide.ItemType.HERO;
import static naraka.events.guide.ItemTypeUtils.getModifiedViewName;

@Service
public class PublishGuideAdapter {
  @Autowired
  private Settings settings;

  public void publish(TextChannel textChannel) {
    delete(textChannel);
    addGuides(textChannel);
    addTableOfContents(textChannel);
  }

  private void delete(TextChannel textChannel) {
    var messageHistory = MessageHistory.getHistoryFromBeginning(textChannel).complete();
    for (Message message : messageHistory.getRetrievedHistory()) {
      if (settings.isAllowDeleteMessage(message)) {
        message.delete().queue();
      }
    }
  }

  private void addGuides(TextChannel textChannel) {
    walkAndSendGuides(textChannel);
  }

  private void walkAndSendGuides(TextChannel textChannel) {
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

        if (typeFile.equals("md") || typeFile.equals("yaml")) {
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

  private void addTableOfContents(TextChannel textChannel) {
    var messageHistory = MessageHistory.getHistoryFromBeginning(textChannel).complete();

    var items = messageHistory.getRetrievedHistory()
            .stream()
            .filter(Item::isItem)
            .map(this::doOnMessage);

    var tableContents = new TableContents(items);
    tableContents.sendChannel(textChannel);
  }

  private Item generateItem(String body, String messageId) {
    var item = new Item("", HERO);
    item.setLink(generateMessageLink(messageId));
    item.modifyName();
    return item;
  }

  private Item doOnMessage(Message message) {
    var item = generateItem(message.getContentRaw(), message.getId());
    changeMessage(message, item);
    return item;
  }

  private void changeMessage(Message message, Item item) {
    MessageBuilder messageBuilder = new MessageBuilder();
    messageBuilder.append(getModifiedViewName(item.getName(), item.getType()));
    message.editMessage(messageBuilder.build()).queue();
  }

  private String generateMessageLink(String messageId) {
    return "https://discord/" +
            settings.getGuildId() + "/" +
            settings.getGuideChannelId() + "/" +
            messageId;
  }
}
