package com.aa.thrivetrack.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.blog.ArticleActivity;
import com.aa.thrivetrack.models.Article;
import com.aa.thrivetrack.models.Blog;
import com.aa.thrivetrack.models.Comment;
import com.aa.thrivetrack.network.SessionStorage;

import org.w3c.dom.Text;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private List<Article> articles;
    private Context context;

    public BlogAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context=context;
    }

    @NonNull
    @Override
    public BlogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.blog_layout,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BlogAdapter.ViewHolder holder, int position) {
        // Get the article at the given position
        Article article = articles.get(position);

        // Bind data to the views
        TextView blogTitleholder = holder.itemView.findViewById(R.id.blogPost1);
        TextView likeTv = holder.itemView.findViewById(R.id.likeTv1);
        TextView commentTv = holder.itemView.findViewById(R.id.commentTv1);
        TextView featuredCommentTv = holder.itemView.findViewById(R.id.featuredComment);
        TextView featuredUsernameTv = holder.itemView.findViewById(R.id.featuredUsernameTv);
        ImageView blogPhoto = holder.itemView.findViewById(R.id.imageView);

        blogTitleholder.setText(article.getArticle_title());
        blogPhoto.setImageDrawable(article.getArticleDrawable(context));
        commentTv.setText(String.valueOf(article.getCommentCount()));
        likeTv.setText(String.valueOf(article.getArticle_likes()));
        blogTitleholder.bringToFront();
        Comment topRated = article.getTopRatedComment();
        featuredCommentTv.setText(topRated.getComment_text());
        featuredUsernameTv.setText(topRated.getUser_username());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

