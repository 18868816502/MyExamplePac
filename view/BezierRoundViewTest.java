package com.jonny.myexamplepac.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


public class BezierRoundViewTest extends View {
    private static final String TAG = "QDX";
    private Matrix matrix_bounceL;   //将向右弹的动画改为向左

    public BezierRoundViewTest(Context context) {
        this(context, null);
    }

    public BezierRoundViewTest(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierRoundViewTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mRadius = 50;
    private final float bezFactor = 0.551915024494f;
    private Path mPath;

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();


//        p0 = new PointF(0, -mRadius);
//        p6 = new PointF(0, mRadius);
//
//        p1 = new PointF(mRadius * bezFactor, -mRadius);
//        p5 = new PointF(mRadius * bezFactor, mRadius);
//
//        p2 = new PointF(mRadius, -mRadius * bezFactor);
//        p4 = new PointF(mRadius, mRadius * bezFactor);
//
//        p3 = new PointF(mRadius, 0);
//        p9 = new PointF(-mRadius, 0);
//
//        p11 = new PointF(-mRadius * bezFactor, -mRadius);
//        p7 = new PointF(-mRadius * bezFactor, mRadius);
//
//        p10 = new PointF(-mRadius, -mRadius * bezFactor);
//        p8 = new PointF(-mRadius, mRadius * bezFactor);


//        //y轴一样
//        p5 = new PointF(mRadius * bezFactor, mRadius * 5 / 6);
//        p6 = new PointF(0, mRadius * 5 / 6);
//        p7 = new PointF(-mRadius * bezFactor, mRadius * 5 / 6);
//        //y轴一样
//        p0 = new PointF(0, -mRadius * 5 / 6);
//        p1 = new PointF(mRadius * bezFactor, -mRadius * 5 / 6);
//        p11 = new PointF(-mRadius * bezFactor, -mRadius * 5 / 6);
//        //x轴一样
//        p2 = new PointF(mRadius * 1.5f, -mRadius * bezFactor);
//        p3 = new PointF(mRadius * 1.5f, 0);
//        p4 = new PointF(mRadius * 1.5f, mRadius * bezFactor);
//        //x轴一样
//        p8 = new PointF(-mRadius * 1.5f, mRadius * bezFactor);
//        p9 = new PointF(-mRadius * 1.5f, 0);
//        p10 = new PointF(-mRadius * 1.5f, -mRadius * bezFactor);


        //y轴一样
        p5 = new PointF(mRadius * bezFactor, mRadius);
        p6 = new PointF(0, mRadius);
        p7 = new PointF(-mRadius * bezFactor, mRadius);
        //y轴一样
        p0 = new PointF(0, -mRadius);
        p1 = new PointF(mRadius * bezFactor, -mRadius);
        p11 = new PointF(-mRadius * bezFactor, -mRadius);
        //x轴一样
        p2 = new PointF(mRadius, -mRadius * bezFactor);
        p3 = new PointF(mRadius, 0);
        p4 = new PointF(mRadius, mRadius * bezFactor);
        //x轴一样
        p8 = new PointF(-mRadius, mRadius * bezFactor);
        p9 = new PointF(-mRadius, 0);
        p10 = new PointF(-mRadius, -mRadius * bezFactor);
    }

    //展示动画
    private ValueAnimator animatorStart;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();
    private float animatedValue;

    public void startAnimator() {
        if (animatorStart != null) {
            if (animatorStart.isRunning()) {
                return;
            }
            animatorStart.start();
        } else {
            animatorStart = ValueAnimator.ofFloat(0, 1f).setDuration(1500);
            animatorStart.setInterpolator(timeInterpolator);
            animatorStart.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                    Log.v(TAG, "animatorStart invalidate()");

                }
            });
            animatorStart.start();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        matrix_bounceL = new Matrix();
        matrix_bounceL.preScale(-1, 1);
    }

    private PointF p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
//                p2 = new PointF(event.getX() - mWidth / 2, -mRadius * bezFactor * 1.2f);
//                p3 = new PointF(event.getX() - mWidth / 2, 0);
//                p4 = new PointF(event.getX() - mWidth / 2, mRadius * bezFactor * 1.2f);
//
//                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                startAnimator();
                break;
        }

        return true;
    }

    private float rRadio=1;  //P2,3,4 x轴倍数 ，不对外开放
    private float lRadio=1;  //P8,9,10倍数 ，不对外开放
    private float tbRadio=1;  //y轴缩放倍数 ，不对外开放
    private float boundRadio = 0.55f;  //进入另一个圆的回弹效果 ，对外开放，可以修改


    private float disL = 0.5f;   //离开圆的阈值
    private float disM = 0.8f;  //最大值的阈值
    private float disA = 0.9f;  //到达下个圆的阈值


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 4, mHeight / 4);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawCircle(0, 0, mRadius, mPaint);

        mPaint.setColor(Color.RED);
        mPath.reset();

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mWidth / 2, 0, mRadius, mPaint);
        canvas.drawCircle(0, 0, mRadius, mPaint);
        mPaint.setStyle(Paint.Style.FILL);


