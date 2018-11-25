package com.pwr.bzapps.plwordnetmobile.layout.animator;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class ExpandableViewAnimator {

    public static void expand(final View v){
        expand(v,0.33f);
    }

    public static void expand(final View v, float speed_multiplier) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)((targetHeight / v.getContext().getResources().getDisplayMetrics().density)/speed_multiplier));
        v.startAnimation(a);
    }

    public static void collapse(final View v){
        collapse(v,0.5f);
    }

    public static void collapse(final View v, float speed_multiplier) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)((initialHeight / v.getContext().getResources().getDisplayMetrics().density)/speed_multiplier));
        v.startAnimation(a);
    }
}
