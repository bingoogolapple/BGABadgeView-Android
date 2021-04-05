Change Log
==========

Version 1.1.9 *(2021-04-05)*
----------------------------

- 从 JCenter 迁移到 JitPack

Version 1.1.8 *(2018-04-09)*
----------------------------

- fix #49
- badge_dragable 变为 badge_draggable
- BadgeViewHelper 的 setDragable 方法变为 setDraggable

Version 1.1.7 *(2018-01-24)*
----------------------------

- 优化 BGABadge 注解参数为 Class
```Java
/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:2018/1/14
 * 描述:初始化 BGABadgeView-Android
 * 1.在项目任意一个类上面添加 BGABadge 注解
 * 2.需要哪些类具有徽章功能，就把那些类的 class 作为 BGABadge 注解的参数
 * 3.再 AS 中执行 Build => Rebuild Project
 * 4.经过前面三个步骤后就可以通过「cn.bingoogolapple.badgeview.BGABadge原始类名」来使用徽章控件了
 */
@BGABadge({
        View.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeView，不想用这个类的话就删了这一行
        ImageView.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeImageView，不想用这个类的话就删了这一行
        TextView.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingTextView，不想用这个类的话就删了这一行
        RadioButton.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeRadioButton，不想用这个类的话就删了这一行
        LinearLayout.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeLinearLayout，不想用这个类的话就删了这一行
        FrameLayout.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeFrameLayout，不想用这个类的话就删了这一行
        RelativeLayout.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeRelativeLayout，不想用这个类的话就删了这一行
        FloatingActionButton.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingActionButton，不想用这个类的话就删了这一行
})
```

Version 1.1.6 *(2018-01-19)*
----------------------------

- 优化 apt 生成的代码

Version 1.1.5 *(2018-01-16)*
----------------------------

- 移除 Library 中的 BGABadgeXxxxView，改为 annotationProcessor 的形式

[ ![Download](https://api.bintray.com/packages/bingoogolapple/maven/bga-badgeview-api/images/download.svg) ](https://bintray.com/bingoogolapple/maven/bga-badgeview-api/_latestVersion) bga-badgeview-api 后面的「latestVersion」指的是左边这个 Download 徽章后面的「数字」，请自行替换。

```groovy
dependencies {
    implementation 'cn.bingoogolapple:bga-badgeview-api:latestVersion'
    annotationProcessor "cn.bingoogolapple:bga-badgeview-compiler:latestVersion"
}
```

```Java
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
        "android.view.View", // 对应 cn.bingoogolapple.badgeview.BGABadgeView
        "android.widget.ImageView", // 对应 cn.bingoogolapple.badgeview.BGABadgeImageView
        "android.widget.TextView", // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingTextView
        "android.widget.RadioButton", // 对应 cn.bingoogolapple.badgeview.BGABadgeRadioButton
        "android.widget.LinearLayout", // 对应 cn.bingoogolapple.badgeview.BGABadgeLinearLayout
        "android.widget.FrameLayout", // 对应 cn.bingoogolapple.badgeview.BGABadgeFrameLayout
        "android.widget.RelativeLayout", // 对应 cn.bingoogolapple.badgeview.BGABadgeRelativeLayout
        "android.support.design.widget.FloatingActionButton", // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingActionButton
})
public class BGABadgeInit {
}
```

Version 1.1.4 *(2018-01-14)*
----------------------------

- 修改拖拽效果 Window 类型 TYPE_APPLICATION_PANEL

Version 1.1.3 *(2016-07-22)*
----------------------------

- 取消对 nineoldandroids 的依赖，minSdkVersion 从 9 改到 14。如果你想兼容到 API 14 以下，请使用 v1.1.2

Version 1.1.2 *(2016-06-03)*
----------------------------

- 增加「badge_dragExtra」触发开始拖拽徽章事件的扩展触摸距离

Version 1.1.1 *(2016-03-29)*
----------------------------

- 增加徽章描边，badge_borderWidth 徽章描边宽度，badge_borderColor 徽章描边颜色
- 修改 badge_is_resume_travel 为 badge_isResumeTravel 拖拽徽章超出轨迹范围后，再次放回到轨迹范围时，是否恢复轨迹

Version 1.1.0 *(2016-03-21)*
----------------------------

- fix #14

Version 1.0.9 *(2016-03-14)*
----------------------------

- 增加getBadgeViewHelper方法，通过Java代码方式配置自定义属性，以便后期支持React Native

Version 1.0.8 *(2016-03-04)*
----------------------------

- 解决某些手机获取不到状态栏导致绘制高度偏移问题  fix #5
- 增加自定义属性badge_is_resume_travel: 拖拽徽章超出轨迹范围后，再次放回到轨迹范围时，是否恢复轨迹  fix #5
- 取消在NavigationBar区域的拖拽

Version 1.0.7 *(2016-03-03)*
----------------------------

- 添加isShowBadge方法获取徽章的显示状态  fix #6

Version 1.0.6 *(2015-12-06)*
----------------------------

- 添加拖拽黏性效果

Version 1.0.5 *(2015-12-05)*
----------------------------

- 优化拖拽删除时的爆照效果

Version 1.0.4 *(2015-12-05)*
----------------------------

- 添加拖拽删除时的爆照效果

Version 1.0.3 *(2015-12-04)*
----------------------------

- 处理RadioButton不能监听点击事件
- 移除BGABadgeCheckedTextView
- 移除AppCompat里相关控件
- 解决6.0系统拖拽拖拽徽章的权限问题

Version 1.0.2 *(2015-7-16)*
----------------------------

- 解决圆形徽章显示全问题

Version 1.0.1 *(2015-7-12)*
----------------------------

- 增加拖拽删除功能
- 将showCriclePointBadge改为showCirclePointBadge

Version 1.0.0 *(2015-7-7)*
----------------------------

Initial release.