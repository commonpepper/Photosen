package com.commonpepper.photosen.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.model.Comments;
import com.commonpepper.photosen.ui.activities.UserActivity;
import com.squareup.picasso.Picasso;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Comments comments;

    public CommentsAdapter(Comments comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        CommentsViewHolder viewHolder = new CommentsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comments.CommentsBean.CommentBean comment = comments.getComments().getComment().get(position);
        String iconUrl = "https://flickr.com/buddyicons/" + comment.getAuthor() + ".jpg";
        Picasso.get().load(iconUrl).into(holder.avatar);

        holder.username.setText(comment.getAuthorname());
//        holder.comment.setText(comment.get_content());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.comment.setText(Html.fromHtml(comment.get_content(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.comment.setText(Html.fromHtml(comment.get_content()));
        }

        holder.commentLayout.setOnClickListener(v -> {
            Intent intent = UserActivity.getStartingIntent(holder.commentLayout.getContext(), comment.getAuthor(), comment.getAuthorname(), iconUrl);
            Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) holder.avatar.getContext(),
                    holder.avatar, "sharedAvatar").toBundle();
            ActivityCompat.startActivity(holder.commentLayout.getContext(), intent, options);
        });
    }

    @Override
    public int getItemCount() {
        return comments.getComments().getComment().size();
    }

    @Override
    public long getItemId(int position) {
        return comments.getComments().getComment().get(position).getId().hashCode(); //id()
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView username;
        public TextView comment;
        public LinearLayout commentLayout;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
            commentLayout = itemView.findViewById(R.id.comment_layout);
        }
    }
}
