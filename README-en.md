:heartpulse:BGABadgeView-Android:heartpulse:
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BGABadgeView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2106)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://jitpack.io/v/bingoogolapple/BGABadgeView-Android.svg)](https://jitpack.io/#bingoogolapple/BGABadgeView-Android)

### [中文文档](https://github.com/bingoogolapple/BGABadgeView-Android/blob/master/README.md)

### [react-native-bga-badge-view](https://github.com/bingoogolapple/react-native-bga-badge-view)

**The demo shows:**
* The latest message number of weibo at the bottom tab
* The VIP badge located in lower-right Corner of User’s Photo in the weibo user list
* The new message in the weixin message page
* The subscribe message in the weixin message page
* User’s  round corner photo made by RoundedBitmapDrawable from v4 package
* Delete badge by drag and drop

### The realization of explosion effect refers to [ExplosionField](https://github.com/tyrantgit/ExplosionField) with the modify of only retaining one View，and only refresh the field around of badge

### Screenshots
![badgeview](https://cloud.githubusercontent.com/assets/8949716/17483429/8f5ab3aa-5db8-11e6-808c-6033f5d5c4ec.gif)

### Gradle Dependencies

[![Download](https://jitpack.io/v/bingoogolapple/BGABadgeView-Android.svg)](https://jitpack.io/#bingoogolapple/BGABadgeView-Android)

```groovy
dependencies {
    implementation 'com.github.bingoogolapple.BGABadgeView-Android:api:latestVersion'
    annotationProcessor 'com.github.bingoogolapple.BGABadgeView-Android:compiler:latestVersion'
}
```

### Initialize BGABadge

Add BGABadge annotation to any class, such as newly created class BGABadgeInit

```Java
@BGABadge({
        View.class, // ===> cn.bingoogolapple.badgeview.BGABadgeView，If you do not need, please delete this line
        ImageView.class, // ===> cn.bingoogolapple.badgeview.BGABadgeImageView，If you do not need, please delete this line
        TextView.class, // ===> cn.bingoogolapple.badgeview.BGABadgeFloatingTextView，If you do not need, please delete this line
        RadioButton.class, // ===> cn.bingoogolapple.badgeview.BGABadgeRadioButton，If you do not need, please delete this line
        LinearLayout.class, // ===> cn.bingoogolapple.badgeview.BGABadgeLinearLayout，If you do not need, please delete this line
        RelativeLayout.class, // ===> cn.bingoogolapple.badgeview.BGABadgeRelativeLayout，If you do not need, please delete this line
        FloatingActionButton.class, // ===> cn.bingoogolapple.badgeview.BGABadgeFloatingActionButton，If you do not need, please delete this line
        ...
        ...
        ...
})
public class BGABadgeInit {
}
```


### Supported badge style for now

Class name | usage scenario
:----------- | :-----------
BGABadgeRadioButton | Bottom navigation of weibo home page
BGABadgeImageView | User’s photo of weibo user list
BGABadgeTextView | You can alternatively use  BGABadgeCheckedTextView
BGABadgeLinearLayout | Message number in the right of item in the list
BGABadgeRelativeLayout | Message number in the right of item in the list
BGABadgeFrameLayout | Message number in the right of item in the list

### Interface/api explain

```java
/**
 * show circle badge
 */
void showCirclePointBadge();

/**
 * show text badge
 *
 * @param badgeText
 */
void showTextBadge(String badgeText);

/**
 * hide badge
 */
void hiddenBadge();

/**
 * show image badge
 *
 * @param bitmap
 */
void showDrawableBadge(Bitmap bitmap);

/**
 * set the delegate to delete the badge by drag and drop
 *
 * @param delegate
 */
void setDragDismissDelegage(BGADragDismissDelegate delegate);

/**
 * Whether show the badge or not
 *
 * @return
 */
boolean isShowBadge();

/**
 * Can it be dragged
 *
 * @return
 */
boolean isDraggable();

/**
 * Whether being dragged
 *
 * @return
 */
boolean isDragging();
```

### The customed attribution

The customed attribution | Explain | Default value
:----------- | :----------- | :-----------
badge_bgColor         | Badge background        | Color.RED
badge_textColor         | Text color of badge        | Color.WHITE
badge_textSize         | Text font size of badge        | 10sp
badge_verticalMargin         | The distance in pixels from the top edge of badge’s background to the top edge of the host or from the bottom edge of badge’s bottom to the host’s bottom        | 4dp
badge_horizontalMargin         | The distance in pixels from the edge of badge’s background to the left or right edge of the host        | 4dp
badge_padding         | Badge’s padding        | 4dp
badge_gravity         | Badge’s gravity, it’s layout direction        | For BGABadgeImageView and BGABadgeRadioButton, upper right.For others, the default is the right side of the vertical center
badge_draggable         | The badge can be drag or not        | false
badge_isResumeTravel         | When the badge is dragged out of path, if it can resume the path in case of put back        | false
badge_borderWidth         | Border width of badge        | 0dp
badge_borderColor         | Border color of badge        | Color.WHITE
badge_dragExtra         | Extra distance of trigger drag event        | 4dp

### About me

| Personal homepage | email |  QQ for the BGA funs |
| ------------- | ------------ | ------------ |
| <a href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a> | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

## Donate

It takes much time and energy to maintain and improve this project. It BGA helps you, you may want to buy me a coffee :).

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
