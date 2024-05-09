package com.aa.thrivetrack.models;

import java.util.Random;

public class Comment {
    int comment_id;
    int article_id;
    int comment_likes;

    String comment_text;
    String user_username;
    String user_rank;

    private static final Comment[] RANDOM_COMMENTS = new Comment[]{
      new Comment(-1, -1, 2456, "This is the best app ever it helped me so much!", "Smiljanic19A", "diamond"),
      new Comment(-1, -1, 2568, "Very Interesting!", "GGG", "diamond"),
      new Comment(-1, -1, 3212, "Changed My Life!", "BronzeBomber", "diamond"),
      new Comment(-1, -1, 4019, "OMG AMAZING!!", "HelloKity56", "diamond")
    };



    public Comment() {
    }

    public Comment(int comment_id, int article_id, int comment_likes, String comment_text, String user_username, String user_rank) {
        this.comment_id = comment_id;
        this.article_id = article_id;
        this.comment_likes = comment_likes;
        this.comment_text = comment_text;
        this.user_username = user_username;
        this.user_rank = user_rank;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public int getComment_likes() {
        return comment_likes;
    }

    public void setComment_likes(int comment_likes) {
        this.comment_likes = comment_likes;
    }

    public static Comment returnRandomComment(){
        int min = 0;
        int max = RANDOM_COMMENTS.length - 1;
        Random random = new Random();

        int randomNumber = random.nextInt(max+1);
        return RANDOM_COMMENTS[randomNumber];
    }



    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", article_id=" + article_id +
                ", comment_likes=" + comment_likes +
                ", comment_text='" + comment_text + '\'' +
                ", user_username='" + user_username + '\'' +
                ", user_rank='" + user_rank + '\'' +
                '}';
    }

}
