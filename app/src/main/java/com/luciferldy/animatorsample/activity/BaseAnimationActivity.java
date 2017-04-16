package com.luciferldy.animatorsample.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.luciferldy.animatorsample.R;

/**
 * Created by Lucifer on 2017/4/4.
 */

public class BaseAnimationActivity extends AppCompatActivity {

    private static final String LOG_TAG = BaseAnimationActivity.class.getSimpleName();
    private static final int ANIMATION_DURATION = 1000;
    private static final int ACTIVITY_ANIMATION_DURATION = 300;
    private static final int FRAGMENT_ANIMATION_DURATION = 300;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            switch (bundle.getInt(MainActivity.STYLE_KEY, 0)) {
                case MainActivity.STYLE_ANIMATOR:
                    performAnimator();
                    break;
                case MainActivity.STYLE_ANIMATION:
                    performAnimation();
                    break;
                case MainActivity.STYLE_FRAGMENT_ANIMATION:
                    performFragmentAnimation();
                    break;
                case MainActivity.STYLE_ACTIVITY_ANIMATION:
                    performActivityAnimation();
                    break;
                default:
                    Log.e(LOG_TAG, "onCreate occur default case.");
                    break;
            }
        } else {
            Log.i(LOG_TAG, "getIntent().getExtras() is null.");
        }

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.i(LOG_TAG, "onBackPressed, count " + count);
        if (count > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void performAnimator() {
        setContentView(R.layout.base_animation);
        final Button btnAlpha = (Button) findViewById(R.id.btn_alpha);
        final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(btnAlpha, "alpha", 1.0f, 0)
                .setDuration(ANIMATION_DURATION);
        final Button btnRotate = (Button) findViewById(R.id.btn_rotate);
        final ValueAnimator rotateAnimator = ValueAnimator.ofInt(0, 360)
                .setDuration(ANIMATION_DURATION);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int value;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (int) animation.getAnimatedValue();
                btnRotate.setRotation(value);
            }
        });
        final Button btnTranslate = (Button) findViewById(R.id.btn_translate);
        // getWidth() is 0
//        Log.i(LOG_TAG, "btnTranslate width: " + btnTranslate.getWidth());
        final ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(btnTranslate, View.TRANSLATION_X, 0, 100, -100, 0)
                .setDuration(ANIMATION_DURATION);
        final Button btnScale = (Button) findViewById(R.id.btn_scale);
        final ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(btnScale, "scaleX", 1.0f, 1.5f, 1.0f)
                .setDuration(ANIMATION_DURATION);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_alpha:
                        alphaAnimator.start();
                        break;
                    case R.id.btn_rotate:
                        rotateAnimator.start();
                        break;
                    case R.id.btn_translate:
                        translateAnimator.start();
                        break;
                    case R.id.btn_scale:
                        scaleAnimator.start();
                        break;
                    default:
                        break;
                }
            }
        };
        btnAlpha.setOnClickListener(listener);
        btnRotate.setOnClickListener(listener);
        btnTranslate.setOnClickListener(listener);
        btnScale.setOnClickListener(listener);

    }

    private void performAnimation() {
        setContentView(R.layout.base_animation);
        final Button btnAlpha = (Button) findViewById(R.id.btn_alpha);
        final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0);
        alphaAnimation.setFillAfter(true); // 保持动画结束时的状态
//        alphaAnimation.setFillBefore(true); // 当动画结束时保持动画开始的状态
        alphaAnimation.setDuration(ANIMATION_DURATION);
        final Button btnRotate = (Button) findViewById(R.id.btn_rotate);
        final RotateAnimation rotateAnimation = new RotateAnimation(0 ,360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(ANIMATION_DURATION);
        final Button btnTranslate = (Button) findViewById(R.id.btn_translate);
        final TranslateAnimation translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(getBaseContext(), R.anim.base_translate);
        translateAnimation.setDuration(ANIMATION_DURATION);
        final Button btnScale = (Button) findViewById(R.id.btn_scale);
        final ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(getBaseContext(), R.anim.base_scale);
        scaleAnimation.setDuration(ANIMATION_DURATION);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_alpha:
                        btnAlpha.startAnimation(alphaAnimation);
                        break;
                    case R.id.btn_rotate:
                        btnRotate.startAnimation(rotateAnimation);
                        break;
                    case R.id.btn_translate:
                        btnTranslate.startAnimation(translateAnimation);
                        break;
                    case R.id.btn_scale:
                        btnScale.startAnimation(scaleAnimation);
                        break;
                    default:
                        break;
                }
            }
        };
        btnAlpha.setOnClickListener(listener);
        btnRotate.setOnClickListener(listener);
        btnTranslate.setOnClickListener(listener);
        btnScale.setOnClickListener(listener);
    }

    private void performFragmentAnimation() {
        setContentView(R.layout.fragment_animation);
        findViewById(R.id.add_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseAnimationFragment fragment = new BaseAnimationFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_enter, R.anim.slide_exit);
                transaction.add(android.R.id.content, fragment, BaseAnimationFragment.class.getSimpleName());
                transaction.addToBackStack(BaseAnimationFragment.class.getSimpleName() + "#" + System.currentTimeMillis());
                transaction.commit();
            }
        });

    }

    private void performActivityAnimation() {
        setContentView(R.layout.activity_animation);
        Button fade = (Button) findViewById(R.id.fade);
        Button slide = (Button) findViewById(R.id.slide);
        Button explode = (Button) findViewById(R.id.explode);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    return;
                // it seems that another activity animation like Activity.overridePendingTransition()
                switch (v.getId()) {
                    case R.id.fade:
                        Fade fade = new Fade();
                        fade.setDuration(ACTIVITY_ANIMATION_DURATION);
                        getWindow().setEnterTransition(fade);
                        getWindow().setExitTransition(fade);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(BaseAnimationActivity.this);
                        Intent intent = new Intent(BaseAnimationActivity.this, SharedElementsActivity.class);
                        startActivity(intent, options.toBundle());
                        break;
                    case R.id.slide:
                        Slide slide = new Slide();
                        slide.setDuration(ACTIVITY_ANIMATION_DURATION);
                        slide.setSlideEdge(Gravity.END);
                        getWindow().setEnterTransition(slide);
                        getWindow().setExitTransition(slide);
                        ActivityOptionsCompat optionsSlide = ActivityOptionsCompat.makeSceneTransitionAnimation(BaseAnimationActivity.this);
                        Intent intentSlide = new Intent(BaseAnimationActivity.this, SharedElementsActivity.class);
                        startActivity(intentSlide, optionsSlide.toBundle());
                        break;
                    case R.id.explode:
                        Explode explode = new Explode();
                        explode.setDuration(ACTIVITY_ANIMATION_DURATION);
                        getWindow().setEnterTransition(explode);
                        getWindow().setExitTransition(explode);
                        ActivityOptionsCompat optionsExplode = ActivityOptionsCompat.makeSceneTransitionAnimation(BaseAnimationActivity.this);
                        Intent intentExplode = new Intent(BaseAnimationActivity.this, SharedElementsActivity.class);
                        startActivity(intentExplode, optionsExplode.toBundle());
                        break;
                    default:
                        break;
                }
            }
        };
        fade.setOnClickListener(listener);
        slide.setOnClickListener(listener);
        explode.setOnClickListener(listener);
    }
}
