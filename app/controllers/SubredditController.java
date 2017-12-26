package controllers;

import models.PostSummary;
import play.mvc.Controller;
import play.mvc.Result;
import services.SubredditService;
import views.html.postList;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SubredditController extends Controller {

  private final SubredditService subredditService;

  @Inject
  public SubredditController(SubredditService subredditService) {
    this.subredditService = subredditService;
  }

  public Result view(String subreddit) throws ExecutionException, InterruptedException {

    List<PostSummary> posts = subredditService.getPosts(subreddit).toCompletableFuture().get();

    return ok(postList.render(subreddit, posts));
  }

}
