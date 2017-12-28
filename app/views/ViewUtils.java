package views;

import java.util.Collection;

public class ViewUtils {

  public static String pluralise(Collection collection, String singular, String plural) {
    return pluralise(collection.size(), singular, plural);
  }

  public static String pluralise(Collection collection, String singular) {
    return pluralise(collection.size(), singular, singular + "s");
  }

  public static String pluralise(Integer count, String singular) {
    return pluralise(count, singular, singular + "s");
  }

  public static String pluralise(Integer count, String singular, String plural) {
    if(count == 1) {
      return count + " " + singular;
    }
    else {
      return count + " " + plural;
    }
  }


}