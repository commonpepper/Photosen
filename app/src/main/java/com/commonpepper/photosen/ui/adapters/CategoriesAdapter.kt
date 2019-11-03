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
import androidx.recyclerview.widget.RecyclerView
import com.commonpepper.photosen.R
import com.commonpepper.photosen.ui.activities.SearchActivity
import java.io.IOException

class CategoriesAdapter(private val categories: Array<String>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
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

        holder.layout.setOnClickListener { v ->
            val intent = Intent(holder.textView.context, SearchActivity::class.java)
            intent.putExtra(SearchActivity.TAG_SEARCHTAG, categories[position])
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(holder.textView.context as Activity,
                    holder.textView, "sharedChip").toBundle()
            ActivityCompat.startActivity(holder.textView.context, intent, options)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.category_text)
        var imageView: ImageView = view.findViewById(R.id.category_image)
        var layout: RelativeLayout = view.findViewById(R.id.category_layout)

    }

    companion object {
        private val TAG = CategoriesAdapter::class.java.simpleName
    }
}
