package com.commonpepper.photosen.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.ui.fragments.IntroSupportFragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro {
    public static final String SCROLL_TO_TOP = "SCROLL_TO_TOP";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setGoBackLock(true);
        setDepthAnimation();

        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.selectableItemBackground, outValue, true);
        skipButton.setBackgroundResource(outValue.resourceId);
        doneButton.setBackgroundResource(outValue.resourceId);

        SliderPage page1 = new SliderPage();
        page1.setTitle(getString(R.string.welcome));
        page1.setDescription(getString(R.string.welcome_desc));
        page1.setImageDrawable(R.drawable.big_ic_launcher);
        page1.setBgColor(R.color.colorAccent);
        addSlide(AppIntroFragment.newInstance(page1));

        SliderPage page2 = new SliderPage();
        page2.setTitle(getString(R.string.intro_navigation));
        page2.setDescription(getString(R.string.intro_navigation_desc));
        page2.setImageDrawable(R.drawable.menu_screen);
        page2.setBgColor(R.color.colorAccent);
        addSlide(AppIntroFragment.newInstance(page2));

        SliderPage page3 = new SliderPage();
        page3.setTitle(getString(R.string.intro_categories));
        page3.setDescription(getString(R.string.intro_categories_desc));
        page3.setImageDrawable(R.drawable.categories_screen);
        page3.setBgColor(R.color.colorAccent);
        addSlide(AppIntroFragment.newInstance(page3));

        SliderPage page4 = new SliderPage();
        page4.setTitle(getString(R.string.intro_search));
        page4.setDescription(getString(R.string.intro_search_desc));
        page4.setImageDrawable(R.drawable.search_screen);
        page4.setBgColor(R.color.colorAccent);
        addSlide(AppIntroFragment.newInstance(page4));

        addSlide(new IntroSupportFragment());
    }

    private void close() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SCROLL_TO_TOP, true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        close();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        close();
    }
}
