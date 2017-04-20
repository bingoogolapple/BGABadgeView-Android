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

package easy.badge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import easy.badge.impl.BadgeViewHelper;
import easy.badge.impl.BadgeView;
import easy.badge.impl.OnBadgeDragDismissListener;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/6 下午10:13
 * 描述:
 */
class BadgeHelperImpl implements BadgeViewHelper {

    private Bitmap mBitmap;
    private BadgeView mBadgeViewTarget;
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
    @VerticalGravity
    private int mBadgeGravityV;
    @HorizontalGravity
    private int mBadgeGravityH;
    /**
     * 整个徽章所占区域
     */
    private RectF mBadgeRectF;
    /**
     * 是否可拖动
     */
    private boolean mDragable;
    /**
     * 拖拽徽章超出轨迹范围后，再次放回到轨迹范围时，是否恢复轨迹
     */
    private boolean mIsResumeTravel;
    /***
     * 徽章描边宽度
     */
    private int mBadgeBorderWidth;
    /***
     * 徽章描边颜色
     */
    private int mBadgeBorderColor;
    /**
     * 触发开始拖拽徽章事件的扩展触摸距离
     */
    private int mDragExtra;
    /**
     * 整个徽章加上其触发开始拖拽区域所占区域
     */
    private RectF mBadgeDragExtraRectF;
    /**
     * 拖动时的徽章控件
     */
    private BadgeDragView mDropBadgeView;
    /**
     * 是否正在拖动
     */
    private boolean mIsDraging;
    /**
     * 拖动大于BadgeViewHelper.mMoveHiddenThreshold后抬起手指徽章消失的代理
     */
    private OnBadgeDragDismissListener mDragDismissListenner;
    private boolean mIsShowDrawable = false;

    public BadgeHelperImpl(BadgeView badgeable, Context context, AttributeSet attrs, @HorizontalGravity int defaultBadgeGravityH,@VerticalGravity int defaultBadgeGravityV) {
        mBadgeViewTarget = badgeable;
        initDefaultAttrs(context, defaultBadgeGravityH,defaultBadgeGravityV);
        initCustomAttrs(context, attrs);
        afterInitDefaultAndCustomAttrs();
        mDropBadgeView = Badge.createDragView(context, this);
    }

