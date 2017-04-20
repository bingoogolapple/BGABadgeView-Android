package easy.badge.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import easy.badge.Badge;
import easy.badge.HorizontalGravity;
import easy.badge.VerticalGravity;
import easy.badge.impl.BadgeConfig;
import easy.badge.impl.BadgeControl;
import easy.badge.impl.BadgeViewHelper;
import easy.badge.impl.OnBadgeDragDismissListener;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/3/14 上午2:46
 * 描述:
 */

/**
 * 重构
 *
 * update: Lucio
 * date: 2017-04-18
 */
public class BadgeView extends View implements BadgeControl, easy.badge.impl.BadgeView {

    private BadgeViewHelper mBadgeViewHeler;
    private Object mPrimaryKey;

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBadgeViewHeler = Badge.createViewHelper(this, context, attrs, HorizontalGravity.RIGHT, VerticalGravity.CENTER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mBadgeViewHeler.onTouchEvent(event);
    }

    @Override
    public boolean callSuperOnTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void setPrimaryKey(Object primaryKey) {
        mPrimaryKey = primaryKey;
    }

    @Override
    public Object getPrimaryKey() {
        return mPrimaryKey;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBadgeViewHeler.drawBadge(canvas);
    }

    @Override
    public void showCirclePointBadge() {
        mBadgeViewHeler.showCirclePointBadge();
    }

    @Override
    public void showTextBadge(String badgeText) {
        mBadgeViewHeler.showTextBadge(badgeText);
    }

    @Override
    public void hiddenBadge() {
        mBadgeViewHeler.hiddenBadge();
    }

    @Override
    public boolean isBadgeShow() {
        return mBadgeViewHeler.isBadgeShow();
    }

    @Override
    public void setOnDragDismissListener(OnBadgeDragDismissListener listener) {
        mBadgeViewHeler.setOnDragDismissListener(listener);
    }

    @Override
    public void showDrawableBadge(Bitmap bitmap) {
        mBadgeViewHeler.showDrawableBadge(bitmap);
    }

    @Override
    public BadgeConfig getBadgeConfigHelper() {
        return mBadgeViewHeler;
    }
}