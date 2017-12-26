package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostSummary {

  public String name;
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

  public String getRelativePostCreatedDate() {
    // TODO localisation
    return TimeAgo.using(createdDate.toEpochMilli());
  }

}
