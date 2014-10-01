package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.R.anim;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ProgressBarIndeterminateDeterminate extends ProgressBarDetermininate {
	
	boolean firstProgress = true;
	boolean runAnimation = true;
	ViewPropertyAnimator animation;
	
	

	public ProgressBarIndeterminateDeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		post(new Runnable() {
			
			@Override
			public void run() {
				// Make progress animation
				
				setProgress(60);
				progressView.setX(getWidth()+progressView.getWidth()/2);
				progressView.setX(getWidth()+progressView.getWidth()/2);
				animation = progressView.animate();
				animation.x(-progressView.getWidth()/2).setDuration(1200);
				animation.setListener(new AnimatorListener() {
					int cont = 1;
					int suma = 1;
					int duration = 1200;
					
					@Override
					public void onAnimationEnd(Animator arg0) {
						// Repeat animation
						if(runAnimation){
							progressView.setX(getWidth()+progressView.getWidth()/2);
							cont += suma;
							animation = progressView.animate();
							animation.setListener(this).x(-progressView.getWidth()/2).setDuration(duration/cont).start();
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
		progressView.setX(0);
		runAnimation = false;
	}

}
