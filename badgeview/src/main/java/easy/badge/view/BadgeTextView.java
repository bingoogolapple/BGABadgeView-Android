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
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import easy.badge.Badge;
import easy.badge.HorizontalGravity;
import easy.badge.VerticalGravity;
import easy.badge.impl.BadgeConfig;
import easy.badge.impl.BadgeControl;
import easy.badge.impl.BadgeViewHelper;
import easy.badge.impl.BadgeView;
import easy.badge.impl.OnBadgeDragDismissListener;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 */

/**
 * 重构
 * update: Lucio
 * date: 2017-04-18
 */
public class BadgeTextView extends AppCompatTextView implements BadgeControl, BadgeView {

    private BadgeViewHelper mBadgeViewHeler;
    private Object mPrimaryKey;


    public BadgeTextView(Context context) {
        this(context, null);
    }

    public BadgeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void showDrawableBadge(Bitmap bitmap) {
        mBadgeViewHeler.showDrawableBadge(bitmap);
    }

    @Override
    public boolean isBadgeShow() {
        return mBadgeViewHeler.isBadgeShow();
    }

    @Override
    public BadgeConfig getBadgeConfigHelper() {
        return mBadgeViewHeler;
    }

    @Override
    public void setOnDragDismissListener(OnBadgeDragDismissListener delegate) {
        mBadgeViewHeler.setOnDragDismissListener(delegate);
    }

}