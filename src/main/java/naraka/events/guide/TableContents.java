package naraka.events.guide;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static java.util.List.of;
import static naraka.events.guide.ItemTypeUtils.getModifiedViewName;
import static naraka.events.guide.ItemTypeUtils.getModifiedViewType;

public class TableContents {
  private HashMap<ItemType, List<Item>> mapItems;

  TableContents(Stream<Item> items) {
    mapItems = new HashMap<>();
    for (ItemType itemType : ItemType.values()) {
      mapItems.put(itemType, of());
    }
    items.forEach(v -> mapItems.get(v.getType()).add(v));
  }

  public void sendChannel(TextChannel textChannel) {
    mapItems.forEach((k, v) -> sendLinkItem(k, v, textChannel));
  }

  private void sendLinkItem(ItemType itemType, List<Item> items, TextChannel textChannel) {
    if (items.isEmpty()) return;
    textChannel.sendMessage(getModifiedViewType(itemType)).queue();
    for (Item item:
         items) {
      var message = generateMessageItem(item);
      textChannel.sendMessage(message).queue();
    }
  }

  private Message generateMessageItem(Item item) {
    MessageBuilder messageBuilder = new MessageBuilder();
    EmbedBuilder eb = new EmbedBuilder();
    eb.setTitle(item.getName(), item.getLink());
    messageBuilder.setEmbeds(eb.build());
    return messageBuilder.build();
  }
}
