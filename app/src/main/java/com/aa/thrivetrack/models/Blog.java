package com.aa.thrivetrack.models;

import java.util.ArrayList;

public class Blog {
    ArrayList<Article> articles;
    ArrayList<Comment> comments;

    public Blog() {
    }

    public Blog(ArrayList<Article> articles, ArrayList<Comment> comments) {
        this.articles = articles;
        this.comments = comments;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "articles=" + articles +
                ", comments=" + comments +
                '}';
    }
}
