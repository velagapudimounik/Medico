package com.drughub.doctor.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

public class DrughubIcon extends TextView {

    private static Typeface sDrughubIcons = null;

    public DrughubIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public DrughubIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrughubIcon(Context context) {
        super(context);
        init();
    }

    private void init() {
        if(sDrughubIcons == null)
            sDrughubIcons = Typeface.createFromAsset(getContext().getAssets(), "fonts/drughub-mobile-doctor.ttf");
        setTypeface(sDrughubIcons);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewGroup.LayoutParams params = getLayoutParams();
        float textSize = getTextSize();
        params.height = (int)textSize;
        setLayoutParams(params);
    }
}
