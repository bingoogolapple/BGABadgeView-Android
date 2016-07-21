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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;

import java.lang.ref.WeakReference;


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
    private SetExplosionAnimatorNullTask mSetExplosionAnimatorNullTask;

    /**
     * 针圆切线的切点
     */
    private PointF[] mStickPoints = new PointF[]{
            new PointF(0, 0),
            new PointF(0, 0)
    };
    /**
     * 拖拽圆切线的切点
     */
    private PointF[] mDragPoints = new PointF[]{
            new PointF(0, 0),
            new PointF(0, 0)
    };
    /**
     * 控制点
     */
    private PointF mControlPoint = new PointF(0, 0);
    /**
     * 拖拽圆中心点
     */
    private PointF mDragCenter = new PointF(0, 0);
    /**
     * 拖拽圆半径
     */
    private float mDragRadius;

    /**
     * 针圆中心点
     */
    private PointF mStickCenter;
    /**
     * 针圆半径
     */
    private float mStickRadius;
    /**
     * 拖拽圆最大半径
     */
    private int mMaxDragRadius;
    /**
     * 拖拽圆半径和针圆半径的差值
     */
    private int mDragStickRadiusDifference;
    /**
     * 拖动mDismissThreshold距离后抬起手指徽章消失
     */
    private int mDismissThreshold;

    private boolean mDismissAble;
    private boolean mIsDragDisappear;

    public BGADragBadgeView(Context context, BGABadgeViewHelper badgeViewHelper) {
        super(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mBadgeViewHelper = badgeViewHelper;
        initBadgePaint();
        initLayoutParams();
        initStick();

        mSetExplosionAnimatorNullTask = new SetExplosionAnimatorNullTask(this);
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
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    private void initStick() {
        mMaxDragRadius = BGABadgeViewUtil.dp2px(getContext(), 10);
        mDragStickRadiusDifference = BGABadgeViewUtil.dp2px(getContext(), 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            if (mExplosionAnimator == null) {
                if (mBadgeViewHelper.isShowDrawable()) {
                    if (mBadgeViewHelper.getBadgeBgColor() == Color.RED) {
                        mBadgePaint.setColor(mBadgeViewHelper.getBitmap().getPixel(mBadgeViewHelper.getBitmap().getWidth() / 2, mBadgeViewHelper.getBitmap().getHeight() / 2));
                    } else {
                        mBadgePaint.setColor(mBadgeViewHelper.getBadgeBgColor());
                    }
                    drawStick(canvas);
                    drawDrawableBadge(canvas);
                } else {
                    mBadgePaint.setColor(mBadgeViewHelper.getBadgeBgColor());
                    drawStick(canvas);
                    drawTextBadge(canvas);
                }
            } else {
                mExplosionAnimator.draw(canvas);
            }
        } catch (Exception e) {
            // 确保自己能被移除
            removeSelfWithException();
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

    private void drawStick(Canvas canvas) {
        float currentStickRadius = getCurrentStickRadius();

        // 2. 获取直线与圆的交点
        float yOffset = mStickCenter.y - mDragCenter.y;
        float xOffset = mStickCenter.x - mDragCenter.x;
        Double lineK = null;
        if (xOffset != 0) {
            lineK = (double) (yOffset / xOffset);
        }
        // 通过几何图形工具获取交点坐标
        mDragPoints = BGABadgeViewUtil.getIntersectionPoints(mDragCenter, mDragRadius, lineK);
        mStickPoints = BGABadgeViewUtil.getIntersectionPoints(mStickCenter, currentStickRadius, lineK);

        // 3. 获取控制点坐标
        mControlPoint = BGABadgeViewUtil.getMiddlePoint(mDragCenter, mStickCenter);

        // 保存画布状态
        canvas.save();
        canvas.translate(0, -BGABadgeViewUtil.getStatusBarHeight(mBadgeViewHelper.getRootView()));

        if (!mIsDragDisappear) {
            if (!mDismissAble) {

                // 3. 画连接部分
                Path path = new Path();
                // 跳到点1
                path.moveTo(mStickPoints[0].x, mStickPoints[0].y);
                // 画曲线1 -> 2
                path.quadTo(mControlPoint.x, mControlPoint.y, mDragPoints[0].x, mDragPoints[0].y);
                // 画直线2 -> 3
                path.lineTo(mDragPoints[1].x, mDragPoints[1].y);
                // 画曲线3 -> 4
                path.quadTo(mControlPoint.x, mControlPoint.y, mStickPoints[1].x, mStickPoints[1].y);
                path.close();
                canvas.drawPath(path, mBadgePaint);

                // 2. 画固定圆
                canvas.drawCircle(mStickCenter.x, mStickCenter.y, currentStickRadius, mBadgePaint);
            }

            // 1. 画拖拽圆
            canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, mBadgePaint);
        }

        // 恢复上次的保存状态
        canvas.restore();
    }

    /**
     * 获取针圆实时半径
     *
     * @return
     */
    private float getCurrentStickRadius() {
        /**
         * distance 0 -> mDismissThreshold
         * percent 0.0f -> 1.0f
         * currentStickRadius mStickRadius * 100% -> mStickRadius * 20%
         */
        float distance = BGABadgeViewUtil.getDistanceBetween2Points(mDragCenter, mStickCenter);
        distance = Math.min(distance, mDismissThreshold);
        float percent = distance / mDismissThreshold;
        return BGABadgeViewUtil.evaluate(percent, mStickRadius, mStickRadius * 0.2f);
    }

    public void setStickCenter(float x, float y) {
        mStickCenter = new PointF(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handleActionDown(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    handleActionMove(event);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handleActionUp(event);
                    break;
            }
        } catch (Exception e) {
            // 确保自己能被移除
            removeSelfWithException();
        }
        return true;
    }

    private void handleActionDown(MotionEvent event) {
        if (mExplosionAnimator == null && getParent() == null) {
            mDragRadius = Math.min(mBadgeViewHelper.getBadgeRectF().width() / 2, mMaxDragRadius);
            mStickRadius = mDragRadius - mDragStickRadiusDifference;
            mDismissThreshold = (int) (mStickRadius * 10);

            mDismissAble = false;
            mIsDragDisappear = false;

            mWindowManager.addView(this, mLayoutParams);

            updateDragPosition(event.getRawX(), event.getRawY());
        }
    }

    private void handleActionMove(MotionEvent event) {
        if (mExplosionAnimator == null && getParent() != null) {
            updateDragPosition(event.getRawX(), event.getRawY());

            // 处理断开事件
            if (BGABadgeViewUtil.getDistanceBetween2Points(mDragCenter, mStickCenter) > mDismissThreshold) {
                mDismissAble = true;
                postInvalidate();
            } else if (mBadgeViewHelper.isResumeTravel()) {
                mDismissAble = false;
                postInvalidate();
            }
        }
    }

    private void handleActionUp(MotionEvent event) {
        handleActionMove(event);

        if (mDismissAble) {
            // 拖拽点超出过范围
            if (BGABadgeViewUtil.getDistanceBetween2Points(mDragCenter, mStickCenter) > mDismissThreshold) {
                // 现在也超出范围,消失
                try {
                    mIsDragDisappear = true;
                    startDismissAnim(getNewStartX(event.getRawX()), getNewStartY(event.getRawY()));
                } catch (Exception e) {
                    removeSelf();
                    mBadgeViewHelper.endDragWithDismiss();
                }
            } else {
                // 现在没有超出范围,放回去
                removeSelf();
                mBadgeViewHelper.endDragWithoutDismiss();
            }
        } else {
            //	拖拽点没超出过范围,弹回去
            try {
                startSpringAnim();
            } catch (Exception e) {
                removeSelf();
                mBadgeViewHelper.endDragWithoutDismiss();
            }
        }
    }

    private void startSpringAnim() {
        final PointF startReleaseDragCenter = new PointF(mDragCenter.x, mDragCenter.y);
        ValueAnimator springAnim = ValueAnimator.ofFloat(1.0f);
        springAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator mAnim) {
                // 0.0 -> 1.0f
                float percent = mAnim.getAnimatedFraction();
                PointF p = BGABadgeViewUtil.getPointByPercent(startReleaseDragCenter, mStickCenter, percent);
                updateDragPosition(p.x, p.y);
            }
        });
        springAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeSelf();
                mBadgeViewHelper.endDragWithoutDismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                removeSelf();
                mBadgeViewHelper.endDragWithoutDismiss();
            }
        });

        springAnim.setInterpolator(new OvershootInterpolator(4));
        springAnim.setRepeatCount(1);
        springAnim.setRepeatMode(ValueAnimator.INFINITE);
        springAnim.setDuration(BGAExplosionAnimator.ANIM_DURATION / 2);
        springAnim.start();
    }

    private void startDismissAnim(int newX, int newY) {
        int badgeWidth = (int) mBadgeViewHelper.getBadgeRectF().width();
        int badgeHeight = (int) mBadgeViewHelper.getBadgeRectF().height();
        Rect rect = new Rect(newX - badgeWidth / 2, newY - badgeHeight / 2, newX + badgeWidth / 2, newY + badgeHeight / 2);

        Bitmap badgeBitmap = BGABadgeViewUtil.createBitmapSafely(this, rect, 1);
        if (badgeBitmap == null) {
            removeSelf();
            mBadgeViewHelper.endDragWithDismiss();
            return;
        }

        if (mExplosionAnimator != null) {
            removeSelf();
            mBadgeViewHelper.endDragWithDismiss();
            return;
        }

        mExplosionAnimator = new BGAExplosionAnimator(this, rect, badgeBitmap);
        mExplosionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeSelf();
                mBadgeViewHelper.endDragWithDismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                removeSelf();
                mBadgeViewHelper.endDragWithDismiss();
            }
        });
        mExplosionAnimator.start();
    }

    private void removeSelf() {
        if (getParent() != null) {
            mWindowManager.removeView(this);
        }
        mDismissAble = false;
        mIsDragDisappear = false;

        // 处理有时候爆炸效果结束后出现一瞬间的拖拽效果
        postDelayed(mSetExplosionAnimatorNullTask, 60);
    }

    /**
     * 修改拖拽位置
     *
     * @param rawX
     * @param rawY
     */
    private void updateDragPosition(float rawX, float rawY) {
        mStartX = getNewStartX(rawX);
        mStartY = getNewStartY(rawY);

        mDragCenter.set(rawX, rawY);
        postInvalidate();
    }

    private int getNewStartX(float rawX) {
        int badgeWidth = (int) mBadgeViewHelper.getBadgeRectF().width();
        int newX = (int) rawX - badgeWidth / 2;
        if (newX < 0) {
            newX = 0;
        }
        if (newX > mWindowManager.getDefaultDisplay().getWidth() - badgeWidth) {
            newX = mWindowManager.getDefaultDisplay().getWidth() - badgeWidth;
        }
        return newX;
    }

    private int getNewStartY(float rawY) {
        int badgeHeight = (int) mBadgeViewHelper.getBadgeRectF().height();
        int maxNewY = getHeight() - badgeHeight;
        int newStartY = (int) rawY - badgeHeight / 2 - BGABadgeViewUtil.getStatusBarHeight(mBadgeViewHelper.getRootView());
        return Math.min(Math.max(0, newStartY), maxNewY);
    }

    private void removeSelfWithException() {
        removeSelf();
        if (BGABadgeViewUtil.getDistanceBetween2Points(mDragCenter, mStickCenter) > mDismissThreshold) {
            mBadgeViewHelper.endDragWithDismiss();
        } else {
            mBadgeViewHelper.endDragWithoutDismiss();
        }
    }

    private static class SetExplosionAnimatorNullTask implements Runnable {
        private final WeakReference<BGADragBadgeView> mDragBadgeView;

        public SetExplosionAnimatorNullTask(BGADragBadgeView dragBadgeView) {
            mDragBadgeView = new WeakReference<>(dragBadgeView);
        }

        @Override
        public void run() {
            BGADragBadgeView dragBadgeView = mDragBadgeView.get();
            if (dragBadgeView != null) {
                dragBadgeView.mExplosionAnimator = null;
            }
        }
    }
}