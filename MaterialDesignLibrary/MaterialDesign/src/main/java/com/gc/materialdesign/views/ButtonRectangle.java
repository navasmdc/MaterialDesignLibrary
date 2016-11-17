package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.AttributesUtils;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ButtonRectangle extends Button {

    TextView textButton;

    int paddingTop, paddingBottom, paddingLeft, paddingRight;


    public ButtonRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultProperties();
    }

    @Override
    protected void setDefaultProperties() {
        paddingBottom = Utils.dpToPx(16, getResources());
        paddingLeft = Utils.dpToPx(16, getResources());
        paddingRight = Utils.dpToPx(16, getResources());
        paddingTop = Utils.dpToPx(16, getResources());
        super.minWidth = 80;
        super.minHeight = 36;
        super.background = R.drawable.background_button_rectangle;
        super.setDefaultProperties();
    }


    // Set atributtes of XML to View
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


    public void setTextSize(float size){
        if (getTextView() != null)
        getTextView().setTextSize(size);
    }
//	/**
//	 * Center text in button
//	 */
//	boolean txtCenter = false;
//	private void centrarTexto(){
//		if((textButton.getWidth()+paddingLeft+paddingRight)>Utils.dpToPx(80, getResources()))
//			setMinimumWidth(textButton.getWidth()+paddingLeft+paddingRight);
//		setMinimumHeight(textButton.getHeight()+paddingBottom+paddingTop);
//		textButton.setX(getWidth()/2-textButton.getWidth()/2 - paddingTop + paddingBottom);
//		textButton.setY(getHeight()/2-textButton.getHeight()/2 - paddingLeft + paddingRight);
//		txtCenter = true;
//	}

    Integer height;
    Integer width;

    @Override
    protected void onDraw(Canvas canvas) {
//		if(!txtCenter)
//		centrarTexto();
        super.onDraw(canvas);
        if (x != -1) {
            Rect src = new Rect(0, 0, getWidth() - Utils.dpToPx(6, getResources()), getHeight() - Utils.dpToPx(7, getResources()));
            Rect dst = new Rect(Utils.dpToPx(6, getResources()), Utils.dpToPx(6, getResources()), getWidth() - Utils.dpToPx(6, getResources()), getHeight() - Utils.dpToPx(7, getResources()));
            canvas.drawBitmap(makeCircle(), src, dst, null);
            invalidate();
        }
    }

}
