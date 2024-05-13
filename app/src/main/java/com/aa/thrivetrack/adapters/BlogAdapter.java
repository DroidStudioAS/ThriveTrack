package com.aa.thrivetrack.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.aa.thrivetrack.helpers.AnimationHelper;
import com.aa.thrivetrack.helpers.StreakHelper;
import com.aa.thrivetrack.models.Article;
import com.aa.thrivetrack.models.Comment;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;
import com.aa.thrivetrack.validation.ToastFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private List<Article> articles;
    private Context context;
    private OnAllCommentsClicked onAllCommentsClicked;

    private static final String[] PATH_TO_LIKE_ARTICLE = new String[]{"edit","patch","article-likes"};

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
        ImageView badgeIv = holder.itemView.findViewById(R.id.badgeIv);
        ImageView likeIv = holder.itemView.findViewById(R.id.likeIcon1);

        blogTitleholder.setText(article.getArticle_title());
        blogTitleholder.setTextColor(Color.parseColor("#d9d9d9"));
        blogTitleholder.setTypeface(null, Typeface.BOLD);
        blogPhoto.setImageDrawable(context.getDrawable(R.drawable.primer));

        commentTv.setText(String.valueOf(article.getCommentCount()));
        likeTv.setText(String.valueOf(article.getArticle_likes()));
        blogTitleholder.bringToFront();
        Comment topRated = article.getTopRatedComment();
        featuredCommentTv.setText(topRated.getComment_text());
        featuredUsernameTv.setText(topRated.getUser_username());
        badgeIv.setImageDrawable(context.getDrawable(R.drawable.diamond));

        viewAllCommentsTrigger.setText("View All " + String.valueOf(article.getCommentCount()) + " Comments");

        likeIv.setOnClickListener(likeArticle(article));
        viewAllCommentsTrigger.setOnClickListener(showComments(article));
        blogPhoto.setOnClickListener(showComments(article));
    }

    public View.OnClickListener likeArticle(Article article){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean articleLiked = context.getSharedPreferences(SessionStorage.getUsername(), Context.MODE_PRIVATE)
                        .getBoolean(article.getArticle_title(), false);
                SharedPreferences.Editor editor = context.getSharedPreferences(SessionStorage.getUsername(), Context.MODE_PRIVATE)
                            .edit();
                String articleId = String.valueOf(article.getArticle_id());
                String articleLikes = articleLiked ?
                        String.valueOf(article.getArticle_likes()-1) :
                        String.valueOf(article.getArticle_likes()+1);
                Map<String, String> params = new HashMap<>();
                params.put("article-id", articleId);
                params.put("article-likes", articleLikes);

                NetworkHelper.callPatch(PATH_TO_LIKE_ARTICLE, params, 0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    String toastMessage = articleLiked?"Like Deleted":"Article Liked";
                    ToastFactory.showToast(context, toastMessage);
                    editor.putBoolean(article.getArticle_title(), !articleLiked).commit();
                    article.setArticle_likes(Integer.parseInt(articleLikes));
                    notifyDataSetChanged();
                    AnimationHelper.likeAnimation(v);
                }else{
                    ToastFactory.showToast(context, "Oops... Something went wrong");
                }
                SessionStorage.resetServerResponse();

            }
        };
    }
    public View.OnClickListener showComments(Article article){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionStorage.setArticleInFocus(article);
                onAllCommentsClicked.onAllCommentsClicked();
            }
        };
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

