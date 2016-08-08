:heartpulse:BGABadgeView-Android:heartpulse:
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BGABadgeView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2106)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-badgeview/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-badgeview)

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

```groovy
dependencies {
    compile 'cn.bingoogolapple:bga-badgeview:latestVersion@aar'
}
```

### 目前支持的徽章类

类名 | 应用场景
:----------- | :-----------
BGABadgeRadioButton | 微博首页底部导航
BGABadgeImageView | 微博列表用户头像
BGABadgeTextView | 其实这个可以用BGABadgeCheckedTextView代替
BGABadgeLinearLayout | 列表item右侧消息条数
BGABadgeRelativeLayout | 列表item右侧消息条数
BGABadgeFrameLayout | 列表item右侧消息条数

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
badge_dragable         | 是否开启拖拽删除徽章        | false
badge_isResumeTravel         | 拖拽徽章超出轨迹范围后，再次放回到轨迹范围时，是否恢复轨迹        | false
badge_borderWidth         | 徽章描边宽度        | 0dp
badge_borderColor         | 徽章描边颜色        | Color.WHITE
badge_dragExtra         | 触发开始拖拽徽章事件的扩展触摸距离        | 4dp

### 扩展自己的BadgeView

继承特定的控件，实现[BGABadgeable](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/library/src/main/java/cn/bingoogolapple/badgeview/BGABadgeable.java)接口中相应的方法，并调用[BGABadgeViewHelper](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/library/src/main/java/cn/bingoogolapple/badgeview/BGABadgeViewHelper.java)中相应的方法，自定义控件实现方式请参考[BGABadgeRadioButton](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/library/src/main/java/cn/bingoogolapple/badgeview/BGABadgeRadioButton.java)，自定义组合控件实现方式请参考[BGABadgeLinearLayout](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/library/src/main/java/cn/bingoogolapple/badgeview/BGABadgeLinearLayout.java)

## 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

## 打赏支持

如果觉得 BGA 系列开源库对您有用，请随意打赏。

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
