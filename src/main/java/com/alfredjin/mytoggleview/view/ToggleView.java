package com.alfredjin.mytoggleview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义ToggleButton
 *
 * @author Created by AlfredJin on 2016/10/10 14:21.
 */
public class ToggleView extends View {
    private Bitmap backgroundBitmap;//背景图片
    private Bitmap slideButton;//滑动按钮的图片
    private boolean slideState;//开关状态
    private Paint paint;    //画笔
    boolean isOnTouch = false;//滑动状态
    private float currentX;
    private onSwitchStateUpdateListener onSwitchStateUpdateListener;

    /**
     * 用于代码创建控件
     *
     * @param context
     */
    public ToggleView(Context context) {
        super(context);
        init();
    }

    /**
     * 用于在xml里使用，可指定自定义属性
     *
     * @param context
     * @param attrs
     */
    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    /**
     * 用于在xml里使用，可指定自定义属性,如果指定了样式，则使用此构造函数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        paint = new Paint();
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    /**
     * 绘制控件
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        //绘制滑块
        if (isOnTouch) {
            float newLeft = currentX - slideButton.getWidth() / 2.0f;
            int maxRight = backgroundBitmap.getWidth() - slideButton.getWidth();
            //限定范围
            if (newLeft < 0) { //限定左边
                newLeft = 0;
            } else if (newLeft > maxRight) {//限定右边
                newLeft = maxRight;
            }
            canvas.drawBitmap(slideButton, newLeft, 0, paint);
        } else {
            if (slideState) {
                //先得到开时的Button的位置
                int newLeft = backgroundBitmap.getWidth() - slideButton.getWidth();
                canvas.drawBitmap(slideButton, newLeft, 0, paint);
            } else {
                canvas.drawBitmap(slideButton, 0, 0, paint);
            }
        }
    }

    /**
     * 设置开关背景
     *
     * @param switch_background
     */
    public void setBackgroundBitmap(int switch_background) {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), switch_background);
    }


    /**
     * 设置按钮背景
     *
     * @param slide_button
     */
    public void setSlidButton(int slide_button) {
        slideButton = BitmapFactory.decodeResource(getResources(), slide_button);
    }

    /**
     * 开关状态
     *
     * @param b
     */
    public void setSlidState(boolean b) {
        this.slideState = b;
    }

    /**
     * 重写触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isOnTouch = true;
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isOnTouch = false;

                float center = backgroundBitmap.getWidth() / 2.0f;
                boolean state = currentX >= center;

                //如果状态改变且监听不为null则回传当前的状态
                if (state != slideState && onSwitchStateUpdateListener != null) {
                    //回传最新状态
                    onSwitchStateUpdateListener.onStateUpdate(state);
                }

                slideState = state;
                break;
        }
        //重新绘制
        invalidate();
        return true;
    }

    /**
     * 设置监听
     *
     * @param onSwitchStateUpdateListener
     */
    public void setOnSwitchStateUpdateListener(
            onSwitchStateUpdateListener onSwitchStateUpdateListener) {
        this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
    }

    /**
     * 设置监听状态是否改变的接口
     */
    public interface onSwitchStateUpdateListener {
        /**
         * 状态回调，把当前状态传出去
         *
         * @param state
         */
        void onStateUpdate(boolean state);
    }
}
