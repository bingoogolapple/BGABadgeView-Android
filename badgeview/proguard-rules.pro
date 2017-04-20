# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Lucio/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:


#########################START############################
#来源：proguard-android-optimize.txt
#-优化-
#（Basic）优化算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
#（Basic）迭代优化，n表示proguard对代码进行迭代优化的次数，Android一般为5
-optimizationpasses 5
#（Basic）提高优化步骤
-allowaccessmodification
#如果不想开启优化，则注释上面三句，开启下面一句
#-dontoptimize
#-优化-

#（Basic）#不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度
-dontpreverify
#（Basic 包名不混合大小写
-dontusemixedcaseclassnames
#（Basic）不忽略非公共的库类
-dontskipnonpubliclibraryclasses
#（Basic）输出混淆日志
-verbose

#（Basic）
-keep public class com.google.vending.licensing.ILicensingService
#（Basic）
-keep public class com.android.vending.licensing.ILicensingService

# 混淆注意事项见：http://www.jianshu.com/p/1b76e4c10495
#（Basic）混淆注意事项第一条，保留清单文件相关的配置比如四大组件；保留参数只有一个View的方法是为了预防xml中定义OnClick属性
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
#（Basic）混淆注意事项第二条，保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#-webview-
# 混淆注意事项第四条，保持WebView中JavaScript调用的方法
# 下面新增的-keepattributes *Annotation*保留注解，还应该是包含了这句功能
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# 建议：使用了WebView和JS的需要添加下面语句
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
#-webview-

# （Basic）混淆注意事项第五条 自定义View
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
# （Basic）混淆注意事项第七条，保持 Parcelable 不被混淆
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}
#（Basic） 混淆注意事项第八条，保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#（Basic）
-keepclassmembers class **.R$* {
    public static <fields>;
}

#（Basic）排除support的警告
-dontwarn android.support.**

#（Basic）保留注解
-keepattributes *Annotation*
# 建议配置：保护泛型
-keepattributes Signature
# 建议配置：保留源文件以及行号 方便查看具体的崩溃信息
-keepattributes SourceFile,LineNumberTable

#以上为基本配置
########################END#############################


#感觉语句没什么用，就是为了保留keep注解，但是上面已经添加了保留注解属性，应该包括以下内容
## Understand the @Keep support annotation.
## （Basic）不混淆指定的类及其类成员
#-keep class android.support.annotation.Keep
## （Basic）不混淆使用注解的类及其类成员
#-keep @android.support.annotation.Keep class * {*;}
## （Basic）不混淆所有类及其类成员中的使用注解的方法
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <methods>;
#}
## （Basic）不混淆所有类及其类成员中的使用注解的字段
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <fields>;
#}
## 不混淆所有类及其类成员中的使用注解的初始化方法
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <init>(...);
#}


##############以下为自定义配置规则#######################

##保持透明状态栏类和所有public static方法

-keep public class easy.badge.**{
    public *;
    protected *;
}
