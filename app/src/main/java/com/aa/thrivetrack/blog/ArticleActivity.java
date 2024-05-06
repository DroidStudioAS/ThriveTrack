package com.aa.thrivetrack.blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.models.Article;
import com.aa.thrivetrack.models.Comment;
import com.aa.thrivetrack.network.SessionStorage;

public class ArticleActivity extends AppCompatActivity {

    ImageView articleImageIv;
    ImageView userRankImageIv;

    TextView articleTitleTv;
    TextView likeTv;
    TextView commentCountTv;
    TextView featuredCommentTv;
    TextView commentUsernameTv;
    TextView viewAllCommentsTrigger;

    FragmentContainerView fragmentContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
         /**Start Of Ui Intitializations**/
        articleImageIv = (ImageView)findViewById(R.id.articleImageIv);
        userRankImageIv = (ImageView)findViewById(R.id.userRankImageIv);

        articleTitleTv = (TextView) findViewById(R.id.articleTitleTv);
        likeTv = (TextView) findViewById(R.id.likeTv);
        commentCountTv = (TextView) findViewById(R.id.commentTv);
        featuredCommentTv = (TextView) findViewById(R.id.featuredCommentTv);
        commentUsernameTv = (TextView) findViewById(R.id.commentUsernameTv);
        viewAllCommentsTrigger = (TextView) findViewById(R.id.viewAllCommentsTrigger);

        fragmentContainer=(FragmentContainerView)findViewById(R.id.allCommentsContainer);
        /**End Of Ui Intitializations**/
        viewAllCommentsTrigger.setOnClickListener(displayComments());
        articleImageIv.setOnClickListener(displayComments());
        setArticle();

    }

    public void setArticle(){
        Article article = SessionStorage.getArticleInFocus();
        //header
        articleImageIv.setImageDrawable(article.getArticleDrawable(ArticleActivity.this));
        articleTitleTv.setText(article.getArticle_title());
        articleTitleTv.bringToFront();
        //comment and like section
        commentCountTv.setText(String.valueOf(article.getCommentCount()));
        likeTv.setText(String.valueOf(article.getArticle_likes()));
        //featured comment
        Comment featured = article.getTopRatedComment();
        Log.e("featured", featured.toString());
        commentUsernameTv.setText(featured.getUser_username());
        featuredCommentTv.setText(featured.getComment_text());
    }

    public View.OnClickListener displayComments(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentContainer.getVisibility()==View.VISIBLE){
                    Animation fadeOut = AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.fade_out);
                    fragmentContainer.setVisibility(View.GONE);
                    fragmentContainer.startAnimation(fadeOut);
                    viewAllCommentsTrigger.setText("View All Comments");
                    return;
                }
                Animation fadeIn = AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.fade_in);
                fragmentContainer.setVisibility(View.VISIBLE);
                fragmentContainer.startAnimation(fadeIn);
                fragmentContainer.bringToFront();
                viewAllCommentsTrigger.bringToFront();
                viewAllCommentsTrigger.setText("Hide All Comments");
            }
        };
    }
}