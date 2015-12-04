:heartpulse:BGABadgeView-Android:heartpulse:
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BGABadgeView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2106)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-badgeview/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-badgeview)

**demo中演示了:**
* 微博底部tab显示最新微博条数
* 微博列表用户头像显示显示右下角vip徽章
* 微信消息界面用户新消息
* 微信消息界面订阅号新消息
* 使用v4包中的RoundedBitmapDrawable制作圆角头像
* 拖拽删除徽章(目前还没实现水滴拖拽效果和爆炸效果，只是抬起手指时渐变消失)

### 效果图
![Demo](http://7xk9dj.com1.z0.glb.clouddn.com/badge/screenshots/badge1.gif)

### Gradle依赖

```groovy
dependencies {
    compile 'com.nineoldandroids:library:2.4.0'
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

### 扩展自己的BadgeView

继承特定的控件，实现[BGABadgeable](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/library/src/main/java/cn/bingoogolapple/badgeview/BGABadgeable.java)接口中相应的方法，并调用[BGABadgeViewHelper](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/library/src/main/java/cn/bingoogolapple/badgeview/BGABadgeViewHelper.java)中相应的方法，自定义控件实现方式请参考[BGABadgeRadioButton](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/library/src/main/java/cn/bingoogolapple/badgeview/BGABadgeRadioButton.java)，自定义组合控件实现方式请参考[BGABadgeLinearLayout](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/library/src/main/java/cn/bingoogolapple/badgeview/BGABadgeLinearLayout.java)

### 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群 |
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

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
