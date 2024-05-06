package com.aa.thrivetrack.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aa.thrivetrack.R;

public class ArticleActivity extends AppCompatActivity {

    ImageView articleImageIv;
    ImageView userRankImageIv;

    TextView articleTitleTv;
    TextView likeTv;
    TextView commentCountTv;
    TextView featuredCommentTv;
    TextView commentUsernameTv;
    TextView viewAllCommentsTrigger;
    TextView articleTextTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        articleImageIv = (ImageView)findViewById(R.id.articleImageIv);
        userRankImageIv = (ImageView)findViewById(R.id.userRankImageIv);

        articleTitleTv = (TextView) findViewById(R.id.articleTitleTv);
        likeTv = (TextView) findViewById(R.id.likeTv);
        commentCountTv = (TextView) findViewById(R.id.commentTv);
        featuredCommentTv = (TextView) findViewById(R.id.featuredCommentTv);
        commentUsernameTv = (TextView) findViewById(R.id.commentUsernameTv);
        viewAllCommentsTrigger = (TextView) findViewById(R.id.viewAllCommentsTrigger);
        articleTextTv = (TextView) findViewById(R.id.articleTextTv);


    }
}