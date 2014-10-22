package com.gc.materialdesign.views;


import com.gc.materialdesign.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ProgressBarIndeterminate extends ProgressBarDetermininate {
   
	ObjectAnimator objectAnimator ;
	public ProgressBarIndeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		post(new Runnable() {
			
			@Override
			public void run() {
				// Make progress animation
				
				setProgress(60);
				Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.progress_indeterminate_animation);
				progressView.startAnimation(anim);
			    objectAnimator = ObjectAnimator.ofFloat(progressView, "x", getWidth());
				objectAnimator.setDuration(1200);
				objectAnimator.addListener(new AnimatorListener() {
					int cont = 1;
					int suma = 1;
					int duration = 1200;
					
					@Override
					public void onAnimationEnd(Animator arg0) {
						// Repeat animation
						ViewHelper.setX(progressView, -progressView.getWidth() / 2);
						cont += suma;
						objectAnimator.setFloatValues(getWidth());
						objectAnimator.setDuration(duration / cont);
						objectAnimator.addListener(this);
						objectAnimator.start();
						if(cont == 3 || cont == 1) suma *=-1;
					}
					
					@Override
					public void onAnimationStart(Animator arg0) {
					}
					@Override
					public void onAnimationRepeat(Animator arg0) {
					}
					@Override
					public void onAnimationCancel(Animator arg0) {}
				});
				objectAnimator.start();
			}
		});
	}

}
