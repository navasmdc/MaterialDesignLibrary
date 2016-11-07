package com.gc.materialdesign.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;
import com.gc.materialdesign.views.ButtonFlat;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class SnackBar extends Dialog{
	
	String text;
	float textSize = 14;//Roboto Regular 14sp 
	String buttonText;
	View.OnClickListener onClickListener;
	Activity activity;
	View view;
	ButtonFlat button;
	int backgroundSnackBar = Color.parseColor("#333333");
	int backgroundButton = Color.parseColor("#1E88E5");

	View pushUpView;
	
	OnHideListener onHideListener;
	// Timer
	private boolean mIndeterminate = false;
	private int mTimer = 3 * 1000;
	
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
	    ((TextView)findViewById(R.id.text)).setTextSize(textSize); //set textSize
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
		view.post(new Runnable() {
			@Override
			public void run() {
				if(pushUpView != null)
					ViewPropertyAnimator.animate(pushUpView).y(
							ViewHelper.getY(pushUpView) - view.getHeight())
							.setDuration(300)
							.start();
			}
		});

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
			 if(onHideListener != null) {
				 onHideListener.onHide();
			 }
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
		if(pushUpView != null)
			ViewPropertyAnimator.animate(pushUpView).y(
					ViewHelper.getY(pushUpView) + view.getHeight())
					.setDuration(300)
					.start();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK )  {
			 dismiss();
		 }
		return super.onKeyDown(keyCode, event);
	}
	
	public SnackBar setMessageTextSize(float size) {
		textSize = size;
		return this;
	}

	/**
	 * Set true to avoid hide the snackbar
	 * @param indeterminate
     */
	public SnackBar setIndeterminate(boolean indeterminate) {
		mIndeterminate = indeterminate;
		return this;
	}
	
	public boolean isIndeterminate() {
		return mIndeterminate;
	}

	/**
	 * Sets the time to dismiss the snackbar
	 * @param time
     */
	public SnackBar setDismissTimer(int time) {
		mTimer = time;
		return this;
	}
	
	public int getDismissTimer() {
		return mTimer;
	}
	
	/**
	 * Change background color of SnackBar
	 * @param color
	 */
	public SnackBar setBackgroundSnackBar(int color){
		backgroundSnackBar = color;
		if(view != null)
			view.setBackgroundColor(color);
		return this;
	}
	
	/**
	 * Chage color of FlatButton in Snackbar
	 * @param color
	 */
	public SnackBar setColorButton(int color){
		backgroundButton = color;
		if(button != null)
			button.setBackgroundColor(color);
		return this;
	}
	
	/**
	 * This event start when snackbar dismish without push the button
	 * @author Navas
	 *
	 */
	public interface OnHideListener{
		public void onHide();
	}

	/**
	 * Sets the view to push up when the snackbar will show
	 * @param pushUpView
     */
	public SnackBar setPushUpView(View pushUpView){
		this.pushUpView = pushUpView;
		return this;
	}
	
	public SnackBar setOnhideListener(OnHideListener onHideListener){
		this.onHideListener = onHideListener;
		return this;
	}
}
