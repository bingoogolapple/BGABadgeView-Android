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

package easy.badge.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import easy.badge.Badge;
import easy.badge.HorizontalGravity;
import easy.badge.VerticalGravity;
import easy.badge.impl.BadgeConfig;
import easy.badge.impl.BadgeControl;
import easy.badge.impl.BadgeView;
import easy.badge.impl.BadgeViewHelper;
import easy.badge.impl.OnBadgeDragDismissListener;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/7 14:07
 * 描述:
 */
public class BadgeRelativeLayout extends RelativeLayout implements BadgeControl, BadgeView {

    private BadgeViewHelper mBadgeViewHeler;
    private Object mPrimaryKey;

    public BadgeRelativeLayout(Context context) {
        this(context, null);
    }

    public BadgeRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
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