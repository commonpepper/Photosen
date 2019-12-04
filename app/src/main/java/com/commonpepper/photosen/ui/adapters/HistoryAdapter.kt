package com.commonpepper.photosen.ui.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.commonpepper.photosen.R.*
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.ui.activities.SinglePhotoActivity
import com.squareup.picasso.Picasso

class HistoryAdapter : PagedListAdapter<Photo?, ViewHolder>(Photo.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return PhotoItemViewHolder(layoutInflater.inflate(layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as PhotoItemViewHolder).bind(getItem(position))
    }

    private inner class PhotoItemViewHolder(itemView: View) : ViewHolder(itemView) {
        private val mImageView: ImageView = itemView.findViewById(id.item_image_imageView)
        fun bind(photo: Photo?) {
            val gd = GradientDrawable()
            gd.setSize(photo!!.width_z!!, photo.height_z!!)
            gd.shape = GradientDrawable.RECTANGLE
            val colors = mImageView.resources.obtainTypedArray(array.scroll_colors)
            val index = (Math.random() * colors.length()).toInt()
            val color = colors.getColor(index, Color.BLACK)
            gd.setColor(color)
            colors.recycle()
            Picasso.get().load(photo.url_z).placeholder(gd).into(mImageView)
            mImageView.setOnClickListener {
                val intent = Intent(mImageView.context, SinglePhotoActivity::class.java)
                intent.putExtra(SinglePhotoActivity.PHOTO_TAG, photo)
                intent.putExtra(SinglePhotoActivity.SAVE_HISTORY_TAG, false)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation((mImageView.context as Activity),
                        mImageView, "sharedImageView").toBundle()
                ActivityCompat.startActivity(mImageView.context, intent, options)
            }
        }

    }

    companion object {
        private val TAG = PhotoAdapter::class.java.simpleName
    }
}
