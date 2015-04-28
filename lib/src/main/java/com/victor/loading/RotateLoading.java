package com.victor.loading;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import java.lang.ref.WeakReference;

/**
 * Created by Victor on 2015/4/28.
 */
public class RotateLoading extends View {

    private static final int DEFAULT_DEGREE = 360 * 4;
    private static final int DEFAULT_DURATION = 3000;
    private static final int DEFAULT_WIDTH = 5;

    private Paint mPaint;

    private int arc;

    private int width;

    private int degree = DEFAULT_DEGREE;
    private int duration = DEFAULT_DURATION;

    private boolean changeBigger = true;

    private MyHandler mHandler;

    public RotateLoading(Context context) {
        super(context);
        initView(context, null);
    }

    public RotateLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public RotateLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mHandler = new MyHandler(this);

        int color = Color.WHITE;
        width = dpToPx(context, DEFAULT_WIDTH);

        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateLoading);
            color = typedArray.getColor(R.styleable.RotateLoading_loading_color, Color.WHITE);
            width = typedArray.getDimensionPixelSize(R.styleable.RotateLoading_loading_width, dpToPx(context, DEFAULT_WIDTH));
            degree = typedArray.getInt(R.styleable.RotateLoading_loading_degree, DEFAULT_DEGREE);
            duration = typedArray.getInt(R.styleable.RotateLoading_loading_duration, DEFAULT_DURATION);
            typedArray.recycle();
        }

        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(width);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        playAnimator();
    }

    private void playAnimator() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 0.0f, 1);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 0.0f, 1);
        scaleXAnimator.setDuration(300);
        scaleXAnimator.setInterpolator(new LinearInterpolator());
        scaleYAnimator.setDuration(300);
        scaleYAnimator.setInterpolator(new LinearInterpolator());
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0.0f, degree);
        rotationAnimator.setRepeatCount(-1);
        rotationAnimator.setRepeatMode(Animation.INFINITE);
        rotationAnimator.setDuration(duration);
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                changeBigger = !changeBigger;
                invalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, rotationAnimator);
        animatorSet.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int centerX = w / 2;
        int centerY = h / 2;
        arc = 10;
        rectF = new RectF(2 * width, 2 * width, centerX * 2 - 2 * width, centerY * 2 - 2 * width);
    }

    RectF rectF;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF, 10, arc, false, mPaint);
        canvas.drawArc(rectF, 190, arc, false, mPaint);
        if (changeBigger) {
            if (arc < 160) {
                arc += 5;
                sendMessage();
            }
        } else {
            if (arc > 10) {
                arc -= 5;
                sendMessage();
            }
        }
    }

    private void sendMessage() {
        Message msg = mHandler.obtainMessage();
        mHandler.sendMessageDelayed(msg, 20);
    }

    public int dpToPx(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }


    private static class MyHandler extends Handler {

        private WeakReference<View> weakReference;

        public MyHandler(View view) {
            weakReference = new WeakReference<View>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View view = weakReference.get();
            if (null != view) {
                view.invalidate();
            }
        }

    }


}
