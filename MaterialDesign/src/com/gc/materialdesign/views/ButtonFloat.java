package com.gc.materialdesign.views;

import com.gc.materialdesign.R;
import com.gc.materialdesign.utils.Utils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author:Jack Tony
 * @tips :正常的圆形悬浮按钮，默认sizeIcon = 24;sizeRadius = 28;
 * @date :2014-11-1
 */
public class ButtonFloat extends Button {

	protected int iconSize;// 内部图片大小
	protected int sizeRadius;// 图标半径

	ImageView icon; // 按钮中的ImageView 
	Drawable iconDrawable;// imageView中的drawable

	public ButtonFloat(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onInitDefaultValues() {
		backgroundResId = R.drawable.background_button_float;
		super.onInitDefaultValues();
		iconSize = 24;
		sizeRadius = 28;
		rippleSpeed = 3;
		rippleSize = 5;
		minWidth = sizeRadius * 2;// 56dp
		minHeight = sizeRadius * 2;// 56dp
	}

	// 将xml文件中的属性设置到view中
	@Override
	protected void onInitAttributes(AttributeSet attrs) {
		super.onInitAttributes(attrs);
		// 设置按钮中的图标
		int iconResource = attrs.getAttributeResourceValue(MATERIALDESIGNXML,"iconFloat",-1);
		if (iconResource != -1) {
			iconDrawable = getResources().getDrawable(iconResource);
		}

		// animation
		boolean animate = attrs.getAttributeBooleanValue(MATERIALDESIGNXML, "animate", false);
		if (animate) {
			post(new Runnable() {
				@Override
				public void run() {
					float originalY = ViewHelper.getY(ButtonFloat.this) - Utils.dpToPx(24, getResources());
					ViewHelper.setY(ButtonFloat.this, 
							ViewHelper.getY(ButtonFloat.this) + getHeight() * 3);
					ObjectAnimator animator = ObjectAnimator.ofFloat(ButtonFloat.this, "y", originalY);
					animator.setInterpolator(new BounceInterpolator());
					animator.setDuration(1500);// 动画持续时间
					animator.start();
				}
			});
		}

		icon = new ImageView(getContext());
		if (iconDrawable != null) {
			icon.setBackgroundDrawable(iconDrawable);
		}
		// 设置按钮中图标的大小
		String size = attrs.getAttributeValue(MATERIALDESIGNXML, "iconSize");
		if (size != null) {
			iconSize = (int) Utils.dipOrDpToFloat(size);
		}
		setIconParams();
		addView(icon);
	}

	private void setIconParams() {
		// TODO 自动生成的方法存根
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				Utils.dpToPx(iconSize, getResources()), Utils.dpToPx(iconSize, getResources()));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		icon.setLayoutParams(params);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (x != -1) {
			Rect src = new Rect(0, 0, getWidth(), getHeight());
			Rect dst = new Rect(Utils.dpToPx(1, getResources()), 
					Utils.dpToPx(2, getResources()), 
					getWidth() - Utils.dpToPx(1, getResources()), 
					getHeight() - Utils.dpToPx(2, getResources()));
			canvas.drawBitmap(cropCircle(makeCircle()), src, dst, null);
		}
		invalidate();
	}

	// 主要用于将涟漪的范围限制在圆圈内
	public Bitmap cropCircle(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				bitmap.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	// GET AND SET
	
	public void isAnimate(boolean isAnimate) {
		if (isAnimate) {
			post(new Runnable() {
				@Override
				public void run() {
					float originalY = ViewHelper.getY(ButtonFloat.this) - Utils.dpToPx(24, getResources());
					ViewHelper.setY(ButtonFloat.this, 
							ViewHelper.getY(ButtonFloat.this) + getHeight() * 3);
					ObjectAnimator animator = ObjectAnimator.ofFloat(ButtonFloat.this, "y", originalY);
					animator.setInterpolator(new BounceInterpolator());
					animator.setDuration(1500);// 动画持续时间
					animator.start();
				}
			});
		}
	}
	
	public ImageView getIcon() {
		return icon;
	}
	
	public Drawable getIconDrawable() {
		return iconDrawable;
	}

	public void setIconDrawable(Drawable drawableIcon) {
		this.iconDrawable = drawableIcon;
		icon.setImageDrawable(drawableIcon);
	}

	/**
	 * 设置button中图片的大小，默认是居中显示的。 如果图片大小超过了按钮的大小，那么按钮会根据图片进行放大，直到能包含内部图片为止
	 * 
	 * @param size
	 */
	public void setIconSize(int size) {
		iconSize = size;
		setIconParams();
	}

	/**
	 * @return 按钮中心图片的大小
	 */
	public int getIconSize() {
		return iconSize;
	}

	@Override
	@Deprecated
	public TextView getTextView() {
		// 无效方法
		return null;
	}

}
