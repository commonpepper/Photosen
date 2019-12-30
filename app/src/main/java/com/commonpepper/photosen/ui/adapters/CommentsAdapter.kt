package com.commonpepper.photosen.ui.adapters

import android.app.Activity
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.commonpepper.photosen.R.id
import com.commonpepper.photosen.R.layout
import com.commonpepper.photosen.model.Comments
import com.commonpepper.photosen.ui.activities.UserActivity
import com.squareup.picasso.Picasso

class CommentsAdapter(private val comments: Comments?) : Adapter<CommentsAdapter.CommentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(layout.item_comment, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = comments!!.comments!!.comment!![position]
        val iconUrl = "https://flickr.com/buddyicons/" + comment.author + ".jpg"
        Picasso.get().load(iconUrl).into(holder.avatar)
        holder.username.text = comment.authorname

        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            holder.comment.text = Html.fromHtml(comment.get_content(), Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.comment.text = Html.fromHtml(comment.get_content())
        }
        holder.commentLayout.setOnClickListener {
            val intent = UserActivity.getStartingIntent(holder.commentLayout.context, comment.author, comment.authorname, iconUrl)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation((holder.avatar.context as Activity),
                    holder.avatar, "sharedAvatar").toBundle()
            ActivityCompat.startActivity(holder.commentLayout.context, intent, options)
        }
    }

    override fun getItemCount(): Int {
        return comments?.comments?.comment?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return comments!!.comments!!.comment!![position].id.hashCode().toLong() //id()
    }

    class CommentsViewHolder(itemView: View) : ViewHolder(itemView) {
        var avatar: ImageView = itemView.findViewById(id.avatar)
        var username: TextView = itemView.findViewById(id.username)
        var comment: TextView = itemView.findViewById(id.comment)
        var commentLayout: LinearLayout = itemView.findViewById(id.comment_layout)

    }

}