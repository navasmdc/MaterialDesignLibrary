package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.AttributesUtils;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ButtonFlat extends Button {

    int paddingTop, paddingBottom, paddingLeft, paddingRight;

	public ButtonFlat(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	protected void setDefaultProperties(){
		minHeight = 36;
		minWidth = 88;
		rippleSize = 3;
		paddingBottom = Utils.dpToPx(16, getResources());
		paddingLeft = Utils.dpToPx(16, getResources());
		paddingRight = Utils.dpToPx(16, getResources());
		paddingTop = Utils.dpToPx(16, getResources());
		// Min size
		setMinimumHeight(Utils.dpToPx(minHeight, getResources()));
		setMinimumWidth(Utils.dpToPx(minWidth, getResources()));
		setBackgroundResource(R.drawable.background_transparent);
	}

	@Override
	protected void setAttributes(AttributeSet attrs, TypedArray typedArray) {

        //Set background Color
        // Color by resource
        int bacgroundColor = AttributesUtils.getBackgroundColor(getResources(), attrs, typedArray);
        if(bacgroundColor != -1) setBackgroundColor(bacgroundColor);

        // Set Padding
        int[] paddings = {paddingLeft,paddingTop,paddingRight,paddingBottom};
        paddings = AttributesUtils.getPadding(getResources(),attrs,typedArray,paddings);
        setPadding(paddings[0], paddings[1], paddings[2], paddings[3]);
        // Set text button
        textButton = new TextView(getContext());
        String text = AttributesUtils.getText(getResources(), attrs, typedArray);
        if(text != null) textButton.setText(text);
        int textColor = AttributesUtils.getTextColor(getResources(), attrs, typedArray);
        textButton.setTextColor((textColor == -1) ? Color.WHITE : textColor);
        textButton.setTypeface(null, Typeface.BOLD);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        params.setMargins(Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()));
        textButton.setLayoutParams(params);
        textButton.setGravity(Gravity.CENTER);
        addView(textButton);
        int[] array = {android.R.attr.textSize};
        TypedArray values = getContext().obtainStyledAttributes(attrs, array);
        float textSize = AttributesUtils.getTextSize(getResources(), values, typedArray);
        if (textSize != -1)
            textButton.setTextSize(textSize);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x != -1) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(makePressColor());
            canvas.drawCircle(x, y, radius, paint);
            if (radius > getHeight() / rippleSize)
                radius += rippleSpeed;
            if (radius >= getWidth()) {
                x = -1;
                y = -1;
                radius = getHeight() / rippleSize;
                if (onClickListener != null && clickAfterRipple)
                    onClickListener.onClick(this);
            }
            invalidate();
        }

    }

    public void setTextSize(float size){
        if (getTextView() != null)
            getTextView().setTextSize(size);
    }

    /**
     * Make a dark color to ripple effect
     *
     * @return
     */
    @Override
    protected int makePressColor() {
        return Color.parseColor("#88DDDDDD");
    }

    public void setText(String text) {
        textButton.setText(text.toUpperCase());
    }

    // Set color of background
    public void setBackgroundColor(int color) {
        backgroundColor = color;
        if (isEnabled())
            beforeBackground = backgroundColor;
        textButton.setTextColor(color);
    }

    @Override
    public TextView getTextView() {
        return textButton;
    }

    public String getText() {
        return textButton.getText().toString();
    }

}
