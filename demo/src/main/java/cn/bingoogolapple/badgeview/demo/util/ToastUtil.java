package cn.bingoogolapple.badgeview.demo.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.badgeview.BGABadgeViewUtil;
import cn.bingoogolapple.badgeview.demo.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/12/5 上午11:50
 * 描述:
 */
public class ToastUtil {
    private static Toast sToast;
    private static TextView sMsgTv;

    private ToastUtil() {
    }

    public static void init(Context context) {
        sToast = new Toast(context);
        sToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, BGABadgeViewUtil.dp2px(context, 70));
        sMsgTv = (TextView) LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        sToast.setView(sMsgTv);
        sToast.setDuration(Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text) {
        sMsgTv.setText(text);
        sToast.show();
    }
}