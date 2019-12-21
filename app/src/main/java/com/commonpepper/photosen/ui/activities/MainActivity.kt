package com.commonpepper.photosen.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.commonpepper.photosen.Photosen
import com.commonpepper.photosen.R
import com.commonpepper.photosen.R.*
import com.commonpepper.photosen.ui.adapters.MyPagerAdapter
import com.commonpepper.photosen.ui.fragments.PhotoListFragment
import com.commonpepper.photosen.ui.fragments.PhotoListFragment.Companion.newInstance
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.vorlonsoft.android.rate.AppRate
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AbstractNavActivity() {
    private var mToolbar: Toolbar? = null
    private var mViewPager: ViewPager? = null
    private var mTabLayout: TabLayout? = null
    private var mPagerAdapter: MyPagerAdapter? = null
    private val photoListFragments = arrayOfNulls<PhotoListFragment?>(FRAGMENTS_NUM)
    /**
     * Used when IntroActivity closes
     * @param intent
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getBooleanExtra(IntroActivity.SCROLL_TO_TOP, false)) {
            photoListFragments[0]!!.scrollToTop()
            photoListFragments[1]!!.scrollToTop()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs: SharedPreferences = getSharedPreferences(Photosen.PREFERENCES, Context.MODE_PRIVATE)
        val firstLaunch = prefs.getBoolean(PREFERENCE_FIRST_LAUNCH, true)
        if (firstLaunch) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            prefs.edit().putBoolean(PREFERENCE_FIRST_LAUNCH, false).apply()
        }
        setContentView(layout.activity_main)
        mToolbar = findViewById<Toolbar?>(id.toolbar)
        mViewPager = findViewById<ViewPager?>(id.view_pager)
        mTabLayout = findViewById<TabLayout?>(id.tab_layout)
        drawerLayout = findViewById(id.drawer_layout)
        val navigationView: NavigationView = findViewById(id.nav_view)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(drawable.ic_menu_white_24dp)
        navigationView.menu.findItem(id.drawer_popular).isCheckable = true
        navigationView.menu.findItem(id.drawer_popular).isChecked = true
        navigationView.setNavigationItemSelectedListener(this)
        mPagerAdapter = MyPagerAdapter(supportFragmentManager)
        mViewPager!!.adapter = mPagerAdapter
        photoListFragments[0] = newInstance("")
        mPagerAdapter!!.addFragment(photoListFragments[0]!!, getString(string.popular) + " " + getString(string.now))
        val dateFormatApi: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatTitle: DateFormat = SimpleDateFormat("dd.MM")
        val cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        for (i in 1 until FRAGMENTS_NUM) {
            cal.add(Calendar.DATE, -1)
            photoListFragments[i] = newInstance(dateFormatApi.format(cal.time))
            mPagerAdapter!!.addFragment(photoListFragments[i]!!, getString(string.popular) + " "
                    + dateFormatTitle.format(cal.time))
        }
        mTabLayout!!.setupWithViewPager(mViewPager)
        AppRate.with(this)
                .setMessage(string.if_you_like_photosen_rate_it_on_play_store)
                .setThemeResId(style.DialogTheme)
                .setInstallDays(0.toByte())                  // default is 10, 0 means install day, 10 means app is launched 10 or more days later than installation
                .setLaunchTimes(4.toByte())                  // default is 10, 3 means app is launched 3 or more times
                .setRemindInterval(1.toByte())               // default is 1, 1 means app is launched 1 or more days after neutral button clicked
                .setRemindLaunchesNumber(0.toByte())         // default is 0, 1 means app is launched 1 or more times after neutral button clicked
                .monitor()                                // Monitors the app launch times

        AppRate.showRateDialogIfMeetsConditions(this) // Shows the Rate Dialog when conditions are met
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.search_button) {
            startActivity(Intent(this, SearchActivity::class.java))
        } else if (id == android.R.id.home) {
            drawerLayout!!.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val PREFERENCE_FIRST_LAUNCH = "MAIN_FIRST_LAUNCH"
        private const val FRAGMENTS_NUM = 7
    }
}
