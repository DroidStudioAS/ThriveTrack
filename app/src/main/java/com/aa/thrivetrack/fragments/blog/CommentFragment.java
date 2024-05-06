package com.aa.thrivetrack.fragments.blog;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.network.SessionStorage;

public class CommentFragment extends Fragment {

    ConstraintLayout commentContainer;

    ImageView articleIv;
    TextView articleTv;

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
        /**End Of Ui Initializations**/
        setUi();
        return view;
    }

    public void setUi(){
        articleIv.setImageDrawable(SessionStorage.getArticleInFocus().getArticleDrawable(getActivity().getApplicationContext()));
        articleTv.setText(SessionStorage.getArticleInFocus().getArticle_title());
    }
}