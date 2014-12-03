package com.gc.materialdesign.views;

import com.gc.materialdesign.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class ProgressBarDeterminate extends CustomView {

	private int max = 100;
	private int min = 0;
	private int progress = 0;

	protected View progressView;

	public ProgressBarDeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
	}

	@Override
	protected void onInitDefaultValues() {
		minWidth = 40;
		minHeight = 3;
		backgroundColor = Color.parseColor("#1E88E5");
	}

	// Set atributtes of XML to View
	@Override
	protected void setAttributes(AttributeSet attrs) {
		progressView = new View(getContext());
		RelativeLayout.LayoutParams params = new LayoutParams(1, 1);
		progressView.setLayoutParams(params);
		if (!isInEditMode()) {
			progressView.setBackgroundResource(R.drawable.background_progress);
		}
		addView(progressView);
		//super必须放在view初始化后，英文setAttr会执行设置背景的方法，如果view还没产生就设置背景会报null
		super.setAttributes(attrs);
		min = attrs.getAttributeIntValue(MATERIALDESIGNXML, "min", 0);
		max = attrs.getAttributeIntValue(MATERIALDESIGNXML, "max", 100);
		progress = attrs.getAttributeIntValue(MATERIALDESIGNXML, "progress", min);

		post(new Runnable() {
			@Override
			public void run() {
				RelativeLayout.LayoutParams params = (LayoutParams) progressView.getLayoutParams();
				params.height = getHeight();
				progressView.setLayoutParams(params);
				//setProgress(progress);
			}
		});
	}

	// SETTERS

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (pendindProgress != -1)
			setProgress(pendindProgress);
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMax() {
		return max;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public int getMin() {
		return min;
	}
	
	private int pendindProgress = -1;

	public void setProgress(int progress) {
		this.progress = progress;
		if (getWidth() == 0) {
			pendindProgress = progress;
		} else {
			//this.progress = progress;
			if (progress > max)
				progress = max;
			if (progress < min)
				progress = min;
			int totalWidth = max - min;
			double progressPercent = (double) progress / (double) totalWidth;
			int progressWidth = (int) (getWidth() * progressPercent);
			RelativeLayout.LayoutParams params = (LayoutParams) progressView.getLayoutParams();
			params.width = progressWidth;
			params.height = getHeight();
			progressView.setLayoutParams(params);
			pendindProgress = -1;
		}
	}

	public int getProgress() {
		return progress;
	}

	// Set color of background
	public void setBackgroundColor(int color) {
		backgroundColor = color;
		if (!isInEditMode()) {
			if (isEnabled()) {
				beforeBackground = backgroundColor;
			}
			LayerDrawable layer = (LayerDrawable) progressView.getBackground();
			GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
			shape.setColor(color);
		}
		super.setBackgroundColor(makePressColor(128));
	}
	
	/**
	 * Make a dark color to ripple effect
	 * @return
	 */
	@Override
	protected int makePressColor(int alpha){
		int r = (this.backgroundColor >> 16) & 0xFF;
		int g = (this.backgroundColor >> 8) & 0xFF;
		int b = (this.backgroundColor >> 0) & 0xFF;
		return Color.argb(alpha,r, g, b);		
	}

}
