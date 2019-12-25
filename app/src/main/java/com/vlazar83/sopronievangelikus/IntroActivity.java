package com.vlazar83.sopronievangelikus;

import android.os.Bundle;
import android.util.Log;

import com.cuneytayyildiz.onboarder.OnboarderActivity;
import com.cuneytayyildiz.onboarder.OnboarderPage;
import com.cuneytayyildiz.onboarder.utils.OnboarderPageChangeListener;

import java.util.Arrays;
import java.util.List;

public class IntroActivity extends OnboarderActivity implements OnboarderPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<OnboarderPage> pages = Arrays.asList(
                new OnboarderPage.Builder()
                        .title("Welcome")
                        .description("nice to see you here")
                        .imageResourceId(R.drawable.church_building_free_icon)
                        .backgroundColorId(R.color.color_church_building)
                        .titleColorId(R.color.primary_text)
                        .descriptionColorId(R.color.secondary_text)
                        .multilineDescriptionCentered(true)
                        .build(),

                new OnboarderPage.Builder()
                        .title("Faith")
                        .description("this is what connects us")
                        .imageResourceId(R.drawable.dove_free_icon)
                        .backgroundColorId(R.color.color_dove)
                        .titleColorId(R.color.primary_text)
                        .descriptionColorId(R.color.secondary_text)
                        .multilineDescriptionCentered(true)
                        .build(),

                new OnboarderPage.Builder()
                        .title("Grow together")
                        .description("it!s better with you onboard")
                        .imageResourceId(R.drawable.growth_free_icon)
                        .backgroundColorId(R.color.color_growth)
                        .titleColorId(R.color.primary_text)
                        .descriptionColorId(R.color.secondary_text)
                        .multilineDescriptionCentered(true)
                        .build(),
                
                new OnboarderPage.Builder()
                        .title("Get notified")
                        .description("we are happy to see you at any of our events")
                        .imageResourceId(R.drawable.family_free_icon)
                        .backgroundColorId(R.color.color_family)
                        .titleColorId(R.color.primary_text)
                        .descriptionColorId(R.color.secondary_text)
                        .multilineDescriptionCentered(true)
                        .build()

        );

        setSkipButtonTitle(R.string.button_skip);
        setFinishButtonTitle(R.string.button_finish);

        setOnboarderPageChangeListener(this);
        initOnboardingPages(pages);
    }

    @Override
    public void onSkipButtonPressed() {
        super.onSkipButtonPressed();
    }

    @Override
    public void onFinishButtonPressed() {
        finish();
    }

    @Override
    public void onPageChanged(int position) {
        Log.d(getClass().getSimpleName(), "onPageChanged: " + position);
    }
}