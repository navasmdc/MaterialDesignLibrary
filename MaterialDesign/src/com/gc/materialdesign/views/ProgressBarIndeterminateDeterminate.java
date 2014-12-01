package com.gc.materialdesign.views;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;

public class ProgressBarIndeterminateDeterminate extends ProgressBarDetermininate {
	
	boolean firstProgress = true;
	boolean runAnimation = true;
	ObjectAnimator animation;

	public ProgressBarIndeterminateDeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		post(new Runnable() {
			
			@Override
			public void run() {
				// Make progress animation
				
				setProgress(60);
				ViewHelper.setX(progressView,getWidth()+progressView.getWidth()/2);
				animation = ObjectAnimator.ofFloat(progressView, "x", -progressView.getWidth()/2);
				animation.setDuration(1200);
				animation.addListener(new AnimatorListener() {
					
					int cont = 1;
					int suma = 1;
					int duration = 1200;
					
					public void onAnimationEnd(Animator arg0) {
						// Repeat animation
						if(runAnimation){
						ViewHelper.setX(progressView,getWidth()+progressView.getWidth()/2);
						cont += suma;
						animation = ObjectAnimator.ofFloat(progressView, "x", -progressView.getWidth()/2);
						animation.setDuration(duration/cont);
						animation.addListener(this);
						animation.start();
						if(cont == 3 || cont == 1) suma *=-1;
						}
						
					}
					
					public void onAnimationStart(Animator arg0) {}
					public void onAnimationRepeat(Animator arg0) {}
					public void onAnimationCancel(Animator arg0) {}
				});
				animation.start();
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
		animation.cancel();
		ViewHelper.setX(progressView,0);
		runAnimation = false;
	}

}
