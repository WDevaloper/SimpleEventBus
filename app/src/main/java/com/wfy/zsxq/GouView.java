package com.wfy.zsxq;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class GouView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GouView(Context context) {
        this(context, null);
    }

    public GouView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GouView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        animatorX.setDuration(1500);
        animatorX.start();
    }


    float startX = 200f;
    float startY = 200f;
    float endx = 300f;
    float endY = 300f;

    private float pointX = 0;
    private float pointY = 0;

    public float getPointY() {
        return pointY;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }

    public float getPointX() {
        return pointX;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
        invalidate();
    }

    private float offset = 0;

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
        invalidate();
    }

    private ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "offset", 200, 350);
    private ObjectAnimator animatory = ObjectAnimator.ofFloat(this, "pointY", startY, endY);
    private AnimatorSet set = new AnimatorSet();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(200f, 200f, 300f, 300f, mPaint);
//        canvas.drawLine(300f, 300f, 600f, 100f, mPaint);
//        float[] pts = {200, 200, pointX, pointY, 300f, 300f, 600f, 100f};
//        canvas.drawLines(pts, mPaint);
        Path path = new Path();
        path.moveTo(200f, 200f);
        if (offset <= 250) {
            path.lineTo(offset, offset);
        }else{
            path.lineTo(250, 250);
        }
        if (offset > 250) {
            path.rLineTo(offset - 250, -(offset - 250));
        }
        canvas.drawPath(path, mPaint);
    }
}
