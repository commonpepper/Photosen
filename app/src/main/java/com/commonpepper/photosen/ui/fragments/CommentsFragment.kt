package com.commonpepper.photosen.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commonpepper.photosen.R
import com.commonpepper.photosen.R.layout
import com.commonpepper.photosen.model.Comments
import com.commonpepper.photosen.network.NetworkState
import com.commonpepper.photosen.ui.adapters.CommentsAdapter
import com.commonpepper.photosen.ui.viewmodels.CommentsViewModelFactory
import com.commonpepper.photosen.ui.viewmodels.CommentsViewModelFactory.CommentsViewModel
import net.cachapa.expandablelayout.ExpandableLayout
import net.cachapa.expandablelayout.ExpandableLayout.OnExpansionUpdateListener
import net.cachapa.expandablelayout.ExpandableLayout.State
import java.lang.ref.WeakReference

class CommentsFragment : Fragment(), OnExpansionUpdateListener {
    private var mViewModel: CommentsViewModel? = null
    private var showComments: Button? = null
    private var refreshButton: Button? = null
    private var errorTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    private var progressLayout: LinearLayout? = null
    private var recyclerView: RecyclerView? = null
    private var expandableLayout: ExpandableLayout? = null
    private var nestedScrollView: WeakReference<NestedScrollView?>? = null
    private var hideComments: Button? = null

    companion object {
        const val TAG_PHOTO_ID = "PHOTO_ID"
        fun newInstance(photo_id: String?): CommentsFragment {
            val newFragment = CommentsFragment()
            val args = Bundle()
            args.putString(TAG_PHOTO_ID, photo_id)
            newFragment.arguments = args
            return newFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        val photoId = args?.getString(TAG_PHOTO_ID)
        val factory = CommentsViewModelFactory(photoId ?: "")
        mViewModel = ViewModelProviders.of(this, factory).get(CommentsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(layout.fragment_comments, container, false)
        showComments = view.findViewById<Button?>(R.id.show_comments_button)
        hideComments = view.findViewById<Button?>(R.id.hide_comments_button)
        progressLayout = view.findViewById<LinearLayout?>(R.id.comments_progress_layout)
        progressBar = view.findViewById<ProgressBar?>(R.id.item_last_progressBar)
        errorTextView = view.findViewById<TextView?>(R.id.item_last_error_textView)
        refreshButton = view.findViewById<Button?>(R.id.item_last_refreshButton)
        recyclerView = view.findViewById(R.id.comments_recycler)
        expandableLayout = view.findViewById(R.id.expandable_layout)
        refreshButton?.setOnClickListener { mViewModel!!.loadComments() }
        showComments?.setOnClickListener {
            mViewModel?.hiden = false
            showComments?.visibility = View.GONE
            hideComments?.visibility = View.VISIBLE
            expandableLayout?.expand()
            if (mViewModel?.getNetworkState()?.value != NetworkState.SUCCESS) {
                mViewModel?.loadComments()
            }
            expandableLayout?.setOnExpansionUpdateListener(this)
        }
        hideComments?.setOnClickListener {
            mViewModel?.hiden = true
            showComments?.visibility = View.VISIBLE
            hideComments?.visibility = View.GONE
            expandableLayout!!.collapse()
        }
        mViewModel?.getNetworkState()?.observe(viewLifecycleOwner, Observer { networkState: NetworkState ->
            when (networkState) {
                NetworkState.FAILED -> {
                    progressLayout?.visibility = View.VISIBLE
                    progressBar?.visibility = View.GONE
                    errorTextView?.visibility = View.VISIBLE
                    refreshButton?.visibility = View.VISIBLE
                }
                NetworkState.SUCCESS -> progressLayout?.visibility = View.GONE
                NetworkState.RUNNING -> {
                    progressLayout?.visibility = View.VISIBLE
                    progressBar?.visibility = View.VISIBLE
                    errorTextView?.visibility = View.GONE
                    refreshButton?.visibility = View.GONE
                }
            }
        })
        mViewModel?.getComments()?.observe(viewLifecycleOwner, Observer { comments: Comments? ->
            val adapter = CommentsAdapter(comments)
            adapter.setHasStableIds(true)
            val llm = LinearLayoutManager(context)
            llm.orientation = RecyclerView.VERTICAL
            recyclerView?.layoutManager = llm
//            recyclerView.setItemViewCacheSize(10);
//            recyclerView.setDrawingCacheEnabled(true);


            recyclerView?.adapter = adapter
        })

        //INITIALIZATION:
        if (mViewModel?.hiden == true) {
            showComments?.visibility = View.VISIBLE
            hideComments?.visibility = View.GONE
            expandableLayout!!.collapse()
        } else {
            showComments?.visibility = View.GONE
            hideComments?.visibility = View.VISIBLE
            expandableLayout?.expand()
        }
        return view
    }

    fun setNestedScrollView(nestedScrollView: NestedScrollView?) {
        this.nestedScrollView = WeakReference(nestedScrollView)
    }

    override fun onExpansionUpdate(expansionFraction: Float, state: Int) {
        if (state == State.EXPANDING && nestedScrollView?.get() != null) {
//            Log.d("ExpandableLayout:", "expansionFraction = " + expansionFraction);

            val nestedScroll = nestedScrollView?.get()
            nestedScroll!!.fullScroll(View.FOCUS_DOWN)
        }
    }
}