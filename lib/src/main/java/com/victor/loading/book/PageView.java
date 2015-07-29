package com.victor.loading.book;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.victor.loading.R;

/**
 * Page
 * Created by Victor on 15/7/28.
 */
public class PageView extends View {

    private Paint paint;
    private Path path;

    private int width;
    private int height;
    private float padding;
    private int border;

    public PageView(Context context) {
        super(context);
        initView();
    }

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        padding = getResources().getDimension(R.dimen.book_padding);
        border = getResources().getDimensionPixelOffset(R.dimen.book_border);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.page_border));

        path = new Path();

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

        paint.setColor(getResources().getColor(R.color.book_loading_book));
        paint.setStyle(Paint.Style.STROKE);
        float offset = border / 4;
        path.moveTo(width / 2, padding + offset);
        path.lineTo(width - padding - offset, padding + offset);
        path.lineTo(width - padding - offset, height - padding - offset);
        path.lineTo(width / 2, height - padding - offset);
        canvas.drawPath(path, paint);

        paint.setColor(getResources().getColor(R.color.book_loading_page));
        paint.setStyle(Paint.Style.FILL);

        offset = border / 2;
        canvas.drawRect(width / 2, padding + offset, width - padding - offset, height - padding - offset, paint);
    }
}
