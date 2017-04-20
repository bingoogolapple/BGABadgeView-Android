package easy.badge;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by Lucio on 17/4/17.
 */
@IntDef({HorizontalGravity.LEFT, HorizontalGravity.CENTER, HorizontalGravity.CENTER_START, HorizontalGravity.RIGHT})
@Retention(RetentionPolicy.SOURCE)
public @interface HorizontalGravity {
    int LEFT = 0;
    int CENTER = 1;
    int CENTER_START = 2;
    int RIGHT = 3;
}
