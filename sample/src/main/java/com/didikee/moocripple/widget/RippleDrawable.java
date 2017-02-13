package com.didikee.moocripple.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.view.MotionEvent;

/**
 * Created by didik on 2017/2/8.
 */

public class RippleDrawable extends Drawable {

    private int mAlpha = 255;/*透明度范围 0 ~ 255*/
    private int mRippleColor = 0;
    private Paint mPaint = new Paint(
            Paint.ANTI_ALIAS_FLAG /*抗锯齿*/
    );

    private float mRipplePointX = 0;
    private float mRipplePointY = 0;
    private float mRippleRadius;

    public RippleDrawable() {
        // 开启抗锯齿,让曲线边缘更圆滑
        mPaint.setAntiAlias(true);
        // 开启防抖动
        mPaint.setDither(true);

        setRippleColor(0x70ff0000);
    }

    public void setRippleColor(@ColorInt int color) {
        // 不建议直接设置
        // mPaint.setColor(color);
        mRippleColor = color;
        onColorOrAlphaChange();
    }

    private void onColorOrAlphaChange() {
        // 0x30ffffff
        mPaint.setColor(mRippleColor);
        if (mAlpha != 255) {
            // 0x30
            // 得到颜色本身的透明度
            // 以下两种方式,1 是从画笔中提取透明度; 2 是从颜色中提取颜色的透明度; 任取一种即可
            int alpha = mPaint.getAlpha();
            // alpha = Color.alpha(mRippleColor);

            // 得到叠加后的实际透明度
            int realAlpha = (int) (alpha * (mAlpha / 255f));

            // 设置到画笔中去
            mPaint.setAlpha(realAlpha);
        }

        // 颜色改变时,也应该刷新自己
        invalidateSelf();
    }

    public void onTouch(MotionEvent event){
        // 每次半径增加
        mRippleRadius += 10;
        invalidateSelf();

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
                onTouchCancel(event.getX(),event.getY());
                break;
        }
    }
    private void onTouchDown(float x, float y) {
        mRipplePointX = x;
        mRipplePointY = y;
        mRippleRadius += 10;
        invalidateSelf();
    }

    private void onTouchMove(float x, float y) {
    }
    private void onTouchUp(float x, float y) {

    }
    private void onTouchCancel(float x, float y) {

    }




    @Override
    public void draw(Canvas canvas) {
        //填充一个红色区域
//        canvas.drawColor(0x70ff0000);
        //画一个圆
        canvas.drawCircle(mRipplePointX, mRipplePointY, mRippleRadius, mPaint);


    }

    @Override
    public void setAlpha(int alpha) {
        //设置 Drawable 的透明度
        mAlpha = alpha;
        onColorOrAlphaChange();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // 颜色滤镜
        mPaint.setColorFilter(colorFilter);
        // 刷新自己
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        int alpha = mPaint.getAlpha();
        if (alpha == 255){
            //不透明
          return  PixelFormat.OPAQUE;
        }
        if (alpha == 0){
            // 全透明
            return PixelFormat.TRANSPARENT;
        }
        // 其他情况即是 半透明
        return PixelFormat.TRANSLUCENT;
    }
}
