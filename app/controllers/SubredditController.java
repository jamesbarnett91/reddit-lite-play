package controllers;

import models.PostDetail;
import models.PostSummary;
import play.mvc.Controller;
import play.mvc.Result;
import services.SubredditService;
import views.html.landingPage;
import views.html.postDetail;
import views.html.postList;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class SubredditController extends Controller {

  private final SubredditService subredditService;

  @Inject
  public SubredditController(SubredditService subredditService) {
    this.subredditService = subredditService;
  }

  public Result view(String subreddit, String postsAfterId, Boolean showThumbs) {

    List<PostSummary> posts = fetchSynchronous(subredditService.getPosts(subreddit, postsAfterId));

    return ok(postList.render(subreddit, postsAfterId, posts, showThumbs));
  }

  public Result viewDetail(String subreddit, String postId) {
    PostDetail detail = fetchSynchronous(subredditService.getPostDetail(subreddit, postId));
    return ok(postDetail.render(subreddit, detail));
  }

  public Result landingPage() {
    return ok(landingPage.render());
  }

  private <T> T fetchSynchronous(CompletionStage<T> completionStage) {
    try {
      // Block until response is received (when async behaviour is not needed)
      return completionStage.toCompletableFuture().get();
    } catch (InterruptedException | ExecutionException ex) {
      throw new RuntimeException("Error executing API response future.", ex);
    }
  }

}
