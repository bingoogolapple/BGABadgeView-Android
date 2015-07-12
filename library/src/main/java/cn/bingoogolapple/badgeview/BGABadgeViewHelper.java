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
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/6 下午10:13
 * 描述:
 */
public class BGABadgeViewHelper {
    private Bitmap mBitmap;
    private BGABadgeable mBadgeable;
    private Paint mBadgePaint;
    /**
     * 徽章背景色
     */
    private int mBadgeBgColor;
    /**
     * 徽章文本的颜色
     */
    private int mBadgeTextColor;
    /**
     * 徽章文本字体大小
     */
    private int mBadgeTextSize;
    /**
     * 徽章背景与宿主控件上下边缘间距离
     */
    private int mBadgeVerticalMargin;
    /**
     * 徽章背景与宿主控件左右边缘间距离
     */
    private int mBadgeHorizontalMargin;
    /***
     * 徽章文本边缘与徽章背景边缘间的距离
     */
    private int mBadgePadding;
    /**
     * 徽章文本
     */
    private String mBadgeText;
    /**
     * 徽章文本所占区域大小
     */
    private Rect mBadgeNumberRect;
    /**
     * 是否显示Badge
     */
    private boolean mIsShowBadge;
    /**
     * 徽章在宿主控件中的位置
     */
    private BadgeGravity mBadgeGravity;
    /**
     * 整个徽章所占区域
     */
    private RectF mBadgeRectF;
    /**
     * 是否可拖动
     */
    private boolean mDragable;
    /**
     * 拖动时的徽章控件
     */
    private BGADragBadgeView mDropBadgeView;
    /**
     * 是否正在拖动
     */
    private boolean mIsDraging;
    /**
     * 按下的点
     */
    private PointF mDownPointF;
    /**
     * 拖动mMoveHiddenThreshold距离后抬起手指徽章消失
     */
    private int mMoveHiddenThreshold;
    /**
     *
     */
    private BGADragDismissDelegate mDelegage;

    public BGABadgeViewHelper(BGABadgeable badgeable, Context context, AttributeSet attrs, BadgeGravity defaultBadgeGravity) {
        mBadgeable = badgeable;
        initDefaultAttrs(context, defaultBadgeGravity);
        initCustomAttrs(context, attrs);
        afterInitDefaultAndCustomAttrs();
        mDropBadgeView = new BGADragBadgeView(context, this);
    }

