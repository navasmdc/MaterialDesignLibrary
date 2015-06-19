package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public abstract class ButtonText extends Button {
    public ButtonText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract TextView getTextView();

    public void setTextColor(int color) {
        getTextView().setTextColor(color);
    }

    public String getText() {
        return getTextView().getText().toString();
    }

    public void setText(String text) {
        getTextView().setText(text);
    }
}
