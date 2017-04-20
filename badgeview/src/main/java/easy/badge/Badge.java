package easy.badge;

import android.content.Context;
import android.util.AttributeSet;

import easy.badge.impl.BadgeView;
import easy.badge.impl.BadgeViewHelper;

/**
 * Created by Lucio on 17/4/17.
 */
public class Badge {

    public static BadgeViewHelper createViewHelper(BadgeView badgeable, Context context, AttributeSet attrs,  @HorizontalGravity int gravityH,@VerticalGravity int gravityV){
        return new BadgeHelperImpl(badgeable,context,attrs,gravityH,gravityV);
    }

    public static BadgeDragView createDragView(Context context, BadgeHelperImpl badgeViewHelper){
        return new BadgeDragView(context,badgeViewHelper);
    }
}
