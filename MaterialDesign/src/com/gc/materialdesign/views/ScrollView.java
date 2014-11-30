package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;


public class ScrollView extends android.widget.ScrollView {
	
	/*
	 * This class avoid problems in scrollviews with elements in library
	 * Use it if you want use a ScrollView in your App
	 * 当你想要在scrollView中放置有涟漪效果的item时，用这个来代替传统的ScrollView
	 */

	public ScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		if(!onInterceptTouchEvent(ev)){
	    	for(int i = 0; i < ((ViewGroup)getChildAt(0)).getChildCount(); i++){
	    		try {
					CustomView child =(CustomView) ((ViewGroup)getChildAt(0)).getChildAt(i);
					if(child.isLastTouch){
						child.onTouchEvent(ev);
						return true;
					}
				} catch (ClassCastException e) {
				}
	        }
//	    }
	    return super.onTouchEvent(ev);
	}
	

}
