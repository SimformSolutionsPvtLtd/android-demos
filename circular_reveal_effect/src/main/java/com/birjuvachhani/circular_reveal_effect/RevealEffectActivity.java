package com.birjuvachhani.circular_reveal_effect;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RevealEffectActivity extends AppCompatActivity implements View.OnTouchListener {

    private boolean isInfoVisible = false;

    @BindView(R.id.albumInfoContainer)
    View albumInfoContainer;

    @BindView(R.id.ivAlbumArt)
    View albumArt;

    @BindView(R.id.textGroup)
    Group textGroup;

    private int hypotenuse;
    private int pointX;
    private int pointY;
    private Animator.AnimatorListener revealListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            textGroup.setVisibility(View.VISIBLE);
            textGroup.startAnimation(mAlphaAnim);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private Animator.AnimatorListener unravelListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            albumInfoContainer.setVisibility(View.GONE);
            textGroup.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private Animation mAlphaAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_effect);
        ButterKnife.bind(this);
        albumArt.setOnTouchListener(this);
        mAlphaAnim = new AlphaAnimation(0.0f, 1.0f);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showMoreInfo(View view) {
        hypotenuse = (int) Math.hypot(albumArt.getWidth(), albumArt.getHeight());
        if (isInfoVisible) {
            Animator animator = ViewAnimationUtils.createCircularReveal(albumInfoContainer, pointX, pointY, hypotenuse, 0);
            animator.setDuration(400);
            animator.addListener(unravelListener);
            animator.start();
            isInfoVisible = false;
            return;
        }
        albumInfoContainer.setBackgroundColor(getTouchedColor());
        Animator animator = ViewAnimationUtils.createCircularReveal(albumInfoContainer, pointX, pointY, 0, hypotenuse);
        animator.setDuration(400);
        animator.addListener(revealListener);
        albumInfoContainer.setVisibility(View.VISIBLE);
        animator.start();
        isInfoVisible = true;
    }

    private int getTouchedColor() {
        float[] eventXY = new float[]{pointX, pointY};

        Matrix invertMatrix = new Matrix();
        ((ImageView) albumArt).getImageMatrix().invert(invertMatrix);

        invertMatrix.mapPoints(eventXY);
        int x = (int) eventXY[0];
        int y = (int) eventXY[1];

        Drawable imgDrawable = ((ImageView) albumArt).getDrawable();
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
