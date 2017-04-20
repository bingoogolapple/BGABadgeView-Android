package easy.badge.impl;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by Lucio on 17/4/16.
 * BadgeView 接口
 */

public interface BadgeView {

    // ------View包含的方法
    int getId();

    Context getContext();

    View getRootView();

    int getWidth();

    int getHeight();

    void postInvalidate();

    ViewParent getParent();

    boolean getGlobalVisibleRect(Rect r);
// ------View包含的方法

    /**
     * 调用父类的onTouchEvent方法
     *
     * @param event
     * @return
     */
    boolean callSuperOnTouchEvent(MotionEvent event);

    void setPrimaryKey(Object primaryKey);

    Object getPrimaryKey();
}
