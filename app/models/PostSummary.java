package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

}
