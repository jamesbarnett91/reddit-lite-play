package controllers;

import models.PostDetail;
import models.PostSummary;
import play.mvc.Controller;
import play.mvc.Result;
import services.SubredditService;
import views.html.postDetail;
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

  public Result view(String subreddit, String postsAfterId, Boolean showThumbs) throws ExecutionException, InterruptedException {

    List<PostSummary> posts = subredditService.getPosts(subreddit, postsAfterId).toCompletableFuture().get();

    return ok(postList.render(subreddit, postsAfterId, posts, showThumbs));
  }

  public Result viewDetail(String subreddit, String postId) throws ExecutionException, InterruptedException {
    PostDetail detail = subredditService.getPostDetail(subreddit, postId).toCompletableFuture().get();
    return ok(postDetail.render(subreddit, detail));
  }

}
