
package com.esilv.fink.supportTutorial;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.esilv.fink.R;
import com.esilv.fink.View.LoginActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(AppIntroFragment.newInstance("Welcome on Fink ! ", "Your application for your budget and investment.. ",R.drawable.app_logo, Color.parseColor("#222222")));
        addSlide(AppIntroFragment.newInstance("Financial Overview", "Quick overview dashboard, budgets, reports with customizations",R.mipmap.capture_a, Color.parseColor("#222222")));
        addSlide(AppIntroFragment.newInstance("Accounts ", "Checking,Saving,credit, assets, and inverstments using Machine Learning Agorithm on our server... Totally OpenSource ! ",R.mipmap.capture, Color.parseColor("#222222")));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}