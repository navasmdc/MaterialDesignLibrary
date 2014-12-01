package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @tips  :矩形按钮
 * @date  :2014-11-1
 */
public class ButtonRectangle extends Button {
	
	protected TextView textButton;
	protected int defaultTextColor = Color.WHITE;
	
	public ButtonRectangle(Context context, AttributeSet attrs) {
		super(context, attrs);//先运行setDefaultProperties，然后运行setAttributes
	}
	
	@Override
	protected void onInitDefaultValues(){
		backgroundResId = R.drawable.background_button_rectangle;
		super.onInitDefaultValues();
		//涟漪的速度，这里的5.5可以调整
		rippleSpeed = 5.5f;
		minWidth = 80;
		minHeight = 36;
	}
	
	// Set atributtes of XML to View
	@Override
	protected void onInitAttributes(AttributeSet attrs){
		super.onInitAttributes(attrs);
		String text = null;
		/**
		 * 设置按钮上的文字内容
		 */
		int textResource = attrs.getAttributeResourceValue(ANDROIDXML,"text",-1);
		if(textResource != -1){
			text = getResources().getString(textResource);
		}else{
			//如果没有文字资源，也就是@String/xx，那么就设置文字
			text = attrs.getAttributeValue(ANDROIDXML,"text");
		}
		
		/**
		 * 当文字不为空的时候，TextView设置文字，否则不设置文字
		 */
		if(text != null){
			textButton = new TextView(getContext());
			textButton.setText(text);
			textButton.setTextColor(defaultTextColor);//默认的文字颜色
			textButton.setTypeface(null, Typeface.BOLD);
			//textButton.setPadding(12, 12, 12, 12);//内边距
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			params.setMargins(Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()));
			textButton.setLayoutParams(params);
			//设置好各种属性后，添加穿件好的控件到view中
			addView(textButton);
		}
		
		/**
		 * 设置textSize
		 */
		String textSize = attrs.getAttributeValue(ANDROIDXML,"textSize");
		if (text != null && textSize != null) {
			textSize = textSize.substring(0, textSize.length() - 2);//12sp->12
			textButton.setTextSize(Float.parseFloat(textSize));
		}
		
		/**
		 * 设置textColor
		 */
		int textColor = attrs.getAttributeResourceValue(ANDROIDXML,"textColor",-1);
		if(text != null && textColor != -1){
			textButton.setTextColor(getResources().getColor(textColor));
		}
		else if(text != null ){
			// 16进制的color
			String color = attrs.getAttributeValue(ANDROIDXML,"textColor");
			if(color != null && !isInEditMode()) {
				textButton.setTextColor(Color.parseColor(color));
			}else if(isInEditMode()){
				textButton.setTextColor(Color.WHITE);//默认的文字颜色
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (x != -1) {
			Rect src = new Rect(0, 0, getWidth()-Utils.dpToPx(6, getResources()), getHeight()-Utils.dpToPx(7, getResources()));
			Rect dst = new Rect(Utils.dpToPx(6, getResources()), Utils.dpToPx(6, getResources()), getWidth()-Utils.dpToPx(6, getResources()), getHeight()-Utils.dpToPx(7, getResources()));
			canvas.drawBitmap(makeCircle(), src, dst, null);
		}
		invalidate();
	}
	
	// GET AND SET
	
	public void setText(String text){
		textButton.setText(text);
	}
	
	// Set color of text
	public void setTextColor(int color){
		textButton.setTextColor(color);
	}
	
	public void setTextSize(float size) {
		textButton.setTextSize(size);
	}

	@Override
	public TextView getTextView() {
		return textButton;
	}

}
