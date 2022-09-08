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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.Gson;

import static java.util.List.of;
import static naraka.events.guide.Item.isItem;
import static naraka.events.guide.ItemType.HERO;
import static naraka.events.guide.ItemTypeUtils.getModifiedViewName;

@Service
public class PublishGuideAdapter {
  @Autowired
  private Settings settings;

  public void publish(TextChannel textChannel) {
    //deleteGroup(textChannel);
    //addGuides(textChannel);
    //addTableOfContents(textChannel);
  }

  private void deleteGroup(TextChannel textChannel) {
    List<Message> deleteMessages = new ArrayList<>();
    var messageHistory = MessageHistory.getHistoryFromBeginning(textChannel).complete();
    for (Message message : messageHistory.getRetrievedHistory()) {
      if (settings.isAllowDeleteMessage(message)) {
        deleteMessages.add(message);
        //message.delete().queue();
      }
    }
    textChannel.deleteMessages(deleteMessages).queue();
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

        if (typeFile.equals("md") || typeFile.equals("json")) {
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

    var history = new MessageHistory(textChannel);
    var lastMessages = history.retrievePast(100).complete();

    var allMessages = messageHistory.getRetrievedHistory();
    List<Message> messages = new ArrayList<>();
    messages.addAll(lastMessages);
    messages.addAll(allMessages);
    var items = messages.stream()
            .filter(Item::isItem)
            .map(this::doOnMessage)
            .collect(Collectors.toList());

    var tableContents = new TableContents(items);
    tableContents.sendChannel(textChannel);
  }

  private Item doOnMessage(Message message) {
    var item = new Gson().fromJson(message.getContentRaw(), Item.class);
    item.setLink(generateMessageLink(message.getId()));
    changeMessage(message, item);
    return item;
  }

  private void changeMessage(Message message, Item item) {
    MessageBuilder messageBuilder = new MessageBuilder();
    messageBuilder.append(getModifiedViewName(item.getName(), item.getType()));
    message.editMessage(messageBuilder.build()).queue();
  }

  private String generateMessageLink(String messageId) {
    return "https://discord.com/channels/" +
            settings.getGuildId() + "/" +
            settings.getGuideChannelId() + "/" +
            messageId;
  }
}
