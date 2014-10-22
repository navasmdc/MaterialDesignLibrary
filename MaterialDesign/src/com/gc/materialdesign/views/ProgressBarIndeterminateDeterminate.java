package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class ProgressBarIndeterminateDeterminate extends ProgressBarDetermininate {
	
	boolean firstProgress = true;
	boolean runAnimation = true;
	
	ObjectAnimator animator;

	public ProgressBarIndeterminateDeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		post(new Runnable() {
			
			@Override
			public void run() {
				// Make progress animation
				
				setProgress(60);
				ViewHelper.setX(progressView, getWidth() + progressView.getWidth() / 2);
				animator = ObjectAnimator.ofFloat(progressView, "x", -progressView.getWidth() / 2);
				animator.setDuration(1200);
				animator.addListener(new AnimatorListener() {
					int cont = 1;
					int suma = 1;
					int duration = 1200;
					
					@Override
					public void onAnimationEnd(Animator arg0) {
						// Repeat animation
						if(runAnimation){
							ViewHelper.setX(progressView, getWidth() + progressView.getWidth() / 2);
							cont += suma;
							animator.setFloatValues(-progressView.getWidth() / 2);
							animator.setDuration(duration / cont);
							animator.addListener(this);
							animator.start();
							if(cont == 3 || cont == 1) suma *=-1;
						}
					}
					
					@Override
					public void onAnimationStart(Animator arg0) {}
					@Override
					public void onAnimationRepeat(Animator arg0) {}
					@Override
					public void onAnimationCancel(Animator arg0) {}
				});
				animator.start();
			}
		});
	}
	
	@Override
	public void setProgress(int progress) {
		if(firstProgress){
			firstProgress = false;
		}else{
			stopIndeterminate();
		}
		super.setProgress(progress);
	}
	
	/**
	 * Stop indeterminate animation to convert view in determinate progress bar
	 */
	private void stopIndeterminate(){
		animator.cancel();
		ViewHelper.setX(progressView, 0);
		runAnimation = false;
	}

}
