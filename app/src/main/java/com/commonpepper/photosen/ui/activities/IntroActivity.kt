package com.commonpepper.photosen.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import com.commonpepper.photosen.R.*
import com.commonpepper.photosen.ui.fragments.IntroSupportFragment
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage

class IntroActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setGoBackLock(true)
        setDepthAnimation()
        val outValue = TypedValue()
        theme.resolveAttribute(attr.selectableItemBackground, outValue, true)
        skipButton.setBackgroundResource(outValue.resourceId)
        doneButton.setBackgroundResource(outValue.resourceId)
        val page1 = SliderPage()
        page1.apply {
            title = getString(string.welcome)
            description = getString(string.welcome_desc)
            imageDrawable = drawable.big_ic_launcher
            bgColor = color.colorAccent
            addSlide(AppIntroFragment.newInstance(this))
        }
        val page2 = SliderPage()
        page2.apply {
            title = getString(string.intro_navigation)
            description = getString(string.intro_navigation_desc)
            imageDrawable = drawable.menu_screen
            bgColor = color.colorAccent
            addSlide(AppIntroFragment.newInstance(this))
        }
        val page3 = SliderPage()
        page3.apply {
            title = getString(string.intro_categories)
            description = getString(string.intro_categories_desc)
            imageDrawable = drawable.categories_screen
            bgColor = color.colorAccent
            addSlide(AppIntroFragment.newInstance(this))
        }
        val page4 = SliderPage()
        page4.apply {
            title = getString(string.intro_search)
            description = getString(string.intro_search_desc)
            imageDrawable = drawable.search_screen
            bgColor = color.colorAccent
            addSlide(AppIntroFragment.newInstance(this))
        }
        addSlide(IntroSupportFragment())
    }

    private fun close() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(SCROLL_TO_TOP, true)
        startActivity(intent)
        finish()
    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        close()
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        close()
    }

    companion object {
        const val SCROLL_TO_TOP = "SCROLL_TO_TOP"
    }
}
