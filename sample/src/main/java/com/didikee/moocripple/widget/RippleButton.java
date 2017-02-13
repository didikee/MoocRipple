package com.didikee.moocripple.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by didik on 2017/2/13.
 */

public class RippleButton extends Button {

    private RippleDrawable mRippleDrawable;

    public RippleButton(Context context) {
        this(context,null);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRippleDrawable = new RippleDrawable();
        /**
         * {@link android.graphics.drawable.Drawable.Callback View 已经实现了,即所有的View都实现了}
         * 设置刷新接口
         */
        mRippleDrawable.setCallback(this);

        /**
         * 如果设置背景,则只有{@link RippleButton#onTouchEvent(MotionEvent)} 方法需要重写,
         * 其他的都被{@link android.view.View#setBackgroundDrawable(Drawable)}实现了
         */
//        setBackgroundDrawable(mRippleDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制自己的 Drawable
        mRippleDrawable.draw(canvas);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 设置 Drawable 绘制和刷新的区域
        mRippleDrawable.setBounds(0,0,getWidth(),getHeight());
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return mRippleDrawable == who || super.verifyDrawable(who);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        invalidate();
//        Button 不更新,通知 Drawable 去更新
        mRippleDrawable.onTouch(event);
        return true;
    }
}
