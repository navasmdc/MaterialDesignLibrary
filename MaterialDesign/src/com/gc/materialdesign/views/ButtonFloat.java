package com.gc.materialdesign.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class ButtonFloat extends Button{
	
	int sizeIcon = 24;
	int sizeRadius = 28;
	
	
	ImageView icon; // Icon of float button
	Drawable drawableIcon;
	
	public ButtonFloat(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.drawable.background_button_float);
		sizeRadius = 28;
		setDefaultProperties();
		icon = new ImageView(context);
		if(drawableIcon != null) {
			try {
				icon.setBackground(drawableIcon);
			} catch (NoSuchMethodError e) {
				icon.setBackgroundDrawable(drawableIcon);
			}
		}
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utils.dpToPx(sizeIcon, getResources()),Utils.dpToPx(sizeIcon, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		icon.setLayoutParams(params);
		addView(icon);		
		
	}
	
	protected void setDefaultProperties(){
		rippleSpeed = Utils.dpToPx(2, getResources());
		rippleSize = Utils.dpToPx(5, getResources());
		super.minWidth = sizeRadius*2;
		super.minHeight = sizeRadius*2;
		super.background = R.drawable.background_button_float;
		super.setDefaultProperties();
	}
	
	
	// Set atributtes of XML to View
	protected void setAttributes(AttributeSet attrs){
		//Set background Color
		// Color by resource
		int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML,"background",-1);
		if(bacgroundColor != -1){
			setBackgroundColor(getResources().getColor(bacgroundColor));
		}else{
			// Color by hexadecimal
			String background = attrs.getAttributeValue(ANDROIDXML,"background");
			if(background != null)
				setBackgroundColor(Color.parseColor(background));
		}
		// Icon of button
		int iconResource = attrs.getAttributeResourceValue(MATERIALDESIGNXML,"icon",-1);
		if(iconResource != -1)
			drawableIcon = getResources().getDrawable(iconResource);
		boolean animate = attrs.getAttributeBooleanValue(MATERIALDESIGNXML,"animate", false);
		if(animate){
			post(new Runnable() {
				
				@Override
				public void run() {
					float originalY = ViewHelper.getY(ButtonFloat.this) - Utils.dpToPx(24, getResources());
					ViewHelper.setY(ButtonFloat.this, ViewHelper.getY(ButtonFloat.this) + getHeight() * 3);
					ObjectAnimator animator = ObjectAnimator.ofFloat(ButtonFloat.this, "y", originalY);
					animator.setInterpolator(new BounceInterpolator());
					animator.setDuration(1500);
					animator.start();
				}
			});
		}
					
	}
		
	Integer height;
	Integer width;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (x != -1) {
			Rect src = new Rect(0, 0, getWidth(), getHeight());
			Rect dst = new Rect(Utils.dpToPx(1, getResources()), Utils.dpToPx(2, getResources()), getWidth()-Utils.dpToPx(1, getResources()), getHeight()-Utils.dpToPx(2, getResources()));
			canvas.drawBitmap(cropCircle(makeCircle()), src, dst, null);
		}
		invalidate();
	}
	
	
	
	
	public ImageView getIcon() {
		return icon;
	}

	public void setIcon(ImageView icon) {
		this.icon = icon;
	}

	public Drawable getDrawableIcon() {
		return drawableIcon;
	}

	public void setDrawableIcon(Drawable drawableIcon) {
		this.drawableIcon = drawableIcon;
		icon.setBackground(drawableIcon);
	}

	public Bitmap cropCircle(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	            bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
	            bitmap.getWidth()/2, paint);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	    return output;
	}

	@Override
	public TextView getTextView() {
		return null;
	}
}
