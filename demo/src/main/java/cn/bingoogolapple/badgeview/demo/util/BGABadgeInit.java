package cn.bingoogolapple.badgeview.demo.util;

import cn.bingoogolapple.badgeview.annotation.BGABadge;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:2018/1/14
 * 描述:初始化 BGABadgeView-Android
 * 1.在项目任意一个类上面添加 BGABadge 注解
 * 2.需要哪些类具有徽章功能，就把那些类的全限定名作为 BGABadge 注解的参数
 * 3.再 AS 中执行 Build => Rebuild Project
 * 4.经过前面三个步骤后就可以通过「cn.bingoogolapple.badgeview.BGABadge原始类名」来使用徽章控件了
 */
@BGABadge({
        "android.view.View", // 对应 cn.bingoogolapple.badgeview.BGABadgeView，不想用这个类的话就删了这一行
        "android.widget.ImageView", // 对应 cn.bingoogolapple.badgeview.BGABadgeImageView，不想用这个类的话就删了这一行
        "android.widget.TextView", // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingTextView，不想用这个类的话就删了这一行
        "android.widget.RadioButton", // 对应 cn.bingoogolapple.badgeview.BGABadgeRadioButton，不想用这个类的话就删了这一行
        "android.widget.LinearLayout", // 对应 cn.bingoogolapple.badgeview.BGABadgeLinearLayout，不想用这个类的话就删了这一行
        "android.widget.FrameLayout", // 对应 cn.bingoogolapple.badgeview.BGABadgeFrameLayout，不想用这个类的话就删了这一行
        "android.widget.RelativeLayout", // 对应 cn.bingoogolapple.badgeview.BGABadgeRelativeLayout，不想用这个类的话就删了这一行
        "android.support.design.widget.FloatingActionButton", // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingActionButton，不想用这个类的话就删了这一行
})
public class BGABadgeInit {
}
