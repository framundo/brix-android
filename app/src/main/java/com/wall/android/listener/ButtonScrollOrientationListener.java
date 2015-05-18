package com.wall.android.listener;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wall.android.R;

import java.util.List;

public class ButtonScrollOrientationListener extends AbstractOrientationListener {

    private Animation mSlideUp;
    private Animation mSlideDown;
    private boolean mAreButtonsShown = true;

    private View mScrollButtonsContainer;
    private List<View> mButtons;

    public ButtonScrollOrientationListener(Context context,
                                           View scrollButtonsContainer,
                                           List<View> buttons) {
        this.mScrollButtonsContainer = scrollButtonsContainer;
        this.mButtons = buttons;
        mSlideDown = AnimationUtils.loadAnimation(context, R.anim.animation_slide_down);
        mSlideUp = AnimationUtils.loadAnimation(context, R.anim.animation_slide_up);
    }

    @Override
    public void onScrollDown() {
        if (!mAreButtonsShown) return;
        mScrollButtonsContainer.startAnimation(mSlideUp);
        mAreButtonsShown = false;
        for (View button : mButtons) button.setClickable(false);
    }

    @Override
    public void onScrollUp() {
        if (mAreButtonsShown) return;
        mScrollButtonsContainer.startAnimation(mSlideDown);
        mAreButtonsShown = true;
        for (View button : mButtons) button.setClickable(true);
    }
}