    private void initDefaultAttrs(Context context, BadgeGravity defaultBadgeGravity) {
        mBadgeNumberRect = new Rect();
        mBadgeRectF = new RectF();
        mBadgeBgColor = Color.RED;
        mBadgeTextColor = Color.WHITE;
        mBadgeTextSize = sp2px(context, 10);

        mBadgePaint = new Paint();
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        // 设置mBadgeText居中，保证mBadgeText长度为1时，文本也能居中
        mBadgePaint.setTextAlign(Paint.Align.CENTER);

        mBadgePadding = dp2px(context, 4);
        mBadgeVerticalMargin = dp2px(context, 4);
        mBadgeHorizontalMargin = dp2px(context, 4);

        mBadgeGravity = defaultBadgeGravity;
        mIsShowBadge = false;

        mBadgeText = null;

        mBitmap = null;

        mIsDraging = false;

        mDragable = true;

        mDownPointF = new PointF();

        mMoveHiddenThreshold = dp2px(context, 60);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BGABadgeView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.BGABadgeView_badge_bgColor) {
            mBadgeBgColor = typedArray.getColor(attr, mBadgeBgColor);
        } else if (attr == R.styleable.BGABadgeView_badge_textColor) {
            mBadgeTextColor = typedArray.getColor(attr, mBadgeTextColor);
        } else if (attr == R.styleable.BGABadgeView_badge_textSize) {
            mBadgeTextSize = typedArray.getDimensionPixelSize(attr, mBadgeTextSize);
        } else if (attr == R.styleable.BGABadgeView_badge_verticalMargin) {
            mBadgeVerticalMargin = typedArray.getDimensionPixelSize(attr, mBadgeVerticalMargin);
        } else if (attr == R.styleable.BGABadgeView_badge_horizontalMargin) {
            mBadgeHorizontalMargin = typedArray.getDimensionPixelSize(attr, mBadgeHorizontalMargin);
        } else if (attr == R.styleable.BGABadgeView_badge_padding) {
            mBadgePadding = typedArray.getDimensionPixelSize(attr, mBadgePadding);
        } else if (attr == R.styleable.BGABadgeView_badge_gravity) {
            int ordinal = typedArray.getInt(attr, mBadgeGravity.ordinal());
            mBadgeGravity = BadgeGravity.values()[ordinal];
        } else if (attr == R.styleable.BGABadgeView_badge_dragable) {
            mDragable = typedArray.getBoolean(attr, mDragable);
        }
    }

    private void afterInitDefaultAndCustomAttrs() {
        mBadgePaint.setTextSize(mBadgeTextSize);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDragable && mIsShowBadge && mBadgeRectF.contains(event.getX(), event.getY())) {
                    mDownPointF.set(event.getRawX(), event.getRawY());
                    mIsDraging = true;
                    mBadgeable.getParent().requestDisallowInterceptTouchEvent(true);
                    mDropBadgeView.onTouchEvent(event);
                    mBadgeable.postInvalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsDraging) {
                    mBadgeable.getParent().requestDisallowInterceptTouchEvent(true);
                    mDropBadgeView.onTouchEvent(event);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsDraging) {
                    mDropBadgeView.onTouchEvent(event);

                    float moveLength = PointF.length(event.getRawX() - mDownPointF.x, event.getRawY() - mDownPointF.y);
                    if (moveLength > mMoveHiddenThreshold) {
                        hiddenBadge();

                        if (mDelegage != null) {
                            mDelegage.onDismiss(mBadgeable);
                        }
                    } else {
                        mIsDraging = false;
                        mBadgeable.postInvalidate();
                    }
                    return true;
                }
                break;
            default:
                break;
        }
        return mBadgeable.callSuperOnTouchEvent(event);
    }

    public void drawBadge(Canvas canvas) {
        if (mIsShowBadge && !mIsDraging) {
            if (mBitmap != null) {
                drawDrawableBadge(canvas);
            } else {
                drawTextBadge(canvas);
            }
        }
    }

    /**
     * 绘制图像徽章
     *
     * @param canvas
     */
    private void drawDrawableBadge(Canvas canvas) {
        mBadgeRectF.left = mBadgeable.getWidth() - mBadgeHorizontalMargin - mBitmap.getWidth();
        mBadgeRectF.top = mBadgeVerticalMargin;
        switch (mBadgeGravity) {
            case RightTop:
                mBadgeRectF.top = mBadgeVerticalMargin;
                break;
            case RightCenter:
                mBadgeRectF.top = (mBadgeable.getHeight() - mBitmap.getHeight()) / 2;
                break;
            case RightBottom:
                mBadgeRectF.top = mBadgeable.getHeight() - mBitmap.getHeight() - mBadgeVerticalMargin;
                break;
            default:
                break;
        }
        canvas.drawBitmap(mBitmap, mBadgeRectF.left, mBadgeRectF.top, mBadgePaint);
        mBadgeRectF.right = mBadgeRectF.left + mBitmap.getWidth();
        mBadgeRectF.bottom = mBadgeRectF.top + mBitmap.getHeight();
    }

    /**
     * 绘制文字徽章
     *
     * @param canvas
     */
    private void drawTextBadge(Canvas canvas) {
        String badgeText = "";
        if (!TextUtils.isEmpty(mBadgeText)) {
            badgeText = mBadgeText;
        }
        // 获取文本宽所占宽高
        mBadgePaint.getTextBounds(badgeText, 0, badgeText.length(), mBadgeNumberRect);
        // 计算徽章背景的宽高
        int badgeHeight = mBadgeNumberRect.height() + mBadgePadding * 2;
        int badgeWidth;
        // 当mBadgeText的长度为1时，计算出来的高度会比宽度大，此时设置宽度等于高度
        if (badgeText.length() == 1) {
            badgeWidth = badgeHeight;
        } else {
            badgeWidth = mBadgeNumberRect.width() + mBadgePadding * 2;
        }

        // 计算徽章背景上下的值
        mBadgeRectF.top = mBadgeVerticalMargin;
        mBadgeRectF.bottom = mBadgeable.getHeight() - mBadgeVerticalMargin;
        switch (mBadgeGravity) {
            case RightTop:
                mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
                break;
            case RightCenter:
                mBadgeRectF.top = (mBadgeable.getHeight() - badgeHeight) / 2;
                mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
                break;
            case RightBottom:
                mBadgeRectF.top = mBadgeRectF.bottom - badgeHeight;
                break;
            default:
                break;
        }

        // 计算徽章背景左右的值
        mBadgeRectF.right = mBadgeable.getWidth() - mBadgeHorizontalMargin;
        mBadgeRectF.left = mBadgeRectF.right - badgeWidth;

        // 设置徽章背景色
        mBadgePaint.setColor(mBadgeBgColor);
        // 绘制徽章背景
        canvas.drawRoundRect(mBadgeRectF, badgeHeight / 2, badgeHeight / 2, mBadgePaint);

        if (!TextUtils.isEmpty(mBadgeText)) {
            // 设置徽章文本颜色
            mBadgePaint.setColor(mBadgeTextColor);
            // initDefaultAttrs方法中设置了mBadgeText居中，此处的x为徽章背景的中心点y
            float x = mBadgeRectF.left + badgeWidth / 2;
            // 注意：绘制文本时的y是指文本底部，而不是文本的中间
            float y = mBadgeRectF.bottom - mBadgePadding;
            // 绘制徽章文本
            canvas.drawText(badgeText, x, y, mBadgePaint);
        }
    }

    public void showCirclePointBadge() {
        showTextBadge(null);
    }

    public void showTextBadge(String badgeText) {
        mBitmap = null;
        mBadgeText = badgeText;
        mIsShowBadge = true;
        mBadgeable.postInvalidate();
    }

    public void hiddenBadge() {
        mIsShowBadge = false;
        mBitmap = null;
        mBadgeable.postInvalidate();
    }

    public void showDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        mIsShowBadge = true;
        mBadgeable.postInvalidate();
    }

    public RectF getBadgeRectF() {
        return mBadgeRectF;
    }

    public int getBadgePadding() {
        return mBadgePadding;
    }

    public String getBadgeText() {
        return mBadgeText;
    }

    public int getBadgeBgColor() {
        return mBadgeBgColor;
    }

    public int getBadgeTextColor() {
        return mBadgeTextColor;
    }

    public int getBadgeTextSize() {
        return mBadgeTextSize;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setDragDismissDelegage(BGADragDismissDelegate delegage) {
        mDelegage = delegage;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public enum BadgeGravity {
        RightTop,
        RightCenter,
        RightBottom
    }
}