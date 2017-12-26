package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import models.Comment;
import models.PostDetail;
import models.PostSummary;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class SubredditService {

  private static final String REDDIT_API_ROOT_URL = "https://reddit.com/r/";

  private final HttpExecutionContext httpExecutionContext;
  private final WSClient client;
  private final int apiConnectionTimeout;

  @Inject
  public SubredditService(HttpExecutionContext httpExecutionContext, WSClient client, @Named("apiConnectionTimeout") int apiConnectionTimeout) {
    this.httpExecutionContext = httpExecutionContext;
    this.client = client;
    this.apiConnectionTimeout = apiConnectionTimeout;
  }

  public CompletionStage<List<PostSummary>> getPosts(String subreddit) {
    return client.url(REDDIT_API_ROOT_URL + subreddit + "/.json")
      .setRequestTimeout(apiConnectionTimeout)
      .get()
      .handleAsync((r, t) -> handleResponse(r, t, (json) -> {
        List<JsonNode> postList = json.path("data").path("children").findValues("data");
        return postList.stream()
          .map(post -> Json.fromJson(post, PostSummary.class))
          .collect(Collectors.toList());
      }), httpExecutionContext.current());
  }

  public CompletionStage<PostDetail> getPostDetail(String subreddit, String postId) {
    return client.url(REDDIT_API_ROOT_URL + subreddit + "/comments/" + postId + "/.json")
      .setRequestTimeout(apiConnectionTimeout)
      .get()
      .handleAsync((r, t) -> handleResponse(r, t, (json) -> {

        JsonNode postSummaryJson = json.findValues("data").get(0).path("children").findValues("data").get(0);
        PostSummary postSummary = Json.fromJson(postSummaryJson, PostSummary.class);

        List<JsonNode> commentsJson = json.findValues("data").get(1).path("children").findValues("data");
        List<Comment> comments =  commentsJson.stream()
          .map(Comment::parse)
          .collect(Collectors.toList());

        return new PostDetail(postSummary, comments);

      }), httpExecutionContext.current());
  }

  private <T> T handleResponse(WSResponse response, Throwable th, Function<JsonNode, T> responseMapper) {
    if(th != null) {
      throw new RuntimeException("API request failed", th);
    }
    else if(response.getStatus() != 200) {
      throw new RuntimeException("Non 200 API response received. Response code was: " + response.getStatus());
    }
    else {
      return responseMapper.apply(response.asJson());
    }
  }

}
