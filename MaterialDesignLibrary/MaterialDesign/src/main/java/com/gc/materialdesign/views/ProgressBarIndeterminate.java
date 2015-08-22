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

public class ProgressBarIndeterminate extends ProgressBarDeterminate {

	public ProgressBarIndeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		post(new Runnable() {
			
			@Override
			public void run() {
				// Make progress animation
				
				setProgress(60);
				Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.progress_indeterminate_animation);
				progressView.startAnimation(anim);
				final ObjectAnimator anim2 = ObjectAnimator.ofFloat(progressView, "x", getWidth());
				anim2.setDuration(1200);
				anim2.addListener(new AnimatorListener() {
					
					int cont = 1;
					int suma = 1;
					int duration = 1200;
					
					public void onAnimationEnd(Animator arg0) {
						// Repeat animation
						ViewHelper.setX(progressView,-progressView.getWidth()/2);
						cont += suma;
						try {
							ObjectAnimator anim2Repeat = ObjectAnimator.ofFloat(progressView, "x", getWidth());
							anim2Repeat.setDuration(duration/cont);
							anim2Repeat.addListener(this);
							anim2Repeat.start();
						} catch (ArrayIndexOutOfBoundsException e) {
							// ignore this error that sometimes comes from the NineOldAndroids 2.4 library
						}
						if(cont == 3 || cont == 1) suma *=-1;
						
					}
					
					public void onAnimationStart(Animator arg0) {}
					public void onAnimationRepeat(Animator arg0) {}
					public void onAnimationCancel(Animator arg0) {}
				});
						
				anim2.start();
			}
		});
	}

}
