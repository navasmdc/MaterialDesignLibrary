package com.gc.materialdesign.views;

import com.gc.materialdesign.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

public class ProgressBarCircularIndeterminate extends CustomView {

	public ProgressBarCircularIndeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
	}

	@Override
	protected void onInitDefaultValues() {
		// TODO 自动生成的方法存根
		minWidth = 32;
		minHeight = 32;
		backgroundColor = Color.parseColor("#1E88E5");
	}
	
	@Override
	protected void setAttributes(AttributeSet attrs) {
		// TODO 自动生成的方法存根
		super.setAttributes(attrs);
		float size = 4;// default ring width
		String width = attrs.getAttributeValue(MATERIALDESIGNXML, "ringWidth");
		if (width != null) {
			size = Utils.dipOrDpToFloat(width);
		}
		ringWidth = size;
	}

	/**
	 * Make a dark color to ripple effect
	 * 
	 * @return
	 */
	protected int makePressColor() {
		int r = (this.backgroundColor >> 16) & 0xFF;
		int g = (this.backgroundColor >> 8) & 0xFF;
		int b = (this.backgroundColor >> 0) & 0xFF;
		// r = (r+90 > 245) ? 245 : r+90;
		// g = (g+90 > 245) ? 245 : g+90;
		// b = (b+90 > 245) ? 245 : b+90;
		return Color.argb(128, r, g, b);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (firstAnimationOver == false)
			drawFirstAnimation(canvas);// 从无到有慢慢扩大的动画
		if (cont > 0)
			drawSecondAnimation(canvas);// 一直旋转的动画
		invalidate();
	}

	private float radius1 = 0;
	private float radius2 = 0;
	private int cont = 0;
	private boolean firstAnimationOver = false;
	private float ringWidth = 4;

	/**
	 * Draw first animation of view
	 * 
	 * @param canvas
	 */
	private void drawFirstAnimation(Canvas canvas) {
		if (radius1 < getWidth() / 2) {
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(makePressColor());
			radius1 = (radius1 >= getWidth() / 2) ? (float) getWidth() / 2 : radius1 + 1;
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius1, paint);
		} else {
			Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas temp = new Canvas(bitmap);
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(makePressColor());
			temp.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 2, paint);
			Paint transparentPaint = new Paint();
			transparentPaint.setAntiAlias(true);
			transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
			transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			if (cont >= 50) {
				radius2 = (radius2 >= getWidth() / 2) ? (float) getWidth() / 2 : radius2 + 1;
			} else {
				radius2 = (radius2 >= getWidth() / 2 - Utils.dpToPx(ringWidth, getResources())) ? 
						(float) getWidth() / 2 - Utils.dpToPx(ringWidth, getResources()) : radius2 + 1;
			}
			temp.drawCircle(getWidth() / 2, getHeight() / 2, radius2, transparentPaint);
			canvas.drawBitmap(bitmap, 0, 0, new Paint());
			if (radius2 >= getWidth() / 2 - Utils.dpToPx(ringWidth, getResources()))
				cont++;
			if (radius2 >= getWidth() / 2)
				firstAnimationOver = true;
		}
	}

	private int arcD = 1;
	private int arcO = 0;
	private float rotateAngle = 0;
	private int limite = 0;

	/**
	 * Draw second animation of view
	 * 
	 * @param canvas
	 */
	private void drawSecondAnimation(Canvas canvas) {
		if (arcO == limite)
			arcD += 6;
		if (arcD >= 290 || arcO > limite) {
			arcO += 6;
			arcD -= 6;
		}
		if (arcO > limite + 290) {
			limite = arcO;
			arcO = limite;
			arcD = 1;
		}
		rotateAngle += 4;
		canvas.rotate(rotateAngle, getWidth() / 2, getHeight() / 2);

		Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas temp = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(backgroundColor);
		// temp.drawARGB(0, 0, 0, 255);
		temp.drawArc(new RectF(0, 0, getWidth(), getHeight()), arcO, arcD, true, paint);
		Paint transparentPaint = new Paint();
		transparentPaint.setAntiAlias(true);
		transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
		transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		temp.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() / 2) - Utils.dpToPx(ringWidth, getResources()), transparentPaint);

		canvas.drawBitmap(bitmap, 0, 0, new Paint());
	}

	// Set color of background
	public void setBackgroundColor(int color) {
		super.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		if (isEnabled()) {
			beforeBackground = backgroundColor;
		}
		this.backgroundColor = color;
	}
	
	public void setRingWidth(float width) {
		ringWidth = width;
	}

}
