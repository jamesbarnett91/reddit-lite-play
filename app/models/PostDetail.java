package models;

import java.util.List;

public class PostDetail {

  public PostSummary postSummary;
  public List<Comment> comments;

  public PostDetail(PostSummary postSummary, List<Comment> comments){
    this.postSummary = postSummary;
    this.comments = comments;
  }

}
