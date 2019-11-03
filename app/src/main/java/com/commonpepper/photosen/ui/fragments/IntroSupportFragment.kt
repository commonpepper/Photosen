package com.commonpepper.photosen.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.R

class IntroSupportFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_intro_support, container, false)

        val rate = view.findViewById<Button>(R.id.intro_rate_btn)
        rate.setOnClickListener { v ->
            Photosen.firebaseAnalytics?.logEvent("INTRO_RATE", null)
            val uri = Uri.parse("market://details?id=" + Photosen.PACKAGE_NAME)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + Photosen.PACKAGE_NAME)))
            }
        }

        return view
    }
}
