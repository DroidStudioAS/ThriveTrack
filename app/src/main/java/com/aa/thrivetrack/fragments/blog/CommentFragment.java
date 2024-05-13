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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.adapters.CommentAdapter;
import com.aa.thrivetrack.callback.OnAllCommentsClicked;
import com.aa.thrivetrack.callback.OnArticleClicked;
import com.aa.thrivetrack.models.Comment;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class CommentFragment extends Fragment implements OnArticleClicked {

    ConstraintLayout commentContainer;

    ImageView articleIv;
    ImageView sendCommentTrigger;

    TextView articleTv;
    TextView hideCommentsTrigger;

    EditText commentInput;

    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    OnAllCommentsClicked onAllCommentsClicked;

    private final String[] PATH_TO_WRITE_COMMENT = new String[]{"write","comment"};

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
        sendCommentTrigger=(ImageView)view.findViewById(R.id.sendCommentTrigger);
        commentInput=(EditText)view.findViewById(R.id.commentInput);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        /**End Of Ui Initializations**/
        onAllCommentsClicked=(OnAllCommentsClicked)container.getContext();

        sendCommentTrigger.setOnClickListener(sendComment());
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
            articleIv.setImageDrawable(SessionStorage.getArticleInFocus().getArticleDrawable(getContext()));
            // articleIv.setImageDrawable(SessionStorage.getArticleInFocus().getArticleDrawable(getActivity().getApplicationContext()));
            articleTv.setText(SessionStorage.getArticleInFocus().getArticle_title());
            commentAdapter = new CommentAdapter(SessionStorage.getArticleInFocus().getPostComments(), getContext());
            recyclerView.setAdapter(commentAdapter);
        }
    }

    public View.OnClickListener sendComment(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentInput.getText().toString().trim();
                if(comment.equals("")){
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("username", SessionStorage.getUsername());
                params.put("comment-text", comment);
                params.put("user-rank", SessionStorage.getUserData().getUser().getUser_rank());
                params.put("article-id", String.valueOf(SessionStorage.getArticleInFocus().getArticle_id()));
                NetworkHelper.callPost(PATH_TO_WRITE_COMMENT, params, 0);
                NetworkHelper.waitForReply();
                Log.i("server response", SessionStorage.getServerResponse());
                if(SessionStorage.getServerResponse().equals("true")){
                    Toast.makeText(getContext(), "Comment Added", Toast.LENGTH_SHORT).show();
                    Comment newComment = new Comment(SessionStorage.getBlog().getNextCommentId(),
                            SessionStorage.getArticleInFocus().getArticle_id(),
                            0, comment,
                            SessionStorage.getUsername(),
                            SessionStorage.getUserData().getUser().getUser_rank());
                    //add comment to frontend list
                    SessionStorage.getArticleInFocus()
                            .getPostComments()
                            .add(newComment);

                    //refresh view
                    commentAdapter.addToComments(newComment);
                    recyclerView.setAdapter(commentAdapter);

                }
                SessionStorage.resetServerResponse();
            }
        };

    }
    @Override
    public void onArticleClick() {
        Log.i("callback active", "on article clicked");
    }
}