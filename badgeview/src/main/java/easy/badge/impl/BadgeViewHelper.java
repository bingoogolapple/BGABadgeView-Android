package easy.badge.impl;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Lucio on 17/4/17.
 * Badge帮助接口
 */

public interface BadgeViewHelper extends BaseControl,BadgeConfig {

    void drawBadge(Canvas canvas);

    boolean onTouchEvent(MotionEvent event);

}
