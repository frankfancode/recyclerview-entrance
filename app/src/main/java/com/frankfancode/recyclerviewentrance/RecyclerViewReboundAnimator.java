package com.frankfancode.recyclerviewentrance;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class RecyclerViewReboundAnimator {
    private static final int INIT_DELAY = 500;

    int order = 1;
    private int mWidth;
    private RecyclerView mRecyclerView;
    private boolean mFirstViewInit = true;
    private int mLastPosition = -1;
    private int mStartDelay;

    public RecyclerViewReboundAnimator(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mWidth = mRecyclerView.getResources().getDisplayMetrics().widthPixels;
        mStartDelay = INIT_DELAY;
    }

    public void onCreateViewHolder(View item, int colum) {
        if (mFirstViewInit) {
            slideInBottom(item, mStartDelay);

            if (order % colum == 0) {
                mStartDelay += 50;
                order = 1;
            } else {
                order++;
            }
        }
    }

    public void onBindViewHolder(View item, int position) {
        if (!mFirstViewInit && position > mLastPosition) {
            slideInBottom(item, 0);
            mLastPosition = position;
        }
    }

    private void slideInBottom(final View item,
                               final int delay) {
        item.setTranslationX(mWidth);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mWidth);
        valueAnimator.setInterpolator(new SpringInterpolator());
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFirstViewInit = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (mWidth - (float) animation.getAnimatedValue());

                item.setTranslationX(val);
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setStartDelay(delay);
        valueAnimator.start();

    }

}
