package com.gc.materialdesign.views;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;

public class ProgressBarIndeterminateDeterminate extends ProgressBarDeterminate {

	boolean firstProgress = true;
	boolean runAnimation = true;
	int duration = 2000;
	ObjectAnimator animation;
	Runnable animationRunnable = new Runnable() {

		@Override
		public void run() {
			// Make progress animation

			setProgress(60);
			ViewHelper.setX(progressView,getWidth());
			animation = ObjectAnimator.ofFloat(progressView, "x", getWidth());
			animation.setDuration(0);
			animation.addListener(new AnimatorListener() {

				public void onAnimationEnd(Animator arg0) {
					// Repeat animation
					if(runAnimation){
						ViewHelper.setX(progressView,getWidth());
						animation = ObjectAnimator.ofFloat(progressView, "x", -progressView.getWidth());
						animation.setDuration(duration);
						animation.addListener(this);
						animation.start();
					}

				}

				public void onAnimationStart(Animator arg0) {}
				public void onAnimationRepeat(Animator arg0) {}
				public void onAnimationCancel(Animator arg0) {}
			});
			animation.start();
		}
	};


	public ProgressBarIndeterminateDeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		post(animationRunnable);
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
		if (animation != null)
		{
			animation.cancel();
			ViewHelper.setX(progressView, 0);
			runAnimation = false;
		}
	}

	public void reset()
	{
		runAnimation = true;
		firstProgress = true;
		post(animationRunnable);
	}

}
