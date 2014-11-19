package com.gc.materialdesign.widgets;

import com.gc.materialdesign.R;
import com.gc.materialdesign.views.ButtonFlat;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SnackBar extends Dialog{
	
	String text;
	String buttonText;
	View.OnClickListener onClickListener;
	Activity activity;
	View view;
	ButtonFlat button;
	
	int backgroundSnackBar = Color.parseColor("#333333");
	int backgroundButton = Color.parseColor("#1E88E5");
	
	OnHideListener onHideListener;
	
	// Timer
	private boolean mIndeterminate = false;
	private int mTimer = 4 * 1000;

	// With action button
	public SnackBar(Activity activity, String text, String buttonText, View.OnClickListener onClickListener) {
		super(activity, android.R.style.Theme_Translucent);
		this.activity = activity;
		this.text = text;
		this.buttonText = buttonText;
		this.onClickListener = onClickListener;
	}
	
	// Only text
	public SnackBar(Activity activity, String text) {
		super(activity, android.R.style.Theme_Translucent);
		this.activity = activity;
		this.text = text;
	}
	
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.snackbar);
	    setCanceledOnTouchOutside(false);
	    ((TextView)findViewById(R.id.text)).setText(text);
		button = (ButtonFlat) findViewById(R.id.buttonflat);
		if(text == null || onClickListener == null){
			button.setVisibility(View.GONE);
		}else{
			button.setText(buttonText);
			
			button.setBackgroundColor(backgroundButton);
			
			button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
					onClickListener.onClick(v);
				}
			});
		}
		view = findViewById(R.id.snackbar);
		view.setBackgroundColor(backgroundSnackBar);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return activity.dispatchTouchEvent(event);
	}
	
	@Override
	public void onBackPressed() {
	}
	
	@Override
	public void show() {
		super.show();
		view.setVisibility(View.VISIBLE);
		view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.snackbar_show_animation));
		
		if (!mIndeterminate) {
		    dismissTimer.start();
		}
	}
	
	// Dismiss timer 
	Thread dismissTimer = new Thread(new Runnable() {
		
		@Override
		public void run() {
			try {
				Thread.sleep(mTimer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.sendMessage(new Message());
		}
	});
	
	Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			if(onHideListener != null)
				onHideListener.onHide();
			dismiss();
			return false;
		}
	});
	
	@Override
	public void dismiss() {
		Animation anim = AnimationUtils.loadAnimation(activity, R.anim.snackbar_hide_animation);
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				SnackBar.super.dismiss();
			}
		});
		view.startAnimation(anim);
	}
	
	public void setIndeterminate(boolean indeterminate) {
        	mIndeterminate = indeterminate;
	}
	
	public boolean isIndeterminate() {
		return mIndeterminate;
	}

	public void setDismissTimer(int time) {
		mTimer = time;
	}
	
	public int getDismissTimer() {
		return mTimer;
	}
	
	/**
	 * Change background color of SnackBar
	 * @param color
	 */
	public void setBackgroundSnackBar(int color){
		backgroundSnackBar = color;
		if(view != null)
			view.setBackgroundColor(color);
	}
	
	/**
	 * Chage color of FlatButton in Snackbar
	 * @param color
	 */
	public void setColorButton(int color){
		backgroundButton = color;
		if(button != null)
			button.setBackgroundColor(color);
	}
	
	/**
	 * This event start when snackbar dismish without push the button
	 * @author Navas
	 *
	 */
	public interface OnHideListener{
		public void onHide();
	}
	
	public void setOnhideListener(OnHideListener onHideListener){
		this.onHideListener = onHideListener;
	}
}
