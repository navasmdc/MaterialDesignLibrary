package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomView extends RelativeLayout {


    final static String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    final static String ML_ANIMATED = "MLanimated";
    final static String ML_RIPPLE_COLOR = "MLrippleColor";
    final static String ML_RIPPLE_SPEED = "MLrippleSpeed";
    final static String ML_SHOW_NUMBER_INDICATOR = "MLshowNumberIndicator";
    final static String ML_MAX = "MLmax";
    final static String ML_MIN = "MLmin";
    final static String ML_VALUE = "MLvalue";
    final static String ML_PROGRESS = "MLprogress";
    final static String ML_RING_WIDTH = "MLringWidth";
    final static String ML_CHECKED = "MLchecked";
    final static String ML_CHECKBOX_SIZE = "MLcheckBoxSize";
    final static String ML_THUMB_SIZE = "MLthumbSize";
    final static String ML_ICON_DRAWABLE = "MLiconDrawable";
    final static String ML_ICON_SIZE = "MLiconSize";
    final static String ML_RIPPLE_BORDER_RADIUS = "MLrippleBorderRadius";
    final static String ML_CLICK_AFTER_RIPPLE = "MLclickAfterRipple";

    final int disabledBackgroundColor = Color.parseColor("#E2E2E2");
    int beforeBackground;

    // Indicate if user touched this view the last time
    public boolean isLastTouch = false;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled)
            setBackgroundColor(beforeBackground);
        else
            setBackgroundColor(disabledBackgroundColor);
        invalidate();
    }

    boolean animation = false;

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
        animation = true;
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        animation = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (animation)
            invalidate();
    }
}
