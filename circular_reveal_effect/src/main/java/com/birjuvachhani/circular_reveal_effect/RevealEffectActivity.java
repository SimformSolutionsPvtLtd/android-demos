package com.birjuvachhani.circular_reveal_effect;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.birjuvachhani.circular_reveal_effect.databinding.ActivityRevealEffectBinding;

public class RevealEffectActivity extends AppCompatActivity implements View.OnTouchListener {

    private boolean isInfoVisible = false;
    private ActivityRevealEffectBinding activityRevealEffectBinding;

    private int pointX;
    private int pointY;
    private Animator.AnimatorListener revealListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            // Nothing to do
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            activityRevealEffectBinding.textGroup.setVisibility(View.VISIBLE);
            activityRevealEffectBinding.textGroup.startAnimation(mAlphaAnim);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // Nothing to do
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // Nothing to do
        }
    };

    private Animator.AnimatorListener unravelListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            // Nothing to do
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            activityRevealEffectBinding.albumInfoContainer.setVisibility(View.GONE);
            activityRevealEffectBinding.textGroup.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // Nothing to do
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // Nothing to do
        }
    };

    private Animation mAlphaAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRevealEffectBinding = DataBindingUtil.setContentView(this, R.layout.activity_reveal_effect);
        activityRevealEffectBinding.ivAlbumArt.setOnTouchListener(this);
        mAlphaAnim = new AlphaAnimation(0.0f, 1.0f);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showMoreInfo(View view) {
        int hypotenuse = (int) Math.hypot(activityRevealEffectBinding.ivAlbumArt.getWidth(), activityRevealEffectBinding.ivAlbumArt.getHeight());
        if (isInfoVisible) {
            Animator animator = ViewAnimationUtils.createCircularReveal(activityRevealEffectBinding.albumInfoContainer, pointX, pointY, hypotenuse, 0);
            animator.setDuration(400);
            animator.addListener(unravelListener);
            animator.start();
            isInfoVisible = false;
            return;
        }
        activityRevealEffectBinding.albumInfoContainer.setBackgroundColor(getTouchedColor());
        Animator animator = ViewAnimationUtils.createCircularReveal(activityRevealEffectBinding.albumInfoContainer, pointX, pointY, 0, hypotenuse);
        animator.setDuration(400);
        animator.addListener(revealListener);
        activityRevealEffectBinding.albumInfoContainer.setVisibility(View.VISIBLE);
        animator.start();
        isInfoVisible = true;
    }

    private int getTouchedColor() {
        float[] eventXY = new float[]{pointX, pointY};

        Matrix invertMatrix = new Matrix();
        activityRevealEffectBinding.ivAlbumArt.getImageMatrix().invert(invertMatrix);

        invertMatrix.mapPoints(eventXY);
        int x = (int) eventXY[0];
        int y = (int) eventXY[1];

        Drawable imgDrawable = activityRevealEffectBinding.ivAlbumArt.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) imgDrawable).getBitmap();
        return bitmap.getPixel(x, y);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            pointX = (int) event.getX();
            pointY = (int) event.getY();
        }
        return false;
    }
}
