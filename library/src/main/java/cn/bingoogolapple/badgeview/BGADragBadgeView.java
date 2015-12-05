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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

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
    private BGAExplosionAnimator mExplosionAnimator;

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
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mLayoutParams.width = mWindowManager.getDefaultDisplay().getWidth();
        mLayoutParams.height = mWindowManager.getDefaultDisplay().getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (mExplosionAnimator == null) {
                if (mBadgeViewHelper.isShowDrawable()) {
                    drawDrawableBadge(canvas);
                } else {
                    drawTextBadge(canvas);
                }
            } else {
                mExplosionAnimator.draw(canvas);
            }
        } catch (Exception e) {
            removeSelf();
        }
    }

    private void drawDrawableBadge(Canvas canvas) {
        canvas.drawBitmap(mBadgeViewHelper.getBitmap(), mStartX, mStartY, mBadgePaint);
    }

    private void drawTextBadge(Canvas canvas) {
        // 设置徽章背景色
        mBadgePaint.setColor(mBadgeViewHelper.getBadgeBgColor());
        // 绘制徽章背景
        canvas.drawRoundRect(new RectF(mStartX, mStartY, mStartX + mBadgeViewHelper.getBadgeRectF().width(), mStartY + mBadgeViewHelper.getBadgeRectF().height()), mBadgeViewHelper.getBadgeRectF().height() / 2, mBadgeViewHelper.getBadgeRectF().height() / 2, mBadgePaint);

        // 设置徽章文本颜色
        mBadgePaint.setColor(mBadgeViewHelper.getBadgeTextColor());
        float x = mStartX + mBadgeViewHelper.getBadgeRectF().width() / 2;
        // 注意：绘制文本时的y是指文本底部，而不是文本的中间
        float y = mStartY + mBadgeViewHelper.getBadgeRectF().height() - mBadgeViewHelper.getBadgePadding();
        // 绘制徽章文本
        String badgeText = mBadgeViewHelper.getBadgeText() == null ? "" : mBadgeViewHelper.getBadgeText();
        canvas.drawText(badgeText, x, y, mBadgePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mExplosionAnimator == null && getParent() == null) {
                    mStartX = (int) (event.getRawX() - mBadgeViewHelper.getBadgeRectF().width() / 2);
                    mStartY = (int) (event.getRawY() - mBadgeViewHelper.getBadgeRectF().height() / 2) - BGABadgeViewUtil.getStatusBarHeight(getContext());

                    mWindowManager.addView(this, mLayoutParams);
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mExplosionAnimator == null && getParent() != null) {
                    mStartX = getNewX(event);
                    mStartY = getNewY(event);
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mExplosionAnimator == null && getParent() != null && mBadgeViewHelper.satisfyMoveDismissCondition(event)) {
                    try {
                        startDismissAnim(getNewX(event), getNewY(event));
                    } catch (Exception e) {
                        removeSelf();
                    }
                } else {
                    removeSelf();
                }
                break;
        }
        return true;
    }

    private int getNewX(MotionEvent event) {
        int badgeWidth = (int) mBadgeViewHelper.getBadgeRectF().width();
        int newX = (int) event.getRawX() - badgeWidth / 2;
        if (newX < 0) {
            newX = 0;
        }
        if (newX > mWindowManager.getDefaultDisplay().getWidth() - badgeWidth) {
            newX = mWindowManager.getDefaultDisplay().getWidth() - badgeWidth;
        }
        return newX;
    }

    private int getNewY(MotionEvent event) {
        int badgeHeight = (int) mBadgeViewHelper.getBadgeRectF().height();
        int newY = (int) event.getRawY() - badgeHeight / 2 - BGABadgeViewUtil.getStatusBarHeight(getContext());
        if (newY < 0) {
            newY = 0;
        }
        if (newY > mWindowManager.getDefaultDisplay().getHeight() - badgeHeight) {
            newY = mWindowManager.getDefaultDisplay().getHeight() - badgeHeight;
        }
        return newY;
    }

    private void startDismissAnim(int newX, int newY) {
        int badgeWidth = (int) mBadgeViewHelper.getBadgeRectF().width();
        int badgeHeight = (int) mBadgeViewHelper.getBadgeRectF().height();
        Rect rect = new Rect(newX - badgeWidth / 2, newY - badgeHeight / 2, newX + badgeWidth / 2, newY + badgeHeight / 2);

        Bitmap badgeBitmap = BGABadgeViewUtil.createBitmapSafely(this, rect, 1);
        if (badgeBitmap == null) {
            removeSelf();
        }

        mExplosionAnimator = new BGAExplosionAnimator(this, rect, badgeBitmap);
        mExplosionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeSelf();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                removeSelf();
            }
        });
        mExplosionAnimator.start();
    }

    private void removeSelf() {
        if (getParent() != null) {
            mWindowManager.removeView(this);
            mExplosionAnimator = null;
        }
    }
}