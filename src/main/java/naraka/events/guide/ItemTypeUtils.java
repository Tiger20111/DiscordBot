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
        return "Fix";
      }
      case MELEE -> {
        return "Xl";
      }
      case DISTANT -> {
        return "Md";
      }
      case NEFRIT -> {
        return "Css";
      }
      default -> {
        return "Ini";
      }
    }
  }
}
