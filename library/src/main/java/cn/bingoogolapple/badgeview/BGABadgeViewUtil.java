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
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/12/5 上午12:27
 * 描述:
 */
public class BGABadgeViewUtil {

    private BGABadgeViewUtil() {
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public static int getStatusBarHeight(View view) {
        Rect rectangle = new Rect();
        view.getRootView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public static Bitmap createBitmapSafely(BGADragBadgeView dragBadgeView, Rect rect, int retryCount) {
        try {
            dragBadgeView.setDrawingCacheEnabled(true);
            // 只裁剪徽章区域,不然会很卡
            return Bitmap.createBitmap(dragBadgeView.getDrawingCache(), rect.left < 0 ? 0 : rect.left, rect.top < 0 ? 0 : rect.top, rect.width(), rect.height());
        } catch (OutOfMemoryError e) {
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(dragBadgeView, rect, retryCount - 1);
            }
            return null;
        }
    }

    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
        return distance;
    }

    public static PointF getMiddlePoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }

    public static PointF getPointByPercent(PointF p1, PointF p2, float percent) {
        return new PointF(evaluate(percent, p1.x, p2.x), evaluate(percent, p1.y, p2.y));
    }

    // 从FloatEvaluator中拷贝过来,这样就不用每次都new FloatEvaluator了
    public static Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK) {
        PointF[] points = new PointF[2];

        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);

        return points;
    }
}