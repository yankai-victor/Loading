package com.victor.loading.newton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Yan
 *         create at 15/8/9
 */
public class CradleBall extends View {

    private int width;
    private int height;

    private Paint paint;

    public CradleBall(Context context) {
        super(context);
        initView();
    }

    public CradleBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CradleBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
    }
}
