package com.aa.thrivetrack.models;

import com.aa.thrivetrack.network.SessionStorage;

public class Article {
    int article_id;
    String article_title;
    String article_date;
    String article_image;
    String article_text;
    int article_likes;

    public Article() {
    }

    public Article(int article_id, String article_title, String article_date, String article_image, String article_text, int article_likes) {
        this.article_id = article_id;
        this.article_title = article_title;
        this.article_date = article_date;
        this.article_image = article_image;
        this.article_text = article_text;
        this.article_likes=article_likes;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_date() {
        return article_date;
    }

    public void setArticle_date(String article_date) {
        this.article_date = article_date;
    }

    public String getArticle_image() {
        return article_image;
    }

    public void setArticle_image(String article_image) {
        this.article_image = article_image;
    }

    public String getArticle_text() {
        return article_text;
    }

    public void setArticle_text(String article_text) {
        this.article_text = article_text;
    }

    public int getArticle_likes() {
        return article_likes;
    }

    public void setArticle_likes(int article_likes) {
        this.article_likes = article_likes;
    }

    public int getCommentCount(){
        int commentCount = 0;
        for(Comment comment : SessionStorage.getBlog().getComments()){
            if(comment.article_id==this.article_id){
                commentCount++;
            }
        }
        return commentCount;
    }
}
