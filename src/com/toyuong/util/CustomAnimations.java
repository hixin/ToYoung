package com.toyuong.util;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

/**
 *Բ����ת����
 * */
public class CustomAnimations {

	public static void startCWAnimation(View animateView, float fromDegree, float toDegree) {
		RotateAnimation cwAnimation = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		cwAnimation.setFillAfter(true);
		animateView.startAnimation(cwAnimation);
	}

	public static void startRotateAnimation(View animateView) {
		RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(1000);
		rotate.setRepeatCount(Animation.INFINITE);
		rotate.setInterpolator(new LinearInterpolator());
		animateView.startAnimation(rotate);
	}

	public static void stopRotateAnmiation(View animatedView) {
		animatedView.clearAnimation();
	}

	/**
	 * @param view
	 * @param show
	 * true :չ��
	 * false���ر�
	 * */
	public static void tabAnimation(final View view,boolean show) {
		Animation myAnimation;
		if(show)
		myAnimation= new ScaleAnimation(1f, 1f, 0f, 1f,
				Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
		else myAnimation=new ScaleAnimation(1f, 1f, 1f, 0f,
				Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
		myAnimation.setDuration(300);
		myAnimation.setFillBefore(false);
		myAnimation.setFillAfter(true);
		myAnimation.setInterpolator(new AccelerateInterpolator());//���ٱ任
		view.setAnimation(myAnimation);

		myAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if(view.getVisibility()==View.VISIBLE)
				view.setVisibility(View.GONE);
				else view.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}
}
