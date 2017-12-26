package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.apache.commons.lang3.StringEscapeUtils;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostSummary {

  public String id;
  public String title;
  public String domain;
  public Integer score;
  public String author;
  public String thumbnail;
  public Integer ups;
  @JsonProperty("num_comments")
  public Integer commentCount;
  public String url;
  @JsonProperty("created_utc")
  public Instant createdDate;
  @JsonProperty("is_self")
  public Boolean isSelfPost;
  @JsonProperty("selftext_html")
  public String selftextHtml;

  public String getRelativePostCreatedDate() {
    // TODO localisation
    return TimeAgo.using(createdDate.toEpochMilli());
  }

  public String getSelftextHtmlUnescaped() {
    return StringEscapeUtils.unescapeHtml4(selftextHtml);
  }
}
