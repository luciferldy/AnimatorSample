package com.luciferldy.animatorsample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.luciferldy.animatorsample.R;

/**
 * Created by Lucifer on 2017/2/22.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String STYLE_KEY = "styleKey";
    public static final int STYLE_ANIMATOR = 1;
    public static final int STYLE_ANIMATION = 2;
    public static final int STYLE_FRAGMENT_ANIMATION = 3;
    public static final int STYLE_ACTIVITY_ANIMATION = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.animator).setOnClickListener(this);
        findViewById(R.id.animation).setOnClickListener(this);
        findViewById(R.id.fragment_animation).setOnClickListener(this);
        findViewById(R.id.activity_animation).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.animator:
                bundle.putInt(STYLE_KEY, STYLE_ANIMATOR);
                break;
            case R.id.animation:
                bundle.putInt(STYLE_KEY, STYLE_ANIMATION);
                break;
            case R.id.fragment_animation:
                bundle.putInt(STYLE_KEY, STYLE_FRAGMENT_ANIMATION);
                break;
            case R.id.activity_animation:
                bundle.putInt(STYLE_KEY, STYLE_ACTIVITY_ANIMATION);
                break;
            default:
                Log.e("MainActivity", "onClick default case.");
                return;
        }
        Intent intent = new Intent(MainActivity.this, BaseAnimationActivity.class);
        startActivity(intent, bundle);
    }
}
