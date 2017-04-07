package com.luciferldy.animatorsample.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Lucifer on 2017/2/22.
 */

public class CustomAnimView extends View {

    private static final String LOG_TAG = CustomAnimView.class.getSimpleName();

    private static final float RADIUS = 50f;
    private PointF currentPointF;
    private Paint mPaint;
    // 这种转换方法可以把 int 类型的 Color 值转成 String 类型
    private String mColor = String.format("#%06X", (0xFFFFFF) & Color.BLUE);

    public CustomAnimView(Context context) {
        this(context, null);
    }

    public CustomAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        Log.d(LOG_TAG, String.format("color int is %d, string is %s", Color.BLUE, String.format("#%06X", 0xFFFFFF & Color.BLUE)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPointF == null) {
            currentPointF = new PointF(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(currentPointF.x, currentPointF.y, RADIUS, mPaint);
    }

    private void startAnimation() {
        PointF startPointF = new PointF(RADIUS, RADIUS);
        PointF endPoint = new PointF(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator pointAnimator = ValueAnimator.ofObject(new PointFEvaluator(), startPointF, endPoint);
        pointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentPointF = (PointF) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(pointAnimator).with(colorAnimator);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }


    public class PointFEvaluator implements TypeEvaluator {

        private PointF startPoint;
        private PointF endPoint;
        private float x;
        private float y;
        private PointF pointF = new PointF();

        @Override
        public Object evaluate(float v, Object o, Object t1) {
            startPoint = (PointF) o;
            endPoint = (PointF) t1;
            x = startPoint.x + v * (endPoint.x - startPoint.x);
            y = startPoint.y + v * (endPoint.y - startPoint.y);
            pointF.set(x, y);
            return pointF;
        }
    }

    public class ColorEvaluator implements TypeEvaluator {

        private final String LOG_TAG = ColorEvaluator.class.getSimpleName();

        private int mCurrentRed = -1;
        private int mCurrentGreen = -1;
        private int mCurrentBlue = -1;

        String startColor;
        String endColor;
        int startRed;
        int startGreen;
        int startBlue;
        int endRed;
        int endGreen;
        int endBlue;
        int currentColor;
        int diffColor;

        @Override
        public Object evaluate(float v, Object o, Object t1) {
            Log.d(LOG_TAG, "evaluate fraction " + v);
            startColor = (String) o;
            endColor = (String) t1;
            startRed = Integer.parseInt(startColor.substring(1, 3), 16);
            startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
            startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
            endRed = Integer.parseInt(endColor.substring(1, 3), 16);
            endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
            endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
            // 初始化颜色的值
            if (mCurrentRed == -1) {
                mCurrentRed = startRed;
            }
            if (mCurrentGreen == -1) {
                mCurrentGreen = startGreen;
            }
            if (mCurrentBlue == -1) {
                mCurrentBlue = startBlue;
            }
            // 计算初始颜色和最终颜色之间的色差
//            int diffRed = Math.abs(endRed - startRed);
//            int diffGreen = Math.abs(endGreen - startGreen);
//            int diffBlue = Math.abs(endBlue - startBlue);
            if (mCurrentRed != endRed) {
                mCurrentRed = getCurrentColor(startRed, endRed, 0, v);
            }
            if (mCurrentGreen != endGreen) {
                mCurrentGreen = getCurrentColor(startGreen, endGreen, 0, v);
            }
            if (mCurrentBlue != endBlue) {
                mCurrentBlue = getCurrentColor(startBlue, endBlue, 0, v);
            }

            Log.d(LOG_TAG, String.format("current red is %d, current green is %d, current blue is %d", mCurrentRed, mCurrentGreen, mCurrentBlue));
            // 将组装的颜色返回
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append(getHexColor(mCurrentRed));
            builder.append(getHexColor(mCurrentGreen));
            builder.append(getHexColor(mCurrentBlue));
            Log.d(LOG_TAG, builder.toString());
            return builder.toString();
        }

        /**
         * 根据 fraction 计算当前的颜色
         * @param startColor
         * @param endColor
         * @param offset
         * @param fraction
         * @return
         */
        private int getCurrentColor(int startColor, int endColor, int offset, float fraction) {
            diffColor = Math.abs(endColor - startColor);
            if (startColor > endColor) {
                currentColor = (int) (startColor - (diffColor * fraction - offset));
                if (currentColor < endColor) {
                    currentColor = endColor;
                }
            } else {
                currentColor = (int) (startColor + (diffColor * fraction - offset));
                if (currentColor > endColor) {
                    currentColor = endColor;
                }
            }
            return currentColor;
        }

        private String getHexColor(int value) {
            String hexString = Integer.toHexString(value);
            if (hexString.length() == 1) {
                return "0" + hexString;
            }
            return hexString;
        }
    }
}
