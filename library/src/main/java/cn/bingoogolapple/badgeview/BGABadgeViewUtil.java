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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.TypedValue;

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

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
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
}