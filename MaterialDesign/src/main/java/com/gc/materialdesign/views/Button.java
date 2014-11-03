package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public abstract class Button extends CustomView{
	
final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

	// Complete in child class 
	int minWidth;
	int minHeight;
	int background;
	float rippleSpeed = 10f;
	int rippleSize = 3;

	OnClickListener onClickListener;
	int backgroundColor = Color.parseColor("#1E88E5");
	int disabledBackgroundColor = Color.parseColor("#E2E2E2");
	
	public Button(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDefaultProperties();
		setAttributes(attrs);
	}
	
	protected void setDefaultProperties(){
		// Min size
		setMinimumHeight(Utils.dpToPx(minHeight, getResources()));
		setMinimumWidth(Utils.dpToPx(minWidth, getResources()));
		// Background shape
		setBackgroundResource(background);
		setBackgroundColor(backgroundColor);
	}
	
	// Set atributtes of XML to View
	abstract protected void setAttributes(AttributeSet attrs);
	
	
	// ### RIPPLE EFFECT ###

		float x = -1, y = -1;
		float radius = -1;
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			isLastTouch = true;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				radius = getHeight()/rippleSize;
				x = event.getX();
				y = event.getY();
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				radius = getHeight()/rippleSize;
				x = event.getX();
				y = event.getY();
				if(!((event.getX()<= getWidth() && event.getX() >= 0) && 
						(event.getY()<= getHeight() && event.getY() >= 0))){
					isLastTouch = false;
					x = -1;
					y = -1;
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if((event.getX()<= getWidth() && event.getX() >= 0) && 
						(event.getY()<= getHeight() && event.getY() >= 0)){
					radius++;
				}else{
					isLastTouch = false;
					x = -1;
					y = -1;
				}
			}
			return true;
		}
		
		@Override
			protected void onFocusChanged(boolean gainFocus, int direction,
					Rect previouslyFocusedRect) {
			if(!gainFocus){
				x = -1;
				y = -1;
			}
			}
		
		@Override
			public boolean onInterceptTouchEvent(MotionEvent ev) {
//				super.onInterceptTouchEvent(ev);
				return true;
			}
		
		public Bitmap makeCircle(){
			Bitmap output = Bitmap.createBitmap(getWidth()-Utils.dpToPx(6, getResources()),
		            getHeight()-Utils.dpToPx(7, getResources()), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			canvas.drawARGB(0, 0, 0, 0);
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(makePressColor());
			canvas.drawCircle(x, y, radius, paint);
			if(radius > getHeight()/rippleSize)
				radius += rippleSpeed;
			if(radius >= getWidth()){
				x = -1;
				y = -1;
				radius = getHeight()/rippleSize;
				if(onClickListener != null)
					onClickListener.onClick(this);
			}
			return output;
		}
		
		/**
		 * Make a dark color to ripple effect
		 * @return
		 */
		protected int makePressColor(){
			int r = (this.backgroundColor >> 16) & 0xFF;
			int g = (this.backgroundColor >> 8) & 0xFF;
			int b = (this.backgroundColor >> 0) & 0xFF;
			r = (r-30 < 0) ? 0 : r-30;
			g = (g-30 < 0) ? 0 : g-30;
			b = (b-30 < 0) ? 0 : b-30;
			return Color.rgb(r, g, b);		
		}
		
		@Override
		public void setOnClickListener(OnClickListener l) {
			onClickListener = l;
		}
		
		// Set color of background
		public void setBackgroundColor(int color){
			this.backgroundColor = color;
			LayerDrawable layer = (LayerDrawable) getBackground();
			GradientDrawable shape =  (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
			shape.setColor(backgroundColor);
		}
		
		abstract public TextView getTextView();
}
