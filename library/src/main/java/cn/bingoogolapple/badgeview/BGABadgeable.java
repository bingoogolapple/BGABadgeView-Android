package cn.bingoogolapple.badgeview;

import android.graphics.Bitmap;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/6 下午10:42
 * 描述:
 */
public interface BGABadgeable {
    /**
     * 显示圆点徽章
     */
    void showCriclePointBadge();

    /**
     * 显示文字徽章
     *
     * @param badgeText
     */
    void showTextBadge(String badgeText);

    /**
     * 隐藏徽章
     */
    void hiddenBadge();

    /**
     * 显示图像徽章
     *
     * @param bitmap
     */
    void showDrawableBadge(Bitmap bitmap);

    int getWidth();

    int getHeight();

    void postInvalidate();
}