//        if (0 < animatedValue && animatedValue <= 0.5) {
//            rRadio = 1f + animatedValue * 2;                         //  [1,2]
//        }
//        if (0.5 < animatedValue && animatedValue <= 0.55) {
//            rRadio = 2 - (animatedValue - 0.5f) * 10;                //  [2,1.5]
//        }
//        if (0.55 < animatedValue && animatedValue <= 0.9f) {
//            rRadio = 1.5f - (animatedValue - 0.55f) / 0.35f / 2;     //  [1.5,1]
//        }
//        if (0.9 < animatedValue && animatedValue <= 1f) {
//            rRadio = 1;
//        }
//
//
//        if (0.5 < animatedValue && animatedValue <= 0.55) {
//            lRadio = 1f + (animatedValue - 0.5f) * 10;                  // [1,1.5]
//        }
//
//        if (0.55 < animatedValue && animatedValue <= 0.9f) {
//            lRadio = 1.5f - (animatedValue - 0.55f) / 0.35f / 2;        // [1.5,1]
//        }
//        if (0.9 < animatedValue && animatedValue <= 1f) {
//            lRadio = 1;
//        }
//
//
//        if (0.5 < animatedValue && animatedValue <= 0.55) {
//            tbRadio = 1 - (animatedValue - 0.5f) * 20f / 3; // [1 , 2/3]
//        }
//        if (0.55 < animatedValue && animatedValue <= 0.9) {
//            tbRadio = (animatedValue - 0.55f) / 0.35f / 3 + 2f / 3f; // [ 2/3,1]
//        }
//
//        if (0.9 < animatedValue && animatedValue <= 1f) {
//            tbRadio = 1;  //  [1.5,1]
//        }


        if (0 < animatedValue && animatedValue <= disL) {
            rRadio = 1f + animatedValue * 2;                         //  [1,2]
        }
        if (disL < animatedValue && animatedValue <= disM) {

            rRadio = 2 - range0Until1(disL, disM) * 0.5f;          //  [2,1.5]
            lRadio = 1 + range0Until1(disL, disM) * 0.5f;          // [1,1.5]
            tbRadio = 1 - range0Until1(disL, disM) / 3;           // [1 , 2/3]
        }
        if (disM < animatedValue && animatedValue <= disA) {
            rRadio = 1.5f - range0Until1(disM, disA) * 0.5f;     //  [1.5,1]
            lRadio = 1.5f - range0Until1(disM, disA) * (1.5f - boundRadio);      //反弹效果，进场 内弹boundRadio
            tbRadio = (range0Until1(disM, disA) + 2) / 3;        // [ 2/3,1]
        }
        if (disA < animatedValue && animatedValue <= 1f) {
            rRadio = 1;
            tbRadio = 1;
            lRadio = boundRadio + range0Until1(disA, 1) * (1 - boundRadio);     //反弹效果，饱和
        }

        boolean isTrans = false;
        float transX = 1f;
        if (disL <= animatedValue && animatedValue <= disA) {
            isTrans = true;
            transX = mWidth / 2f * (animatedValue - disL) / (disA - disL);
        }
        if (disA < animatedValue && animatedValue <= 1) {
            isTrans = true;
            transX = mWidth / 2;
        }

        if (isTrans) {
            canvas.translate(transX, 0);
        }


        bounce2RightRound();

//        mPath.transform(matrix_bounceL);
        canvas.drawPath(mPath, mPaint);

        if (isTrans) {
            canvas.save();
        }
    }


    /**
     * 通过 path 将向右弹射的动画绘制出来
     * 如果要绘制向左的动画，只要设置path的transform(matrix)即可
     */
    private void bounce2RightRound() {
        mPath.moveTo(p0.x, p0.y * tbRadio);
        mPath.cubicTo(p1.x, p1.y * tbRadio, p2.x * rRadio, p2.y, p3.x * rRadio, p3.y);
        mPath.cubicTo(p4.x * rRadio, p4.y, p5.x, p5.y * tbRadio, p6.x, p6.y * tbRadio);
        mPath.cubicTo(p7.x, p7.y * tbRadio, p8.x * lRadio, p8.y, p9.x * lRadio, p9.y);
        mPath.cubicTo(p10.x * lRadio, p10.y, p11.x, p11.y * tbRadio, p0.x, p0.y * tbRadio);
        mPath.close();
    }


    /**
     * 将值域转化为[0,1]
     *
     * @param minValue 大于等于
     * @param maxValue 小于等于
     * @return 根据当前 animatedValue,返回 [0,1] 对应的数值
     */
    private float range0Until1(float minValue, float maxValue) {
        return (animatedValue - minValue) / (maxValue - minValue);
    }
}
