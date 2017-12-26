package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import models.PostSummary;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import java.util.List;
import java.util.concurrent.CompletionStage;
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
      .handleAsync(this::handleResponse, httpExecutionContext.current());
  }

  private List<PostSummary> handleResponse(WSResponse response, Throwable th) {
    if(th != null) {
      throw new RuntimeException("Web service request failed", th);
    }
    else if(response.getStatus() != 200) {
      throw new RuntimeException("Non 200 response received. Response code was: " + response.getStatus());
    }
    else {
      List<JsonNode> postList = response.asJson().path("data").path("children").findValues("data");
      return postList.stream()
        .map(post -> Json.fromJson(post, PostSummary.class))
        .collect(Collectors.toList());

    }
  }

}
