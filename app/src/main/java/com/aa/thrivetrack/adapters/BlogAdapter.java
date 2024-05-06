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
import com.aa.thrivetrack.network.SessionStorage;

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
        int itemsPerPage = 3;
        int startIndex = position * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, articles.size());

        TextView[] blogPostViews = new TextView[]{
                holder.itemView.findViewById(R.id.blogPost1),
                holder.itemView.findViewById(R.id.blogPost2),
                holder.itemView.findViewById(R.id.blogPost3)
        };

        TextView[] likeTvViews = new TextView[]{
                holder.itemView.findViewById(R.id.likeTv1),
                holder.itemView.findViewById(R.id.likeTv2),
                holder.itemView.findViewById(R.id.likeTv3)
        };

        TextView[] commentTvViews = new TextView[]{
                holder.itemView.findViewById(R.id.commentTv1),
                holder.itemView.findViewById(R.id.commentTv2),
                holder.itemView.findViewById(R.id.commentTv3)
        };
        ImageView[] blogPhotos = new ImageView[]{
                holder.itemView.findViewById(R.id.imageView),
                holder.itemView.findViewById(R.id.imageView2),
                holder.itemView.findViewById(R.id.imageView3)
        };
        ImageView[] likeIcons = new ImageView[]{
                holder.itemView.findViewById(R.id.likeIcon1),
                holder.itemView.findViewById(R.id.likeIcon2),
                holder.itemView.findViewById(R.id.likeIcon3)
        };
        ImageView[] commentIcons = new ImageView[]{
                holder.itemView.findViewById(R.id.commentIcon1),
                holder.itemView.findViewById(R.id.commentIcon2),
                holder.itemView.findViewById(R.id.commentIcon3)
        };

        for (int i = 0; i < blogPostViews.length; i++) {
            if (startIndex + i < endIndex) {
                Article currentItem = articles.get(startIndex + i);
                String uri = "@drawable/"+currentItem.getArticle_image();
                int imageRes = context.getResources().getIdentifier(uri,null,context.getPackageName());
                @SuppressLint("UseCompatLoadingForDrawables")
                Drawable drawable = context.getResources().getDrawable(imageRes);

                blogPostViews[i].setVisibility(View.VISIBLE);
                likeTvViews[i].setVisibility(View.VISIBLE);
                commentTvViews[i].setVisibility(View.VISIBLE);
                likeIcons[i].setVisibility(View.VISIBLE);
                commentIcons[i].setVisibility(View.VISIBLE);
                blogPhotos[i].setVisibility(View.VISIBLE);

                blogPostViews[i].setText(currentItem.getArticle_title());
                blogPhotos[i].setImageDrawable(drawable);
                likeTvViews[i].setText(String.valueOf(currentItem.getArticle_likes()));
                commentTvViews[i].setText(String.valueOf(currentItem.getCommentCount()));
                blogPostViews[i].bringToFront();
                blogPostViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SessionStorage.setArticleInFocus(currentItem);
                        context.startActivity(new Intent(context, ArticleActivity.class));
                    }
                });

            } else {
                blogPostViews[i].setVisibility(View.GONE);
                likeTvViews[i].setVisibility(View.GONE);
                commentTvViews[i].setVisibility(View.GONE);
                likeIcons[i].setVisibility(View.GONE);
                commentIcons[i].setVisibility(View.GONE);
                blogPhotos[i].setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return (articles.size() + 2) / 3;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

