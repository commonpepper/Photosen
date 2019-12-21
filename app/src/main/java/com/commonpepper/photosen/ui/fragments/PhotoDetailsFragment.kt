package com.commonpepper.photosen.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.commonpepper.photosen.R
import com.commonpepper.photosen.R.layout
import com.commonpepper.photosen.R.plurals
import com.commonpepper.photosen.model.Photo
import com.commonpepper.photosen.model.PhotoDetails
import com.commonpepper.photosen.model.PhotoDetails.PhotoBean.TagsBean.TagBean
import com.commonpepper.photosen.model.PhotoSizes
import com.commonpepper.photosen.network.NetworkState
import com.commonpepper.photosen.ui.activities.SearchActivity
import com.commonpepper.photosen.ui.activities.UserActivity
import com.commonpepper.photosen.ui.viewmodels.PhotoDetailsViewModelFactory
import com.commonpepper.photosen.ui.viewmodels.PhotoDetailsViewModelFactory.PhotoDetailsViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayout.LayoutParams
import com.google.android.flexbox.JustifyContent
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import java.util.*

class PhotoDetailsFragment : Fragment() {
    private var mViewModel: PhotoDetailsViewModel? = null
    private var progressLayout: LinearLayout? = null
    private var detailsLayout: LinearLayout? = null
    private var progressBar: ProgressBar? = null
    private var errorTextView: TextView? = null
    private var refreshButton: MaterialButton? = null
    private var username: TextView? = null
    private var viewsNumber: TextView? = null
    private var imageViewAvatar: ImageView? = null
    private var location: TextView? = null
    private var locationLayout: LinearLayout? = null
    private var textViewWidth: TextView? = null
    private var textViewHeigth: TextView? = null
    private var textViewDate: TextView? = null
    private var photo: Photo? = null
    private var tagsLayout: LinearLayout? = null
    private var tagsLabel: TextView? = null
    private var details: PhotoDetails? = null
    private val photoSizes = MutableLiveData<PhotoSizes>()
    private var usernameLayout: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        photo = args!!.getParcelable(TAG_PHOTO)
        val factory = PhotoDetailsViewModelFactory(photo!!.id!!, photo!!.secret!!)
        mViewModel = ViewModelProviders.of(this, factory).get(PhotoDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(layout.fragment_details, container, false)
        progressLayout = view.findViewById<LinearLayout?>(R.id.single_image_progress_layout)
        progressBar = progressLayout!!.findViewById<ProgressBar?>(R.id.item_last_progressBar)
        errorTextView = progressLayout!!.findViewById<TextView?>(R.id.item_last_error_textView)
        refreshButton = progressLayout!!.findViewById(R.id.item_last_refreshButton)
        refreshButton!!.setOnClickListener { mViewModel!!.loadDetails() }
        detailsLayout = view.findViewById<LinearLayout?>(R.id.single_image_details_layout)
        username = view.findViewById<TextView?>(R.id.single_image_username)
        viewsNumber = view.findViewById<TextView?>(R.id.single_image_views_number)
        imageViewAvatar = view.findViewById<ImageView?>(R.id.single_photo_avatar_image_view)
        location = view.findViewById<TextView?>(R.id.single_image_location)
        locationLayout = view.findViewById<LinearLayout?>(R.id.single_image_location_layout)
        textViewWidth = view.findViewById<TextView?>(R.id.single_image_width)
        textViewHeigth = view.findViewById<TextView?>(R.id.single_image_height)
        textViewDate = view.findViewById<TextView?>(R.id.single_image_date)
        tagsLayout = view.findViewById<LinearLayout?>(R.id.single_photo_tags_layout)
        tagsLabel = view.findViewById<TextView?>(R.id.tags_label)
        usernameLayout = view.findViewById<LinearLayout?>(R.id.single_image_username_layout)
        showRunning()
        mViewModel!!.getNetworkState().observe(this, Observer { networkState: NetworkState ->
            if (networkState == NetworkState.RUNNING) {
                showRunning()
            } else if (networkState == NetworkState.FAILED) {
                showFailed()
            } //else SUCCESS
        })
        mViewModel!!.getPhotoDetails().observe(this, Observer { photoDetails: PhotoDetails? -> if (photoDetails != null) showSuccess(photoDetails) })
        mViewModel!!.getPhotoSizes().observe(this, Observer { sizes: PhotoSizes ->
            photoSizes.postValue(sizes)
            if (sizes.sizes!!.size!!.isNotEmpty()) {
                val width = sizes.sizes!!.size!![sizes.sizes!!.size!!.size - 1].width
                val height = sizes.sizes!!.size!![sizes.sizes!!.size!!.size - 1].height
                textViewWidth!!.text = resources.getQuantityString(plurals.x_pixels, width, width)
                textViewHeigth!!.text = resources.getQuantityString(plurals.x_pixels, height, height)
            }
        })
        return view
    }

