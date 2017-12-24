package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.postList;

public class SubredditController extends Controller {

  public Result view(String subreddit) {
    return ok(postList.render(subreddit));
  }

}
