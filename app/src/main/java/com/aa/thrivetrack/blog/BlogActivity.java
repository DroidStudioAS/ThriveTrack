package com.aa.thrivetrack.blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.adapters.BlogAdapter;
import com.aa.thrivetrack.callback.OnAllCommentsClicked;
import com.aa.thrivetrack.callback.OnArticleClicked;
import com.aa.thrivetrack.fragments.blog.CommentFragment;
import com.aa.thrivetrack.network.SessionStorage;

public class BlogActivity extends AppCompatActivity implements OnAllCommentsClicked {

    RecyclerView blogContainer;
    BlogAdapter blogAdapter;

    FragmentContainerView commentContainer;

    OnArticleClicked onArticleClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        /**Start Of UI Initializations**/
        commentContainer=(FragmentContainerView) findViewById(R.id.commentContainer);
        blogContainer=(RecyclerView) findViewById(R.id.blogContainer);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        blogContainer.setLayoutManager(layoutManager);

        blogAdapter=new BlogAdapter(SessionStorage.getBlog().getArticles(), BlogActivity.this);
        blogContainer.setAdapter(blogAdapter);
        /**End Of UI Initializations**/



    }



    @Override
    public void onAllCommentsClicked() {
        Log.i("Callback active", "OnAllCommentsClicked");
        Log.i("Article In Focus", SessionStorage.getArticleInFocus().toString());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.commentContainer, new CommentFragment())
                .commit();

        if(commentContainer.getVisibility()==View.VISIBLE){
            Animation fadeOut = AnimationUtils.loadAnimation(BlogActivity.this, R.anim.fade_out);
            commentContainer.startAnimation(fadeOut);
            commentContainer.setVisibility(View.GONE);
            return;
        }
        Animation fadeIn = AnimationUtils.loadAnimation(BlogActivity.this, R.anim.fade_in);
        commentContainer.setVisibility(View.VISIBLE);
        commentContainer.startAnimation(fadeIn);
        commentContainer.bringToFront();
    }


}
