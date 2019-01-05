package com.pwr.bzapps.plwordnetmobile.layout.listener;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.layout.animator.ExpandableViewAnimator;

public class OnClickExpander implements View.OnClickListener {
    private boolean isOpen, isButtonBellow;
    private View container;
    private View button;
    private Context context;
    private float speed_open, speed_close;

    public OnClickExpander(boolean isOpen, View container, View button, Context context){
        this.isOpen=isOpen;
        this.container=container;
        this.button=button;
        this.context=context;
        this.speed_close = 0.5f;
        this.speed_open = 0.33f;
        this.isButtonBellow=false;
    }

    public OnClickExpander(boolean isOpen, View container, View button, Context context, float speed_open, float speed_close){
        this.isOpen=isOpen;
        this.container=container;
        this.button=button;
        this.context=context;
        this.speed_close = speed_close;
        this.speed_open = speed_open;
        this.isButtonBellow=false;
    }

    public OnClickExpander(boolean isOpen, View container, View button, Context context, float speed_open, float speed_close, boolean isButtonBellow){
        this.isOpen=isOpen;
        this.container=container;
        this.button=button;
        this.context=context;
        this.speed_close = speed_close;
        this.speed_open = speed_open;
        this.isButtonBellow=isButtonBellow;
    }

    @Override
    public void onClick(View view) {
        if (isOpen) {
            isOpen = !isOpen;
            ExpandableViewAnimator.collapse(container,speed_close);
            button.startAnimation(getRotateAnimation());

        } else {
            isOpen = !isOpen;
            ExpandableViewAnimator.expand(container,speed_open);
            button.startAnimation(getRotateAnimation());
        }
    }

    private Animation getRotateAnimation(){
        Animation rotateAnimation = null;
        if(isOpen){
            //if(isButtonBellow){
                rotateAnimation = (Animation) AnimationUtils.loadAnimation(context,R.anim.rotate_180_anim);
                rotateAnimation.setFillBefore(true);
                rotateAnimation.setFillAfter(true);
            //}
            //else{
            //    rotateAnimation = (Animation) AnimationUtils.loadAnimation(context, R.anim.rotate_back_180_anim);
            //    rotateAnimation.setFillBefore(true);
            //    rotateAnimation.setFillAfter(true);
            //}
        }
        else{
            //if(isButtonBellow){
                rotateAnimation = (Animation) AnimationUtils.loadAnimation(context, R.anim.rotate_back_180_anim);
                rotateAnimation.setFillBefore(true);
                rotateAnimation.setFillAfter(true);
            //}
            //else{
            //    rotateAnimation = (Animation) AnimationUtils.loadAnimation(context,R.anim.rotate_180_anim);
            //    rotateAnimation.setFillBefore(true);
            //    rotateAnimation.setFillAfter(true);
            //}
        }
        return rotateAnimation;
    }
}