    private void initDefaultAttrs(Context context,@HorizontalGravity int defaultBadgeGravityH,@VerticalGravity int defaultBadgeGravityV) {
        mBadgeNumberRect = new Rect();
        mBadgeRectF = new RectF();
        mBadgeBgColor = Color.RED;
        mBadgeTextColor = Color.WHITE;
        mBadgeTextSize = BadgeViewUtil.sp2px(context, 10);

        mBadgePaint = new Paint();
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        // 设置mBadgeText居中，保证mBadgeText长度为1时，文本也能居中
        mBadgePaint.setTextAlign(Paint.Align.CENTER);

        mBadgePadding = BadgeViewUtil.dp2px(context, 4);
        mBadgeVerticalMargin = BadgeViewUtil.dp2px(context, 4);
        mBadgeHorizontalMargin = BadgeViewUtil.dp2px(context, 4);

        mBadgeGravityH = defaultBadgeGravityH;
        mBadgeGravityV = defaultBadgeGravityV;
        mIsShowBadge = false;

        mBadgeText = null;

        mBitmap = null;

        mIsDraging = false;

        mDragable = false;

        mBadgeBorderColor = Color.WHITE;

        mDragExtra = BadgeViewUtil.dp2px(context, 4);
        mBadgeDragExtraRectF = new RectF();
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.BadgeView_badge_bgColor) {
            mBadgeBgColor = typedArray.getColor(attr, mBadgeBgColor);
        } else if (attr == R.styleable.BadgeView_badge_textColor) {
            mBadgeTextColor = typedArray.getColor(attr, mBadgeTextColor);
        } else if (attr == R.styleable.BadgeView_badge_textSize) {
            mBadgeTextSize = typedArray.getDimensionPixelSize(attr, mBadgeTextSize);
        } else if (attr == R.styleable.BadgeView_badge_verticalMargin) {
            mBadgeVerticalMargin = typedArray.getDimensionPixelSize(attr, mBadgeVerticalMargin);
        } else if (attr == R.styleable.BadgeView_badge_horizontalMargin) {
            mBadgeHorizontalMargin = typedArray.getDimensionPixelSize(attr, mBadgeHorizontalMargin);
        } else if (attr == R.styleable.BadgeView_badge_padding) {
            mBadgePadding = typedArray.getDimensionPixelSize(attr, mBadgePadding);
        } else if (attr == R.styleable.BadgeView_badge_gravityH) {
            @HorizontalGravity int ordinal = typedArray.getInt(attr, HorizontalGravity.RIGHT);
            mBadgeGravityH = ordinal;
        }else if (attr == R.styleable.BadgeView_badge_gravityV) {
            @VerticalGravity int ordinal = typedArray.getInt(attr, VerticalGravity.TOP);
            mBadgeGravityV = ordinal;
        } else if (attr == R.styleable.BadgeView_badge_dragable) {
            mDragable = typedArray.getBoolean(attr, mDragable);
        } else if (attr == R.styleable.BadgeView_badge_isResumeTravel) {
            mIsResumeTravel = typedArray.getBoolean(attr, mIsResumeTravel);
        } else if (attr == R.styleable.BadgeView_badge_borderWidth) {
            mBadgeBorderWidth = typedArray.getDimensionPixelSize(attr, mBadgeBorderWidth);
        } else if (attr == R.styleable.BadgeView_badge_borderColor) {
            mBadgeBorderColor = typedArray.getColor(attr, mBadgeBorderColor);
        } else if (attr == R.styleable.BadgeView_badge_dragExtra) {
            mDragExtra = typedArray.getDimensionPixelSize(attr, mDragExtra);
        }
    }

    private void afterInitDefaultAndCustomAttrs() {
        mBadgePaint.setTextSize(mBadgeTextSize);
    }

    public void setBadgeBgColorInt(int badgeBgColor) {
        mBadgeBgColor = badgeBgColor;
        mBadgeViewTarget.postInvalidate();
    }

    public void setBadgeTextColorInt(int badgeTextColor) {
        mBadgeTextColor = badgeTextColor;
        mBadgeViewTarget.postInvalidate();
    }

    public void setBadgeTextSizeSp(int badgetextSize) {
        if (badgetextSize >= 0) {
            mBadgeTextSize = BadgeViewUtil.sp2px(mBadgeViewTarget.getContext(), badgetextSize);
            mBadgePaint.setTextSize(mBadgeTextSize);
            mBadgeViewTarget.postInvalidate();
        }
    }

    public void setBadgeVerticalMarginDp(int badgeVerticalMargin) {
        if (badgeVerticalMargin >= 0) {
            mBadgeVerticalMargin = BadgeViewUtil.dp2px(mBadgeViewTarget.getContext(), badgeVerticalMargin);
            mBadgeViewTarget.postInvalidate();
        }
    }

    public void setBadgeHorizontalMarginDp(int badgeHorizontalMargin) {
        if (badgeHorizontalMargin >= 0) {
            mBadgeHorizontalMargin = BadgeViewUtil.dp2px(mBadgeViewTarget.getContext(), badgeHorizontalMargin);
            mBadgeViewTarget.postInvalidate();
        }
    }

    public void setBadgePaddingDp(int badgePadding) {
        if (badgePadding >= 0) {
            mBadgePadding = BadgeViewUtil.dp2px(mBadgeViewTarget.getContext(), badgePadding);
            mBadgeViewTarget.postInvalidate();
        }
    }

    public void setBadgeHorizontalGravity(@HorizontalGravity int badgeGravity) {
        if (mBadgeGravityH != badgeGravity) {
            mBadgeGravityH = badgeGravity;
            mBadgeViewTarget.postInvalidate();
        }
    }

    public void setBadgeVerticalGravity(@VerticalGravity int badgeGravity) {
        if (mBadgeGravityV != badgeGravity) {
            mBadgeGravityV = badgeGravity;
            mBadgeViewTarget.postInvalidate();
        }
    }

    public void setDragable(boolean dragable) {
        mDragable = dragable;
        mBadgeViewTarget.postInvalidate();
    }

    public void setIsResumeTravel(boolean isResumeTravel) {
        mIsResumeTravel = isResumeTravel;
        mBadgeViewTarget.postInvalidate();
    }

    public void setBadgeBorderWidthDp(int badgeBorderWidthDp) {
        if (badgeBorderWidthDp >= 0) {
            mBadgeBorderWidth = BadgeViewUtil.dp2px(mBadgeViewTarget.getContext(), badgeBorderWidthDp);
            mBadgeViewTarget.postInvalidate();
        }
    }

    public void setBadgeBorderColorInt(int badgeBorderColor) {
        mBadgeBorderColor = badgeBorderColor;
        mBadgeViewTarget.postInvalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mBadgeDragExtraRectF.left = mBadgeRectF.left - mDragExtra;
                mBadgeDragExtraRectF.top = mBadgeRectF.top - mDragExtra;
                mBadgeDragExtraRectF.right = mBadgeRectF.right + mDragExtra;
                mBadgeDragExtraRectF.bottom = mBadgeRectF.bottom + mDragExtra;

                if ((mBadgeBorderWidth == 0 || mIsShowDrawable) && mDragable && mIsShowBadge && mBadgeDragExtraRectF.contains(event.getX(), event.getY())) {
                    mIsDraging = true;
                    mBadgeViewTarget.getParent().requestDisallowInterceptTouchEvent(true);

                    Rect badgeableRect = new Rect();
                    mBadgeViewTarget.getGlobalVisibleRect(badgeableRect);
                    mDropBadgeView.setStickCenter(badgeableRect.left + mBadgeRectF.left + mBadgeRectF.width() / 2, badgeableRect.top + mBadgeRectF.top + mBadgeRectF.height() / 2);

                    mDropBadgeView.onTouchEvent(event);
                    mBadgeViewTarget.postInvalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsDraging) {
                    mDropBadgeView.onTouchEvent(event);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsDraging) {
                    mDropBadgeView.onTouchEvent(event);
                    mIsDraging = false;
                    return true;
                }
                break;
            default:
                break;
        }
        return mBadgeViewTarget.callSuperOnTouchEvent(event);
    }

    public void endDragWithDismiss() {
        hiddenBadge();
        if (mDragDismissListenner != null) {
            mDragDismissListenner.onBadgeDismiss(mBadgeViewTarget);
        }
    }

    public void endDragWithoutDismiss() {
        mBadgeViewTarget.postInvalidate();
    }

    public void drawBadge(Canvas canvas) {
        if (mIsShowBadge && !mIsDraging) {
            if (mIsShowDrawable) {
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
        int badgeWidth = mBitmap.getWidth();
        int badgeHeight = mBitmap.getHeight();

        //计算上下位置
        switch (mBadgeGravityV){
            case VerticalGravity.TOP:
                mBadgeRectF.top = mBadgeVerticalMargin;
                break;
            case VerticalGravity.CENTER:
                mBadgeRectF.top = (mBadgeViewTarget.getHeight() - badgeHeight) / 2 + mBadgeVerticalMargin;
                break;
            case VerticalGravity.BOTTOM:
                mBadgeRectF.top = mBadgeViewTarget.getHeight() - mBadgeVerticalMargin - badgeHeight;
                break;
        }

        // 计算徽章背景左右的值
        switch (mBadgeGravityH) {
            case HorizontalGravity.LEFT:
                mBadgeRectF.left = mBadgeHorizontalMargin;
                break;
            case HorizontalGravity.CENTER:
                mBadgeRectF.left = (mBadgeViewTarget.getWidth() - badgeWidth) / 2 + mBadgeHorizontalMargin;
                break;
            case HorizontalGravity.CENTER_START:
                mBadgeRectF.left = mBadgeViewTarget.getWidth()  / 2 + mBadgeHorizontalMargin;
                break;
            case HorizontalGravity.RIGHT:
                mBadgeRectF.left = mBadgeViewTarget.getWidth() - mBadgeHorizontalMargin - badgeWidth;
                break;
        }
        canvas.drawBitmap(mBitmap, mBadgeRectF.left, mBadgeRectF.top, mBadgePaint);
        mBadgeRectF.right = mBadgeRectF.left + badgeWidth;
        mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
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
        // 当mBadgeText的长度为1或0时，计算出来的高度会比宽度大，此时设置宽度等于高度
        if (badgeText.length() == 1 || badgeText.length() == 0) {
            badgeWidth = badgeHeight;
        } else {
            badgeWidth = mBadgeNumberRect.width() + mBadgePadding * 2;
        }

        //计算上下位置
        switch (mBadgeGravityV){
            case VerticalGravity.TOP:
                mBadgeRectF.top = mBadgeVerticalMargin;
                break;
            case VerticalGravity.CENTER:
                mBadgeRectF.top = (mBadgeViewTarget.getHeight() - badgeHeight) / 2 + mBadgeVerticalMargin;
                break;
            case VerticalGravity.BOTTOM:
                mBadgeRectF.top = mBadgeRectF.bottom - badgeHeight - mBadgeVerticalMargin;
                break;
        }
        mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;


        // 计算徽章背景左右的值
        switch (mBadgeGravityH) {
            case HorizontalGravity.LEFT:
                mBadgeRectF.left = mBadgeHorizontalMargin;
                break;
            case HorizontalGravity.CENTER:
                mBadgeRectF.left = (mBadgeViewTarget.getWidth() - badgeWidth) / 2 + mBadgeHorizontalMargin;
                break;
            case HorizontalGravity.CENTER_START:
                mBadgeRectF.left = mBadgeViewTarget.getWidth()  / 2 + mBadgeHorizontalMargin;
                break;
            case HorizontalGravity.RIGHT:
                mBadgeRectF.left = mBadgeViewTarget.getWidth() - mBadgeHorizontalMargin - badgeWidth;
                break;
        }
        mBadgeRectF.right = mBadgeRectF.left + badgeWidth;

        if (mBadgeBorderWidth > 0) {
            // 设置徽章边框景色
            mBadgePaint.setColor(mBadgeBorderColor);
            // 绘制徽章边框背景
            canvas.drawRoundRect(mBadgeRectF, badgeHeight / 2, badgeHeight / 2, mBadgePaint);

            // 设置徽章背景色
            mBadgePaint.setColor(mBadgeBgColor);
            // 绘制徽章背景
            canvas.drawRoundRect(new RectF(mBadgeRectF.left + mBadgeBorderWidth, mBadgeRectF.top + mBadgeBorderWidth, mBadgeRectF.right - mBadgeBorderWidth, mBadgeRectF.bottom - mBadgeBorderWidth), (badgeHeight - 2 * mBadgeBorderWidth) / 2, (badgeHeight - 2 * mBadgeBorderWidth) / 2, mBadgePaint);
        } else {
            // 设置徽章背景色
            mBadgePaint.setColor(mBadgeBgColor);
            // 绘制徽章背景
            canvas.drawRoundRect(mBadgeRectF, badgeHeight / 2, badgeHeight / 2, mBadgePaint);
        }


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
        mIsShowDrawable = false;
        mBadgeText = badgeText;
        mIsShowBadge = true;
        mBadgeViewTarget.postInvalidate();
    }

    public void hiddenBadge() {
        mIsShowBadge = false;
        mBadgeViewTarget.postInvalidate();
    }

    public boolean isBadgeShow() {
        return mIsShowBadge;
    }

    public void showDrawableBadge(Bitmap bitmap) {
        mBitmap = bitmap;
        mIsShowDrawable = true;
        mIsShowBadge = true;
        mBadgeViewTarget.postInvalidate();
    }

    public boolean isShowDrawable() {
        return mIsShowDrawable;
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

    public void setOnDragDismissListener(OnBadgeDragDismissListener delegage) {
        mDragDismissListenner = delegage;
    }

    public View getRootView() {
        return mBadgeViewTarget.getRootView();
    }

    public boolean isResumeTravel() {
        return mIsResumeTravel;
    }


}