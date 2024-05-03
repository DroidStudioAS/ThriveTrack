package com.aa.thrivetrack.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.models.Article;
import com.aa.thrivetrack.models.Blog;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private List<Article> articles;

    public BlogAdapter(List<Article> articles) {
        this.articles = articles;
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


        TextView blogPost1 = holder.itemView.findViewById(R.id.blogPost1);
        TextView blogPost2 = holder.itemView.findViewById(R.id.blogPost2);
        TextView blogPost3 = holder.itemView.findViewById(R.id.blogPost3);
        TextView[] tvs = new TextView[]{blogPost1,blogPost2,blogPost3};

        for (TextView tv : tvs){
            int index = startIndex + java.util.Arrays.asList(tvs).indexOf(tv);
            if(index<endIndex){
                Article currentItem = articles.get(index);
                tv.setText(currentItem.getArticle_title());
                tv.setVisibility(View.VISIBLE);
            }else{
                tv.setVisibility(View.GONE);
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

