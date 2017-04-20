package easy.badge.impl;

import android.graphics.Bitmap;

/**
 * Created by Lucio on 17/4/17.
 * 基本控制
 */
interface BaseControl {

    /**
     * 显示圆点徽章
     */
    void showCirclePointBadge();

    /**
     * 显示文字徽章
     *
     * @param badgeText
     */
    void showTextBadge(String badgeText);

    /**
     * 显示图像徽章
     *
     * @param bitmap
     */
    void showDrawableBadge(Bitmap bitmap);

    /**
     * 隐藏徽章
     */
    void hiddenBadge();

    /**
     * 徽章是否显示
     *
     * @return
     */
    boolean isBadgeShow();

    /**
     * 拖动大于BGABadgeViewHelper.mMoveHiddenThreshold后抬起手指徽章消失的代理
     *
     * @param listener
     */
    void setOnDragDismissListener(OnBadgeDragDismissListener listener);
}
