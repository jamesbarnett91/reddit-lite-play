package filters;

import com.google.inject.Inject;
import play.http.DefaultHttpFilters;

public class Filters extends DefaultHttpFilters {

  @Inject
  public Filters(RequestLoggingFilter logging) {
    super(logging);
  }
}
