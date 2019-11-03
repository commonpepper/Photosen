package com.commonpepper.photosen.ui.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commonpepper.photosen.R
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.network.NetworkState
import com.commonpepper.photosen.network.datasource.AbstractListDataSource
import com.commonpepper.photosen.ui.activities.SinglePhotoActivity
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

class PhotoAdapter : PagedListAdapter<Photo, RecyclerView.ViewHolder>(Photo.DIFF_CALLBACK) {

    private var networkState: NetworkState? = NetworkState.RUNNING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_PHOTO) {
            PhotoItemViewHolder(layoutInflater.inflate(R.layout.item_image, parent, false))
        } else {
            NetworkItemViewHolder(layoutInflater.inflate(R.layout.item_extra, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (networkState != NetworkState.SUCCESS && position == itemCount - 1) {
            VIEW_TYPE_EXTRA
        } else {
            VIEW_TYPE_PHOTO
        }
    }

    override fun getItemCount(): Int {
        return if (hasExtraRow()) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhotoItemViewHolder) {
            holder.bind(getItem(position)!!)
        } else if (holder is NetworkItemViewHolder) {
            holder.bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.SUCCESS
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = networkState
        val hadExtraRow = hasExtraRow()
        networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private inner class PhotoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mImageView: ImageView = itemView.findViewById(R.id.item_image_imageView)
        private val layout: LinearLayout = itemView.findViewById(R.id.item_image_layout)

        fun bind(photo: Photo) {
            val gd = GradientDrawable()
            gd.setSize(photo.width_z, photo.height_z)
            gd.shape = GradientDrawable.RECTANGLE
            val colors = mImageView.resources.obtainTypedArray(R.array.scroll_colors)
            val index = (Math.random() * colors.length()).toInt()
            val color = colors.getColor(index, Color.BLACK)
            gd.setColor(color)
            colors.recycle()

            if (!photo.isSexuallyExplicit) {
                Picasso.get().load(photo.url_z).placeholder(gd).into(mImageView)
                mImageView.setOnClickListener { v ->
                    val intent = Intent(mImageView.context, SinglePhotoActivity::class.java)
                    intent.putExtra(SinglePhotoActivity.PHOTO_TAG, photo)
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mImageView.context as Activity,
                            mImageView, "sharedImageView").toBundle()
                    ActivityCompat.startActivity(mImageView.context, intent, options)
                }
            } else {
                mImageView.setImageDrawable(mImageView.resources.getDrawable(R.drawable.explicit_content))
                mImageView.setOnClickListener { v -> }
            }
        }
    }

    private inner class NetworkItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mProgressBar: ProgressBar
        private val mTextView: TextView
        private val mButton: MaterialButton

        init {
            mProgressBar = itemView.findViewById(R.id.item_last_progressBar)
            mTextView = itemView.findViewById(R.id.item_last_error_textView)
            mButton = itemView.findViewById(R.id.item_last_refreshButton)

            mButton.setOnClickListener { v ->
                val list = currentList
                if (list != null) {
                    if (list.size == 0) {
                        list.dataSource.invalidate()
                    } else {
                        (list.dataSource as AbstractListDataSource<*>).retryLast()
                    }
                }
            }
        }

        fun bind(networkState: NetworkState?) {
            if (networkState == NetworkState.RUNNING) {
                mProgressBar.visibility = View.VISIBLE
                mTextView.visibility = View.GONE
                mButton.visibility = View.GONE
            } else {
                mProgressBar.visibility = View.GONE
                mTextView.visibility = View.VISIBLE
                mButton.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private val TAG = PhotoAdapter::class.java.simpleName

        private val VIEW_TYPE_PHOTO = 1
        private val VIEW_TYPE_EXTRA = 2
    }
}
