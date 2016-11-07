package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
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
    protected void setAttributes(AttributeSet attrs) {

        //Set background Color
        // Color by resource
        int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1);
        if (bacgroundColor != -1) {
            setBackgroundColor(getResources().getColor(bacgroundColor));
        } else {
            // Color by hexadecimal
            // Color by hexadecimal
            background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
            if (background != -1)
                setBackgroundColor(background);
        }

        // Set Padding
        String value = attrs.getAttributeValue(ANDROIDXML, "padding");
        if (value != null) {
            float padding = Float.parseFloat(value.replace("dip", ""));
            paddingBottom = Utils.dpToPx(padding, getResources());
            paddingLeft = Utils.dpToPx(padding, getResources());
            paddingRight = Utils.dpToPx(padding, getResources());
            paddingTop = Utils.dpToPx(padding, getResources());
        } else {
            value = attrs.getAttributeValue(ANDROIDXML, "paddingLeft");
            paddingLeft = (value == null) ? paddingLeft : (int) Float.parseFloat(value.replace("dip", ""));
            value = attrs.getAttributeValue(ANDROIDXML, "paddingTop");
            paddingTop = (value == null) ? paddingTop : (int) Float.parseFloat(value.replace("dip", ""));
            value = attrs.getAttributeValue(ANDROIDXML, "paddingRight");
            paddingRight = (value == null) ? paddingRight : (int) Float.parseFloat(value.replace("dip", ""));
            value = attrs.getAttributeValue(ANDROIDXML, "paddingBottom");
            paddingBottom = (value == null) ? paddingBottom : (int) Float.parseFloat(value.replace("dip", ""));
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        // Set text button
        String text = null;
        int textResource = attrs.getAttributeResourceValue(ANDROIDXML, "text", -1);
        if (textResource != -1) {
            text = getResources().getString(textResource);
        } else {
            text = attrs.getAttributeValue(ANDROIDXML, "text");
        }

        textButton = new TextView(getContext());
        if (text != null) {
            textButton.setText(text);
            textButton.setTextColor(Color.WHITE);
            textButton.setTypeface(null, Typeface.BOLD);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            params.setMargins(Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()));
            textButton.setLayoutParams(params);
            addView(textButton);
//					FrameLayout.LayoutParams params = (LayoutParams) textView.getLayoutParams();
//					params.width = getWidth();
//					params.gravity = Gravity.CENTER_HORIZONTAL;
////					params.setMargins(paddingLeft, paddingTop, paddingRight, paddingRight);
//					textView.setLayoutParams(params);textColor
            int textColor = attrs.getAttributeResourceValue(ANDROIDXML, "textColor", -1);
            if (textColor != -1) {
                textButton.setTextColor(textColor);
            } else {
                // Color by hexadecimal
                // Color by hexadecimal
                textColor = attrs.getAttributeIntValue(ANDROIDXML, "textColor", -1);
                if (textColor != -1)
                    textButton.setTextColor(textColor);
            }
            int[] array = {android.R.attr.textSize};
            TypedArray values = getContext().obtainStyledAttributes(attrs, array);
            float textSize = values.getDimension(0, -1);
            values.recycle();
            if (textSize != -1)
                textButton.setTextSize(textSize);

        }

        rippleSpeed = attrs.getAttributeFloatValue(MATERIALDESIGNXML,
                "rippleSpeed", Utils.dpToPx(6, getResources()));
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
