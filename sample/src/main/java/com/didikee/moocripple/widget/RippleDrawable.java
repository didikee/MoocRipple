package com.didikee.moocripple.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * Created by didik on 2017/2/8.
 */

public class RippleDrawable extends Drawable {

    private int mAlpha = 255;/*透明度范围 0 ~ 255*/
    private int mRippleColor = 0;
    private Paint mPaint = new Paint(
            Paint.ANTI_ALIAS_FLAG /*抗锯齿*/
    );

    public RippleDrawable() {
        // 开启抗锯齿,让曲线边缘更圆滑
        mPaint.setAntiAlias(true);
        // 开启防抖动
        mPaint.setDither(true);

        setRippleColor(0x30000000);
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
    }

    @Override
    public void draw(Canvas canvas) {
        //填充一个红色区域
        canvas.drawColor(0x70ff0000);
        //画一个圆
//        canvas.drawCircle();
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

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
