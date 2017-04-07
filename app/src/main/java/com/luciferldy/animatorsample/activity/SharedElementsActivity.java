package com.luciferldy.animatorsample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.luciferldy.animatorsample.R;

/**
 * Created by Lucifer on 2017/4/4.
 */

public class SharedElementsActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_elements);
        final ImageView sharedElements = (ImageView) findViewById(R.id.shared_elements);
        sharedElements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SharedElementsActivity.this,
                        sharedElements, "VivaLavida");
                Intent intent = new Intent(SharedElementsActivity.this, SampleActivity.class);
                startActivity(intent, options.toBundle());
            }
        });
        final ImageView scaleUp = (ImageView) findViewById(R.id.scale_up);
        scaleUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(scaleUp,
                        scaleUp.getLeft() + scaleUp.getWidth() / 2,
                        scaleUp.getTop() + scaleUp.getHeight() / 2, 0, 0);
                Intent intent = new Intent(SharedElementsActivity.this, SampleActivity.class);
                startActivity(intent, optionsCompat.toBundle());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shared_elements:
                break;
            case R.id.scale_up:
                break;
            default:
                break;
        }
    }
}
