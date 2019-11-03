package com.commonpepper.photosen.ui.adapters

import android.app.Activity
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.commonpepper.photosen.R
import com.commonpepper.photosen.model.Comments
import com.commonpepper.photosen.ui.activities.UserActivity
import com.commonpepper.photosen.ui.adapters.CommentsAdapter.CommentsViewHolder
import com.squareup.picasso.Picasso

class CommentsAdapter(private val comments: Comments?) : RecyclerView.Adapter<CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = comments!!.comments.comment[position]
        val iconUrl = "https://flickr.com/buddyicons/" + comment.author + ".jpg"
        Picasso.get().load(iconUrl).into(holder.avatar)

        holder.username.text = comment.authorname
        //        holder.comment.setText(comment.get_content());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.comment.text = Html.fromHtml(comment._content, Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.comment.text = Html.fromHtml(comment._content)
        }

        holder.commentLayout.setOnClickListener { v ->
            val intent = UserActivity.getStartingIntent(holder.commentLayout.context, comment.author, comment.authorname, iconUrl)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(holder.avatar.context as Activity,
                    holder.avatar, "sharedAvatar").toBundle()
            ActivityCompat.startActivity(holder.commentLayout.context, intent, options)
        }
    }

    override fun getItemCount(): Int {
        return if (comments == null || comments.comments == null || comments.comments.comment == null) 0 else comments.comments.comment.size
    }

    override fun getItemId(position: Int): Long {
        return comments!!.comments.comment[position].id.hashCode().toLong() //id()
    }

    class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView = itemView.findViewById(R.id.avatar)
        var username: TextView = itemView.findViewById(R.id.username)
        var comment: TextView = itemView.findViewById(R.id.comment)
        var commentLayout: LinearLayout = itemView.findViewById(R.id.comment_layout)

    }
}
