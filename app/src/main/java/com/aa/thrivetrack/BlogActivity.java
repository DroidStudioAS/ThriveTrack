package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aa.thrivetrack.adapters.BlogAdapter;
import com.aa.thrivetrack.network.SessionStorage;

public class BlogActivity extends AppCompatActivity {

    RecyclerView blogContainer;
    BlogAdapter blogAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        /**Start Of UI Initializations**/
        blogContainer=(RecyclerView) findViewById(R.id.blogContainer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        blogContainer.setLayoutManager(layoutManager);

        blogAdapter=new BlogAdapter(SessionStorage.getBlog().getArticles());
        blogContainer.setAdapter(blogAdapter);
        /**End Of UI Initializations**/

    }
}