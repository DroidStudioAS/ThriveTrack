package com.aa.thrivetrack.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnAllCommentsClicked;
import com.aa.thrivetrack.models.Article;
import com.aa.thrivetrack.models.Comment;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private List<Article> articles;
    private Context context;
    private OnAllCommentsClicked onAllCommentsClicked;

    public BlogAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context=context;
        onAllCommentsClicked = (OnAllCommentsClicked) context;
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
        FragmentContainerView commentContainer = holder.itemView.findViewById(R.id.commentContainer);

        // Bind data to the views
        TextView blogTitleholder = holder.itemView.findViewById(R.id.blogPost1);
        TextView likeTv = holder.itemView.findViewById(R.id.likeTv1);
        TextView commentTv = holder.itemView.findViewById(R.id.commentTv1);
        TextView featuredCommentTv = holder.itemView.findViewById(R.id.featuredComment);
        TextView featuredUsernameTv = holder.itemView.findViewById(R.id.featuredUsernameTv);
        TextView viewAllCommentsTrigger = holder.itemView.findViewById(R.id.viewAllComments);
        ImageView blogPhoto = holder.itemView.findViewById(R.id.imageView);

        blogTitleholder.setText(article.getArticle_title());
        blogPhoto.setImageDrawable(article.getArticleDrawable(context));
        commentTv.setText(String.valueOf(article.getCommentCount()));
        likeTv.setText(String.valueOf(article.getArticle_likes()));
        blogTitleholder.bringToFront();
        Comment topRated = article.getTopRatedComment();
        featuredCommentTv.setText(topRated.getComment_text());
        featuredUsernameTv.setText(topRated.getUser_username());

        viewAllCommentsTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionStorage.setArticleInFocus(article);
                Log.i("article", SessionStorage.getArticleInFocus().toString());

                onAllCommentsClicked.onAllCommentsClicked();

            }

        });
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

