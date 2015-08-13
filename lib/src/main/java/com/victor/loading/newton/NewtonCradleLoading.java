package com.victor.loading.newton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.victor.loading.R;

/**
 * @author Victor
 *         create at 15/8/9
 */
public class NewtonCradleLoading extends LinearLayout {

    private CradleBall cradleBallOne;
    private CradleBall cradleBallTwo;
    private CradleBall cradleBallThree;
    private CradleBall cradleBallFour;
    private CradleBall cradleBallFive;

    private static final int DURATION = 400;
    private static final int SHAKE_DISTANCE = 2;
    private static final float PIVOT_X = 0.5f;
    private static final float PIVOT_Y = -3f;
    private static final int DEGREE = 30;


    private boolean isStart = false;

    public NewtonCradleLoading(Context context) {
        super(context);
        initView(context);
    }

    public NewtonCradleLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NewtonCradleLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.newton_cradle_loading, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        cradleBallOne = (CradleBall) findViewById(R.id.ball_one);
        cradleBallTwo = (CradleBall) findViewById(R.id.ball_two);
        cradleBallThree = (CradleBall) findViewById(R.id.ball_three);
        cradleBallFour = (CradleBall) findViewById(R.id.ball_four);
        cradleBallFive = (CradleBall) findViewById(R.id.ball_five);

        initAnim();
    }

    RotateAnimation rotateLeftAnimation;//cradleBallOne left to right
    RotateAnimation rotateRightAnimation;//cradleBallFive right to left
    TranslateAnimation shakeLeftAnimation;
    TranslateAnimation shakeRightAnimation;


    private void initAnim() {
        rotateRightAnimation = new RotateAnimation(0, -DEGREE, RotateAnimation.RELATIVE_TO_SELF, PIVOT_X, RotateAnimation.RELATIVE_TO_SELF, PIVOT_Y);
        rotateRightAnimation.setRepeatCount(1);
        rotateRightAnimation.setRepeatMode(Animation.REVERSE);
        rotateRightAnimation.setDuration(DURATION);
        rotateRightAnimation.setInterpolator(new LinearInterpolator());
        rotateRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isStart)
                    startRightAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        shakeLeftAnimation = new TranslateAnimation(0, SHAKE_DISTANCE, 0, 0);
        shakeLeftAnimation.setDuration(DURATION);
        shakeLeftAnimation.setInterpolator(new CycleInterpolator(2));

        rotateLeftAnimation = new RotateAnimation(0, DEGREE, RotateAnimation.RELATIVE_TO_SELF, PIVOT_X, RotateAnimation.RELATIVE_TO_SELF, PIVOT_Y);
        rotateLeftAnimation.setRepeatCount(1);
        rotateLeftAnimation.setRepeatMode(Animation.REVERSE);
        rotateLeftAnimation.setDuration(DURATION);
        rotateLeftAnimation.setInterpolator(new LinearInterpolator());
        rotateLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isStart) {
                    cradleBallTwo.startAnimation(shakeLeftAnimation);
                    cradleBallThree.startAnimation(shakeLeftAnimation);
                    cradleBallFour.startAnimation(shakeLeftAnimation);

                    cradleBallFive.startAnimation(rotateRightAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        shakeRightAnimation = new TranslateAnimation(0, -SHAKE_DISTANCE, 0, 0);
        shakeRightAnimation.setDuration(DURATION);
        shakeRightAnimation.setInterpolator(new CycleInterpolator(2));
        shakeRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isStart)
                    startLeftAnim();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startLeftAnim() {
        cradleBallOne.startAnimation(rotateLeftAnimation);
    }

    private void startRightAnim() {
        cradleBallTwo.startAnimation(shakeRightAnimation);
        cradleBallThree.startAnimation(shakeRightAnimation);
        cradleBallFour.startAnimation(shakeRightAnimation);
    }

    public void start() {
        if (!isStart) {
            isStart = true;
            startLeftAnim();
        }
    }

    public void stop() {
        isStart = false;
        cradleBallOne.clearAnimation();
        cradleBallTwo.clearAnimation();
        cradleBallThree.clearAnimation();
        cradleBallFour.clearAnimation();
        cradleBallFive.clearAnimation();
    }

    public boolean isStart() {
        return isStart;
    }
}
