package easy.badge.impl;

import easy.badge.HorizontalGravity;
import easy.badge.VerticalGravity;

/**
 * Created by Lucio on 17/4/16.
 * Badge的配置接口
 */

public interface BadgeConfig {

    void setBadgeBgColorInt(int badgeBgColor);

    void setBadgeTextColorInt(int badgeTextColor);

    void setBadgeTextSizeSp(int badgetextSize);

    void setBadgeVerticalMarginDp(int badgeVerticalMargin);

    void setBadgeHorizontalMarginDp(int badgeHorizontalMargin);

    void setBadgePaddingDp(int badgePadding);

    void setBadgeHorizontalGravity(@HorizontalGravity int badgeGravity);

    void setBadgeVerticalGravity(@VerticalGravity int badgeGravity);

    void setDragable(boolean dragable);

    void setIsResumeTravel(boolean isResumeTravel);

    void setBadgeBorderWidthDp(int badgeBorderWidthDp);

    void setBadgeBorderColorInt(int badgeBorderColor);
}
