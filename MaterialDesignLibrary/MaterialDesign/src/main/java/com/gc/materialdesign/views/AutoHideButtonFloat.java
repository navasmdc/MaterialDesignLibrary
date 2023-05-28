package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import com.gc.materialdesign.views.ButtonFloat;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by hamedpc on 4/25/2015.
 * This widget uses NineOldAndroid to animate the view so you're gonna need to include it in your project
 */
public class AutoHideButtonFloat extends ButtonFloat implements AbsListView.OnScrollListener {

    ListView listView;

    private boolean floatHiding = false, floatShowing = false;

    private int mLastFirstVisibleItem;

    private View view = this;

    private AbsListView.OnScrollListener onScrollListener;

    public AutoHideButtonFloat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
        this.listView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        switch(scrollState) {
            case AbsListView.SCROLL_AXIS_NONE:
                floatHiding = false;
                floatShowing = false;
                ViewPropertyAnimator.animate(view).translationY(0).setDuration(300);
                break;
        }
        if (onScrollListener != null)
            onScrollListener.onScrollStateChanged(absListView, scrollState);
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mLastFirstVisibleItem < firstVisibleItem) {
            if (floatShowing)
                floatShowing = false;
            if (!floatHiding) {
                ViewPropertyAnimator.animate(view).translationY(500).setDuration(300);
                floatHiding = true;
            }
        }
        if (mLastFirstVisibleItem > firstVisibleItem) {
            if (floatHiding) {
                floatHiding = false;
            }
            if (!floatShowing) {
                ViewPropertyAnimator.animate(view).translationY(0).setDuration(300);
                floatShowing = true;
            }
        }
        mLastFirstVisibleItem = firstVisibleItem;
        if (onScrollListener != null)
            onScrollListener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
    }
}
