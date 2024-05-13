package com.aa.thrivetrack.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.helpers.StreakHelper;
import com.aa.thrivetrack.models.Comment;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;
import com.aa.thrivetrack.validation.ToastFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<Comment> comments;
    private Context context;
    private int itemsPerPage = 5;

    private static final String[] PATH_T0_LIKE_COMMENT = new String[]{"edit","patch","comment-likes"};

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        int startIndex = position * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, comments.size());

        for (int i = 0; i < holder.rankIvs.length; i++) {
            if (startIndex + i < endIndex) {
                Comment comment = comments.get(startIndex + i);
                holder.rankIvs[i].setVisibility(View.VISIBLE);
                holder.likeIvs[i].setVisibility(View.VISIBLE);
                holder.usernameTvs[i].setVisibility(View.VISIBLE);
                holder.commentTvs[i].setVisibility(View.VISIBLE);
                holder.likeCountTvs[i].setVisibility(View.VISIBLE);

                holder.usernameTvs[i].setText(comment.getUser_username());
                holder.commentTvs[i].setText(comment.getComment_text());
                holder.likeCountTvs[i].setText(String.valueOf(comment.getComment_likes()));
                holder.rankIvs[i].setImageDrawable(StreakHelper.getUserBadge(context, comment.getUser_rank()));

                holder.likeIvs[i].setOnClickListener(likeComment(comment));
            } else {
                holder.rankIvs[i].setVisibility(View.GONE);
                holder.likeIvs[i].setVisibility(View.GONE);
                holder.usernameTvs[i].setVisibility(View.GONE);
                holder.commentTvs[i].setVisibility(View.GONE);
                holder.likeCountTvs[i].setVisibility(View.GONE);
            }
        }
    }
    public void addToComments(Comment comment){
        this.comments.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil((double) comments.size() / itemsPerPage);
    }

    public View.OnClickListener likeComment(Comment comment){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean commentLiked = context.getSharedPreferences(SessionStorage.getUsername(), Context.MODE_PRIVATE).getBoolean(String.valueOf(comment.getComment_id()), false);
                int commentId = comment.getComment_id();
                int commentLikes =  commentLiked? comment.getComment_likes()-1:comment.getComment_likes()+1;

                SharedPreferences.Editor editor = context.getSharedPreferences(SessionStorage.getUsername(), Context.MODE_PRIVATE).edit();

                Map<String, String> params = new HashMap<>();
                params.put("comment-id", String.valueOf(commentId));
                params.put("comment-likes", String.valueOf(commentLikes));

                NetworkHelper.callPatch(PATH_T0_LIKE_COMMENT, params, 0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    if(commentLiked){
                        ToastFactory.showToast(context, "Like Deleted");
                    }else{
                        ToastFactory.showToast(context, "Comment Liked");
                    }
                    comment.setComment_likes(commentLikes);
                    editor.putBoolean(String.valueOf(comment.getComment_id()), !commentLiked).commit();
                    notifyDataSetChanged();

                    likeAnimation(v);

                }else{
                    ToastFactory.showToast(context,"Oops, Something went wrong");
                }
                SessionStorage.resetServerResponse();
            }
        };
    }
    public void likeAnimation(View v){
        ObjectAnimator scaleUp = ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.5f);
        scaleUp.setDuration(500); // Duration in milliseconds
        scaleUp.setRepeatCount(1); // Repeat once
        scaleUp.setRepeatMode(ObjectAnimator.REVERSE); // Reverse animation on repeat

        // Start the animation
        scaleUp.start();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private ImageView[] rankIvs = new ImageView[5];
        private ImageView[] likeIvs = new ImageView[5];
        private TextView[] usernameTvs = new TextView[5];
        private TextView[] commentTvs = new TextView[5];
        private TextView[] likeCountTvs = new TextView[5];

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            rankIvs[0] = itemView.findViewById(R.id.rankIv1);
            rankIvs[1] = itemView.findViewById(R.id.rankIv2);
            rankIvs[2] = itemView.findViewById(R.id.rankIv3);
            rankIvs[3] = itemView.findViewById(R.id.rankIv4);
            rankIvs[4] = itemView.findViewById(R.id.rankIv5);

            likeIvs[0] = itemView.findViewById(R.id.likeIv1);
            likeIvs[1] = itemView.findViewById(R.id.likeIv2);
            likeIvs[2] = itemView.findViewById(R.id.likeIv3);
            likeIvs[3] = itemView.findViewById(R.id.likeIv4);
            likeIvs[4] = itemView.findViewById(R.id.likeIv5);

            usernameTvs[0] = itemView.findViewById(R.id.usernameTv1);
            usernameTvs[1] = itemView.findViewById(R.id.usernameTv2);
            usernameTvs[2] = itemView.findViewById(R.id.usernameTv3);
            usernameTvs[3] = itemView.findViewById(R.id.usernameTv4);
            usernameTvs[4] = itemView.findViewById(R.id.usernameTv5);

            commentTvs[0] = itemView.findViewById(R.id.commentTvOne);
            commentTvs[1] = itemView.findViewById(R.id.commentTvTwo);
            commentTvs[2] = itemView.findViewById(R.id.commentTvThree);
            commentTvs[3] = itemView.findViewById(R.id.commentTvFour);
            commentTvs[4] = itemView.findViewById(R.id.commentTvFive);

            likeCountTvs[0] = itemView.findViewById(R.id.likeTvOne);
            likeCountTvs[1] = itemView.findViewById(R.id.likeTvTwo);
            likeCountTvs[2] = itemView.findViewById(R.id.likeTvThree);
            likeCountTvs[3] = itemView.findViewById(R.id.likeTvFour);
            likeCountTvs[4] = itemView.findViewById(R.id.likeTvFive);
        }
    }
}
