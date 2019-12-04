package com.commonpepper.photosen.ui.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.commonpepper.photosen.R.id
import com.commonpepper.photosen.R.layout
import com.commonpepper.photosen.ui.activities.SearchActivity
import java.io.IOException

class CategoriesAdapter(private val categories: Array<String>) : Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(layout.item_category, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.textView.text = categories[position]
        try {
            val inputStream = holder.imageView.context.assets.open(categories[position] + ".jpg")
            holder.imageView.setImageDrawable(Drawable.createFromStream(inputStream, null))
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        }
        holder.layout.setOnClickListener {
            val intent = Intent(holder.textView.context, SearchActivity::class.java)
            intent.putExtra(SearchActivity.TAG_SEARCHTAG, categories[position])
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation((holder.textView.context as Activity),
                    holder.textView, "sharedChip").toBundle()
            ActivityCompat.startActivity(holder.textView.context, intent, options)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoriesViewHolder(view: View) : ViewHolder(view) {
        var textView: TextView = view.findViewById(id.category_text)
        var imageView: ImageView = view.findViewById(id.category_image)
        var layout: RelativeLayout = view.findViewById(id.category_layout)

    }

    companion object {
        private val TAG = CategoriesAdapter::class.java.simpleName
    }

}