    private fun showRunning() {
        progressLayout!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.VISIBLE
        errorTextView!!.visibility = View.GONE
        refreshButton!!.visibility = View.GONE
        detailsLayout!!.visibility = View.GONE
    }

    private fun showSuccess(photoDetails: PhotoDetails) {
        details = photoDetails
        progressLayout!!.visibility = View.GONE
        detailsLayout!!.visibility = View.VISIBLE
        Picasso.get().load(photo!!.iconUrl).into(imageViewAvatar)
        username!!.text = photoDetails.photo!!.owner!!.username
        usernameLayout!!.setOnClickListener { v: View? ->
            val intent: Intent? = UserActivity.getStartingIntent(activity, photoDetails.photo!!.owner!!.nsid,
                    photoDetails.photo!!.owner!!.username,
                    photo!!.iconUrl)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation((imageViewAvatar!!.context as Activity),
                    imageViewAvatar!!, "sharedAvatar").toBundle()
            ActivityCompat.startActivity(imageViewAvatar!!.context, intent!!, options)
        }
        val views = photoDetails.photo!!.views
        viewsNumber!!.text = resources.getQuantityString(plurals.x_views, views, views)
        val locationStr: String = photoDetails.photo!!.owner!!.location!!
        if (locationStr.isNotEmpty()) {
            location!!.text = locationStr
        } else {
            locationLayout!!.visibility = View.GONE
        }
        textViewDate!!.text = photo!!.datetaken
        val tags: List<TagBean> = photoDetails.photo!!.tags!!.tag!!
        if (tags.isEmpty()) {
            tagsLabel!!.visibility = View.GONE
        }
        val chips: MutableList<Chip> = ArrayList()
        for (tag in tags) {
            val chip = Chip(context)
            chip.transitionName = "sharedChip"
            chip.text = tag.raw
            chip.setOnClickListener { v: View? ->
                val intent = Intent(this@PhotoDetailsFragment.context, SearchActivity::class.java)
                intent.putExtra(SearchActivity.TAG_SEARCHTAG, tag.raw)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation((this@PhotoDetailsFragment.context as Activity?)!!,
                        chip, "sharedChip").toBundle()
                ActivityCompat.startActivity(this@PhotoDetailsFragment.context!!, intent, options)
            }
            chips.add(chip)
        }
        simpleBinPack(chips)
    }

    private fun showFailed() {
        progressLayout!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.GONE
        errorTextView!!.visibility = View.VISIBLE
        refreshButton!!.visibility = View.VISIBLE
        detailsLayout!!.visibility = View.GONE
    }

    val liveSizes: LiveData<PhotoSizes>
        get() = photoSizes

    private fun simpleBinPack(chips: List<Chip>) {
        val containers: MutableList<ArrayList<Chip>> = ArrayList()
        val freeSpace: MutableList<Int> = ArrayList()
        Collections.sort(chips) { x: Chip, y: Chip ->
            -x.chipDrawable.intrinsicWidth.compareTo(y.chipDrawable.intrinsicWidth)
        }
        for (chip in chips) {
            val chipWidth = chip.chipDrawable.intrinsicWidth
            var resultingContainer = -1
            for (i in containers.indices) {
                if (chipWidth < freeSpace[i]) {
                    resultingContainer = i
                }
            }
            if (resultingContainer != -1) {
                containers[resultingContainer].add(chip)
                freeSpace[resultingContainer] = freeSpace[resultingContainer] - chipWidth
            } else {
                val newContainer = ArrayList<Chip>()
                newContainer.add(chip)
                containers.add(newContainer)
                freeSpace.add(context!!.resources.displayMetrics.widthPixels - chipWidth)
            }
        }
        if (containers.size > 0) {
            for (i in 0 until containers.size - 1) {
//                Log.d("CONTAINER SIZE:", "" + containers.get(i).size());

                val flexbox = getNewFlexbox()
                flexbox.justifyContent = JustifyContent.SPACE_AROUND
                for (chip in containers[i]) {
                    flexbox.addView(chip)
                }
                tagsLayout!!.addView(flexbox)
            }
            //last row


            val flexbox = getNewFlexbox()
            flexbox.justifyContent = JustifyContent.FLEX_START
            for (chip in containers[containers.size - 1]) {
                flexbox.addView(chip)
            }
            tagsLayout!!.addView(flexbox)
        }
    }

    private fun getNewFlexbox(): FlexboxLayout {
        val flexbox = FlexboxLayout(context)
        val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT)
        params.layoutDirection = FlexDirection.ROW
        flexbox.layoutParams = params
        flexbox.flexWrap = FlexWrap.NOWRAP
        return flexbox
    }

    companion object {
        private const val TAG_PHOTO = "photo"
        fun newInstance(photo: Photo?): PhotoDetailsFragment {
            val newFragment = PhotoDetailsFragment()
            val args = Bundle()
            args.putParcelable(TAG_PHOTO, photo)
            newFragment.arguments = args
            return newFragment
        }
    }
}