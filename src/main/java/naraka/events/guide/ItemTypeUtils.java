package naraka.events.guide;

public class ItemTypeUtils {

  public static String getModifiedViewName(String name, ItemType type) {
    String color = getColor(type);
    return "**[" + color + "]" + name + "[/" + color + "]**";
  }

  public static String getModifiedViewType(ItemType type) {
    String color = getColor(type);
    return "**[" + color + "]" + type + "[/" + color + "]**";
  }

  private static String getColor(ItemType itemType) {
    switch (itemType) {
      case HERO -> {
        return "blue";
      }
      case MELEE -> {
        return "red";
      }
      default -> {
        return "black";
      }
    }
  }
}
