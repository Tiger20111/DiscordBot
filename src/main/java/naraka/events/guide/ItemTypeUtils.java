package naraka.events.guide;

public class ItemTypeUtils {

  public static String getModifiedViewName(String name, ItemType type) {
    String color = getColor(type);
    return "**```" + color + "\n" + name + "\n```**";
  }

  public static String getModifiedViewType(ItemType type) {
    String color = getColor(type);
    return "**```" + color + "\n" + type + "\n```**";
  }

  private static String getColor(ItemType itemType) {
    switch (itemType) {
      case HERO -> {
        return "fix";
      }
      case MELEE -> {
        return "xl";
      }
      case DISTANT -> {
        return "md";
      }
      case NEFRIT -> {
        return "css";
      }
      default -> {
        return "ini";
      }
    }
  }
}
