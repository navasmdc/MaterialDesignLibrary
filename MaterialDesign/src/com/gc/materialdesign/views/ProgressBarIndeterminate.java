package com.gc.materialdesign.views;

import com.gc.materialdesign.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ProgressBarIndeterminate extends ProgressBarDetermininate {

	public ProgressBarIndeterminate(Context context, AttributeSet attrs) {
		super(context, attrs);
		post(new Runnable() {
			
			@Override
			public void run() {
				// Make progress animation
				
				setProgress(60);
				Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.progress_indeterminate_animation);
				progressView.startAnimation(anim);
				final ViewPropertyAnimator anim2 = progressView.animate();
				anim2.x(getWidth()).setDuration(1200);
				anim2.setListener(new AnimatorListener() {
					int cont = 1;
					int suma = 1;
					int duration = 1200;
					
					@Override
					public void onAnimationEnd(Animator arg0) {
						// Repeat animation
						progressView.setX(-progressView.getWidth()/2);
						cont += suma;
						progressView.animate().setListener(this).x(getWidth()).setDuration(duration/cont).start();
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
				anim2.start();
			}
		});
	}

}
