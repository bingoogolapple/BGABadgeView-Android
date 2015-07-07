package cn.bingoogolapple.badgeview.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import cn.bingoogolapple.badgeview.BGABadgeCheckedTextView;
import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import cn.bingoogolapple.badgeview.BGABadgeTextView;
import cn.bingoogolapple.badgeview.BGACircleImageDrawable;
import cn.bingoogolapple.badgeview.BGARoundImageDrawable;

public class MainActivity extends AppCompatActivity {
    private BGABadgeTextView mTestBtv;
    private BGABadgeCheckedTextView mTestBctv;

    private BGABadgeImageView mTest1Biv;
    private BGABadgeImageView mTest2Biv;
    private BGABadgeImageView mTest3Biv;

    private LinearLayout mTestLl;

    private BGABadgeRadioButton mHomeBrb;
    private BGABadgeRadioButton mMessageBrb;
    private BGABadgeRadioButton mDiscoverBrb;
    private BGABadgeRadioButton mMeBrb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        testBadgeView();
    }

    private void initView() {
        mTestBtv = getViewById(R.id.btv_home_test);
        mTestBctv = getViewById(R.id.bctv_home_test);
        mTest1Biv = getViewById(R.id.biv_home_test1);
        mTest2Biv = getViewById(R.id.biv_home_test2);
        mTest3Biv = getViewById(R.id.biv_home_test3);

        mTestLl = getViewById(R.id.ll_main_test);


        mHomeBrb = getViewById(R.id.brb_main_home);
        mMessageBrb = getViewById(R.id.brb_main_message);
        mDiscoverBrb = getViewById(R.id.brb_main_discover);
        mMeBrb = getViewById(R.id.brb_main_me);
    }

    private void testBadgeView() {
        mTestBtv.showCriclePointBadge();
        mTestBctv.showTextBadge("BGA");
        mTest1Biv.showCriclePointBadge();

        Bitmap avatorBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avator);
        Bitmap avatorBadgeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_vip);
        mTest2Biv.setImageDrawable(new BGARoundImageDrawable(avatorBitmap, 30, 30));
        mTest2Biv.showDrawableBadge(avatorBadgeBitmap);
        mTest3Biv.setImageDrawable(new BGACircleImageDrawable(avatorBitmap));
        mTest3Biv.showDrawableBadge(avatorBadgeBitmap);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTest2Biv.hiddenBadge();
            }
        }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTest2Biv.showCriclePointBadge();
            }
        }, 6000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTest2Biv.showDrawableBadge(BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_vip));
            }
        },9000);

        mHomeBrb.showTextBadge("10");
        mMessageBrb.showTextBadge("1");
        mDiscoverBrb.showTextBadge("...");
        mMeBrb.showTextBadge("1");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHomeBrb.showTextBadge("1");
                mMeBrb.showCriclePointBadge();
            }
        },5000);
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

}