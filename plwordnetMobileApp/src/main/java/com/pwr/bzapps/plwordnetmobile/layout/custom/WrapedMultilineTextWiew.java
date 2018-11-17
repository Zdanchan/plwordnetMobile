package com.pwr.bzapps.plwordnetmobile.layout.custom;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class WrapedMultilineTextWiew extends android.support.v7.widget.AppCompatTextView {

    public WrapedMultilineTextWiew(Context context) {
        super(context);
    }

    public WrapedMultilineTextWiew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapedMultilineTextWiew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout != null) {
            int width = (int) Math.ceil(getMaxLineWidth(layout))
                    + getCompoundPaddingLeft() + getCompoundPaddingRight();
            int height = getMeasuredHeight();
            setMeasuredDimension(width, height);
        }
    }

    private float getMaxLineWidth(Layout layout) {
        float max_width = 0.0f;
        int lines = layout.getLineCount();
        for (int i = 0; i < lines; i++) {
            if (layout.getLineMax(i) > max_width) {
                max_width = layout.getLineMax(i);
            }
        }
        return max_width;
    }
}
