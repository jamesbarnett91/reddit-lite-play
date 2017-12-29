package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.apache.commons.lang3.StringEscapeUtils;
import play.libs.Json;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

  public String author;
  public Integer score;
  @JsonProperty("is_submitter")
  public Boolean isSubmitter;
  @JsonProperty("created_utc")
  public Instant createdDate;
  @JsonProperty("author_flair_text")
  public String flairText;
  public String body;
  @JsonProperty("body_html")
  public String bodyHtml;
  public Integer depth;


  @JsonIgnore
  public List<Comment> replies = new ArrayList<>();

  public static Comment parse(JsonNode json) {
    Comment topLevelComment = Json.fromJson(json, Comment.class);

    List<JsonNode> childComments = json.path("replies").path("data").path("children").findValues("data");
    for(JsonNode childComment : childComments) {
      topLevelComment.replies.add(parse(childComment));
    }

    return topLevelComment;
  }

  public String getBodyHtmlUnescaped() {
    return StringEscapeUtils.unescapeHtml4(bodyHtml);
  }

  public String getRelativeCreatedDate() {
    // TODO localisation
    return TimeAgo.using(createdDate.toEpochMilli());
  }

}
