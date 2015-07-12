/**
 * Copyright 2015 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.bingoogolapple.badgeview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/12 下午3:23
 * 描述:
 */
class BGADragBadgeView extends View {
    private static final String TAG = BGADragBadgeView.class.getSimpleName();
    private BGABadgeViewHelper mBadgeViewHelper;
    private Paint mBadgePaint;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private int mStartX;
    private int mStartY;

    public BGADragBadgeView(Context context, BGABadgeViewHelper badgeViewHelper) {
        super(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mBadgeViewHelper = badgeViewHelper;
        initBadgePaint();
        initLayoutParams();
    }

    private void initBadgePaint() {
        mBadgePaint = new Paint();
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        // 设置mBadgeText居中，保证mBadgeText长度为1时，文本也能居中
        mBadgePaint.setTextAlign(Paint.Align.CENTER);
        mBadgePaint.setTextSize(mBadgeViewHelper.getBadgeTextSize());
    }

    private void initLayoutParams() {
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.gravity = Gravity.LEFT + Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBadgeViewHelper.getBitmap() != null) {
            drawDrawableBadge(canvas);
        } else {
            drawTextBadge(canvas);
        }
    }

    private void drawDrawableBadge(Canvas canvas) {
        canvas.drawBitmap(mBadgeViewHelper.getBitmap(), 0, 0, mBadgePaint);
    }

    private void drawTextBadge(Canvas canvas) {
        // 设置徽章背景色
        mBadgePaint.setColor(mBadgeViewHelper.getBadgeBgColor());
        // 绘制徽章背景
        canvas.drawRoundRect(new RectF(0, 0, mBadgeViewHelper.getBadgeRectF().width(), mBadgeViewHelper.getBadgeRectF().height()), mBadgeViewHelper.getBadgeRectF().height() / 2, mBadgeViewHelper.getBadgeRectF().height() / 2, mBadgePaint);

        // 设置徽章文本颜色
        mBadgePaint.setColor(mBadgeViewHelper.getBadgeTextColor());
        float x = mBadgeViewHelper.getBadgeRectF().width() / 2;
        // 注意：绘制文本时的y是指文本底部，而不是文本的中间
        float y = mBadgeViewHelper.getBadgeRectF().height() - mBadgeViewHelper.getBadgePadding();
        // 绘制徽章文本
        String badgeText = mBadgeViewHelper.getBadgeText() == null ? "" : mBadgeViewHelper.getBadgeText();
        canvas.drawText(badgeText, x, y, mBadgePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLayoutParams.width = (int) mBadgeViewHelper.getBadgeRectF().width();
                mLayoutParams.height = (int) mBadgeViewHelper.getBadgeRectF().height();

                mStartX = (int) event.getRawX() - mLayoutParams.width / 2;
                mStartY = (int) event.getRawY() - mLayoutParams.height / 2 - getStatusBarHeight(getContext());
                mLayoutParams.x = mStartX;
                mLayoutParams.y = mStartY;
                mWindowManager.addView(this, mLayoutParams);
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                int newX = (int) event.getRawX() - mLayoutParams.width / 2;
                int newY = (int) event.getRawY() - mLayoutParams.height / 2 - getStatusBarHeight(getContext());
                int dx = newX - mStartX;
                int dy = newY - mStartY;
                mLayoutParams.x += dx;
                mLayoutParams.y += dy;
                if (mLayoutParams.x < 0) {
                    mLayoutParams.x = 0;
                }
                if (mLayoutParams.y < 0) {
                    mLayoutParams.y = 0;
                }
                if (mLayoutParams.x > mWindowManager.getDefaultDisplay().getWidth()) {
                    mLayoutParams.x = mWindowManager.getDefaultDisplay().getWidth();
                }
                if (mLayoutParams.y > mWindowManager.getDefaultDisplay().getHeight()) {
                    mLayoutParams.y = mWindowManager.getDefaultDisplay().getHeight();
                }
                mWindowManager.updateViewLayout(this, mLayoutParams);
                mStartX = newX;
                mStartY = newY;

                break;
            case MotionEvent.ACTION_UP:
                mWindowManager.removeView(this);
                break;
        }
        return true;
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

}
