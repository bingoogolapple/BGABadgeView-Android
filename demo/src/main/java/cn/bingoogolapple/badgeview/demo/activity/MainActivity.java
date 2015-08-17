package cn.bingoogolapple.badgeview.demo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import cn.bingoogolapple.badgeview.BGABadgeCheckedTextView;
import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;
import cn.bingoogolapple.badgeview.BGABadgeTextView;
import cn.bingoogolapple.badgeview.BGABadgeable;
import cn.bingoogolapple.badgeview.BGADragDismissDelegate;
import cn.bingoogolapple.badgeview.demo.R;
import cn.bingoogolapple.badgeview.demo.adapter.MessageAdapter;
import cn.bingoogolapple.badgeview.demo.model.MessageModel;

public class MainActivity extends AppCompatActivity {
    private BGABadgeTextView mTestBtv;
    private BGABadgeCheckedTextView mTestBctv;

    private BGABadgeImageView mNormalBiv;
    private BGABadgeImageView mRoundedBiv;
    private BGABadgeImageView mCircleBiv;

    private BGABadgeLinearLayout mTestBll;
    private BGABadgeRelativeLayout mTestBrl;
    private BGABadgeFrameLayout mTestBfl;

    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdapter;

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
        testRecyclerView();
    }

    private void initView() {
        mTestBtv = getViewById(R.id.btv_home_test);
        mTestBctv = getViewById(R.id.bctv_home_test);

        mNormalBiv = getViewById(R.id.biv_home_normal);
        mRoundedBiv = getViewById(R.id.biv_home_rounded);
        mCircleBiv = getViewById(R.id.biv_home_circle);

        mTestBll = getViewById(R.id.bll_main_test);
        mTestBrl = getViewById(R.id.brl_main_test);
        mTestBfl = getViewById(R.id.bfl_main_test);

        mRecyclerView = getViewById(R.id.recyclerview);

        mHomeBrb = getViewById(R.id.brb_main_home);
        mMessageBrb = getViewById(R.id.brb_main_message);
        mDiscoverBrb = getViewById(R.id.brb_main_discover);
        mMeBrb = getViewById(R.id.brb_main_me);
    }

    private void testBadgeView() {
        mTestBtv.showCirclePointBadge();
        mTestBctv.showTextBadge("BGA");

        mNormalBiv.showCirclePointBadge();

        Bitmap avatorBadgeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_vip);

        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.avator));
        roundedDrawable.getPaint().setAntiAlias(true);
        roundedDrawable.setCornerRadius(30);
        mRoundedBiv.setImageDrawable(roundedDrawable);
        mRoundedBiv.showDrawableBadge(avatorBadgeBitmap);

        Bitmap avatorBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avator);
        RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(getResources(), avatorBitmap);
        circleDrawable.getPaint().setAntiAlias(true);
        circleDrawable.setCornerRadius(Math.max(avatorBitmap.getWidth(), avatorBitmap.getHeight()) / 2.0f);
        mCircleBiv.setImageDrawable(circleDrawable);
        mCircleBiv.showDrawableBadge(avatorBadgeBitmap);

        mTestBll.showDrawableBadge(avatorBadgeBitmap);
        mTestBrl.showTextBadge("LoveAndroid");
        mTestBfl.showTextBadge("王浩");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRoundedBiv.hiddenBadge();
            }
        }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRoundedBiv.showCirclePointBadge();
            }
        }, 6000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRoundedBiv.showDrawableBadge(BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_vip));
            }
        }, 9000);

        mHomeBrb.showTextBadge("10");
        mMessageBrb.showTextBadge("1");
        mDiscoverBrb.showTextBadge("...");
        mMeBrb.showDrawableBadge(BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_vip));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHomeBrb.showTextBadge("1");
            }
        }, 5000);



        mHomeBrb.setDragDismissDelegage(new BGADragDismissDelegate() {
            @Override
            public void onDismiss(BGABadgeable badgeable) {
                Toast.makeText(MainActivity.this, "消息单选按钮徽章拖动消失", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMessageAdapter = new MessageAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mMessageAdapter);
        mMessageAdapter.setDatas(MessageModel.getTestDatas());
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