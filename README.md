:heartpulse:BGABadgeView-Android:heartpulse:
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BGABadgeView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2106)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[ ![Download](https://api.bintray.com/packages/bingoogolapple/maven/bga-badgeview-api/images/download.svg) ](https://bintray.com/bingoogolapple/maven/bga-badgeview-api/_latestVersion)

### [English Document](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/README-en.md)

### [react-native-bga-badge-view](https://github.com/bingoogolapple/react-native-bga-badge-view)

**demo中演示了:**
* 微博底部tab显示最新微博条数
* 微博列表用户头像显示显示右下角vip徽章
* 微信消息界面用户新消息
* 微信消息界面订阅号新消息
* 使用v4包中的RoundedBitmapDrawable制作圆角头像
* 拖拽删除徽章

### 爆炸效果参考的 [ExplosionField](https://github.com/tyrantgit/ExplosionField) 改成了只有一个View的情况,只刷新徽章附近的区域

### 效果图
![badgeview](https://cloud.githubusercontent.com/assets/8949716/17483429/8f5ab3aa-5db8-11e6-808c-6033f5d5c4ec.gif)

### [点击下载 Apk](http://fir.im/BGABadgeViewDemo) 或扫描下方二维码安装体验
![Demo](http://7xk9dj.com1.z0.glb.clouddn.com/badge/BGABadgeViewDemoQRCode.png)

### Gradle依赖

[ ![Download](https://api.bintray.com/packages/bingoogolapple/maven/bga-badgeview-api/images/download.svg) ](https://bintray.com/bingoogolapple/maven/bga-badgeview-api/_latestVersion) bga-badgeview-api 后面的「latestVersion」指的是左边这个 Download 徽章后面的「数字」，请自行替换。

```groovy
dependencies {
    implementation 'cn.bingoogolapple:bga-badgeview-api:latestVersion'
    annotationProcessor "cn.bingoogolapple:bga-badgeview-compiler:latestVersion"
}
```

### 初始化徽章控件

1. 在项目任意一个类上面添加 BGABadge 注解，例如新建一个类 BGABadgeInit 专门用于初始化徽章控件
2. 需要哪些类具有徽章功能，就把那些类的 Class 作为 BGABadge 注解的参数「下面的代码块给出了例子，不需要的可以删掉对应的行」
```Java
@BGABadge({
        View.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeView，不想用这个类的话就删了这一行
        ImageView.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeImageView，不想用这个类的话就删了这一行
        TextView.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingTextView，不想用这个类的话就删了这一行
        RadioButton.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeRadioButton，不想用这个类的话就删了这一行
        LinearLayout.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeLinearLayout，不想用这个类的话就删了这一行
        FrameLayout.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeFrameLayout，不想用这个类的话就删了这一行
        RelativeLayout.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeRelativeLayout，不想用这个类的话就删了这一行
        FloatingActionButton.class, // 对应 cn.bingoogolapple.badgeview.BGABadgeFloatingActionButton，不想用这个类的话就删了这一行
        ...
        ...
        ...
})
public class BGABadgeInit {
}
```
3. 再 AS 中执行 Build => Rebuild Project
4. 经过前面三个步骤后就可以通过「cn.bingoogolapple.badgeview.BGABadge原始类名」来使用徽章控件了

### 接口说明

```java
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
 * 隐藏徽章
 */
void hiddenBadge();

/**
 * 显示图像徽章
 *
 * @param bitmap
 */
void showDrawableBadge(Bitmap bitmap);

/**
 * 设置拖动删除徽章的代理
 *
 * @param delegate
 */
void setDragDismissDelegage(BGADragDismissDelegate delegate);

/**
 * 是否显示徽章
 *
 * @return
 */
boolean isShowBadge();

/**
 * 是否可拖动
 *
 * @return
 */
boolean isDraggable();

/**
 * 是否正在拖动
 *
 * @return
 */
boolean isDragging();
```

### 自定义属性说明

属性名 | 说明 | 默认值
:----------- | :----------- | :-----------
badge_bgColor         | 徽章背景色        | Color.RED
badge_textColor         | 徽章文本的颜色        | Color.WHITE
badge_textSize         | 徽章文本字体大小        | 10sp
badge_verticalMargin         | 徽章背景与宿主控件上下边缘间距离        | 4dp
badge_horizontalMargin         | 徽章背景与宿主控件左右边缘间距离        | 4dp
badge_padding         | 徽章文本边缘与徽章背景边缘间的距离        | 4dp
badge_gravity         | 徽章在宿主控件中的位置        | BGABadgeImageView和BGABadgeRadioButton是右上方，其他控件是右边垂直居中
badge_draggable         | 是否开启拖拽删除徽章        | false
badge_isResumeTravel         | 拖拽徽章超出轨迹范围后，再次放回到轨迹范围时，是否恢复轨迹        | false
badge_borderWidth         | 徽章描边宽度        | 0dp
badge_borderColor         | 徽章描边颜色        | Color.WHITE
badge_dragExtra         | 触发开始拖拽徽章事件的扩展触摸距离        | 4dp

## 关于我

| 个人主页 | 邮箱 | BGA 系列开源库 QQ 群 | GitHub 喵(专注于 GitHub 等一切与程序员有关的内容) |
| ------------- | ------------ | ------------ | ------------ |
| <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://bgashare.bingoogolapple.cn/BGA_CODE_CLUB.png?imageView2/2/w/200) | ![GitHub喵](https://user-images.githubusercontent.com/8949716/99201262-1fd55200-27e5-11eb-8097-c06d2497f477.jpeg) |

## 打赏支持

如果您觉得 BGA 系列开源库帮你节省了大量的开发时间，请扫描下方的二维码随意打赏，要是能打赏个 10.24 :monkey_face:就太:thumbsup:了。您的支持将鼓励我继续创作:octocat:

如果您目前正打算购买通往墙外的梯子，可以使用我的邀请码「YFQ9Q3B」购买 [Lantern](https://github.com/getlantern/forum)，双方都赠送三个月的专业版使用时间:beers:

<p align="center">
  <img src="http://7xk9dj.com1.z0.glb.clouddn.com/bga_pay.png" width="450">
</p>

## License

    Copyright 2015 bingoogolapple

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
