package easy.badge;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by Lucio on 17/4/17.
 */
@IntDef({VerticalGravity.TOP, VerticalGravity.CENTER, VerticalGravity.BOTTOM})
@Retention(RetentionPolicy.SOURCE)
public @interface VerticalGravity {
    int TOP = 0;
    int CENTER = 1;
    int BOTTOM = 2;
}
