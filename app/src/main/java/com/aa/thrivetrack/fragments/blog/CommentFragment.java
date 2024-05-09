package com.aa.thrivetrack.fragments.blog;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.adapters.CommentAdapter;
import com.aa.thrivetrack.callback.OnAllCommentsClicked;
import com.aa.thrivetrack.callback.OnArticleClicked;
import com.aa.thrivetrack.network.SessionStorage;

public class CommentFragment extends Fragment implements OnArticleClicked {

    ConstraintLayout commentContainer;

    ImageView articleIv;
    TextView articleTv;
    TextView hideCommentsTrigger;

    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    OnAllCommentsClicked onAllCommentsClicked;

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        /**Start Of Ui Initializations**/
        commentContainer=(ConstraintLayout)view.findViewById(R.id.commentsContainer);
        articleIv=(ImageView) view.findViewById(R.id.articleIv);
        articleTv=(TextView) view.findViewById(R.id.articleTv);
        recyclerView=(RecyclerView)view.findViewById(R.id.commentRv);
        hideCommentsTrigger=(TextView)view.findViewById(R.id.hideComments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        /**End Of Ui Initializations**/
        onAllCommentsClicked=(OnAllCommentsClicked)container.getContext();
        hideCommentsTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAllCommentsClicked.onAllCommentsClicked();
            }
        });
        setUi();
        return view;
    }

    public void setUi(){
        if(SessionStorage.getArticleInFocus()!=null){
            articleIv.setImageDrawable(getContext().getDrawable(R.drawable.primer));
            // articleIv.setImageDrawable(SessionStorage.getArticleInFocus().getArticleDrawable(getActivity().getApplicationContext()));
            articleTv.setText(SessionStorage.getArticleInFocus().getArticle_title());
            commentAdapter = new CommentAdapter(SessionStorage.getArticleInFocus().getPostComments(), getContext());
            recyclerView.setAdapter(commentAdapter);
        }
    }

    @Override
    public void onArticleClick() {
        Log.i("callback active", "on article clicked");
    }
}