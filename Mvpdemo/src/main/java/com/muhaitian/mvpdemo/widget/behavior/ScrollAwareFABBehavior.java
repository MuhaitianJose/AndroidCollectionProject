package com.muhaitian.mvpdemo.widget.behavior;

import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by muhaitian on 2017/9/5.
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {
    private boolean mIsAnimatingOut = false;

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && !mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            animateIn(child);
        }
    }

    private void animateOut(FloatingActionButton child) {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(child).translationY(child.getHeight() + getMarginBottom(child))
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            mIsAnimatingOut = true;
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            mIsAnimatingOut = false;
                            view.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(View view) {
                            mIsAnimatingOut = false;
                        }
                    }).start();
        }
    }

    private void animateIn(FloatingActionButton child) {
        child.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(child).translationY(0)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .withLayer()
                    .setListener(null)
                    .start();
        }
    }

    private int getMarginBottom(View v) {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }
}
