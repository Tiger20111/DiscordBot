package naraka.events.guide;

import net.dv8tion.jda.api.entities.Message;

public class Item {
  private final String name;
  private final ItemType type;
  private String link;

  Item(String name, ItemType type) {
    this.name = name;
    this.type = type;
  }

  public ItemType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public static boolean isItem(Message message) {
    return  (message.getContentRaw().toLowerCase().contains("type"));
  }
}
