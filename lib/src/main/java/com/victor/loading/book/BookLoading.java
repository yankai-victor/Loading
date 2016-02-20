package com.victor.loading.book;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.victor.loading.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Book loading
 * Created by Victor on 15/7/28.
 */
public class BookLoading extends FrameLayout {

    private static final long DURATION = 1000;

    private static final int PAGE_NUM = 5;

    private static final int DELAYED = 200;

    private ArrayList<PageView> pageViews;

    private BookHandler bookHandler;

    private boolean isStart;

    public BookLoading(Context context) {
        super(context);
        initView(context);
    }

    public BookLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BookLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.book_loading, this, true);
        pageViews = new ArrayList<>();
        bookHandler = new BookHandler(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addPage();
    }

    private void addPage() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        for (int i = 0; i < PAGE_NUM; i++) {
            PageView pageView = new PageView(getContext());
            addView(pageView, params);
            pageView.setTag(R.string.app_name, i);
            pageViews.add(pageView);
        }
    }


    private void playAnim() {
        setAnim(pageViews.get(PAGE_NUM - 1), DELAYED);

        setAnim(pageViews.get(PAGE_NUM - 1), DURATION + DELAYED);
        setAnim(pageViews.get(PAGE_NUM - 2), DURATION + DELAYED * 2);

        for (int i = PAGE_NUM - 1; i >= 0; i--) {
            setAnim(pageViews.get(i), DURATION * 3 + (PAGE_NUM - 1 - i) * DELAYED / 2);
        }
    }


    private void setAnim(final View view, long delay) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationY", 0.0F, -180.0F);
        animator.setDuration(DURATION);
        animator.setStartDelay(delay);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            boolean change = false;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() > DURATION / 2 && !change) {
                    change = true;
                    view.bringToFront();
                }

            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if ((int) view.getTag(R.string.app_name) == PAGE_NUM - 1) {
                    view.bringToFront();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }

    public void start() {
        isStart = true;
        bookHandler.obtainMessage().sendToTarget();
    }

    public void stop() {
        isStart = false;
        bookHandler.removeCallbacks(null);
        bookHandler.removeCallbacksAndMessages(null);
    }

    public boolean isStart() {
        return isStart;
    }

    static class BookHandler extends Handler {
        private WeakReference<BookLoading> weakReference;

        public BookHandler(BookLoading bookLoading) {
            weakReference = new WeakReference<>(bookLoading);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BookLoading bookLoading = weakReference.get();
            if (null == bookLoading)
                return;
            bookLoading.playAnim();

            Message message = obtainMessage();
            sendMessageDelayed(message, DURATION * 5);
        }
    }
}
