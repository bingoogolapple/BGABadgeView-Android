package cn.bingoogolapple.badgeview.demo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;
import cn.bingoogolapple.badgeview.BGABadgeTextView;
import cn.bingoogolapple.badgeview.BGABadgeView;
import cn.bingoogolapple.badgeview.BGABadgeable;
import cn.bingoogolapple.badgeview.BGADragDismissDelegate;
import cn.bingoogolapple.badgeview.demo.R;
import cn.bingoogolapple.badgeview.demo.adapter.MessageAdapter;
import cn.bingoogolapple.badgeview.demo.model.MessageModel;
import cn.bingoogolapple.badgeview.demo.util.ToastUtil;
import cn.bingoogolapple.badgeview.demo.widget.BadgeFloatingActionButton;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class MainActivity extends AppCompatActivity {
    private BGABadgeView mTestBv;

    private BGABadgeTextView mTestBtv;

    private BGABadgeImageView mNormalBiv;
    private BGABadgeImageView mRoundedBiv;
    private BGABadgeImageView mCircleBiv;

    private BGABadgeLinearLayout mTestBll;
    private BGABadgeRelativeLayout mTestBrl;
    private BGABadgeFrameLayout mTestBfl;

    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdapter;


    private RadioGroup mTabRg;
    private BGABadgeRadioButton mHomeBrb;
    private BGABadgeRadioButton mMessageBrb;
    private BGABadgeRadioButton mDiscoverBrb;
    private BGABadgeRadioButton mMeBrb;

    private BadgeFloatingActionButton mChatBfab;

    private boolean mHasRequestPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtil.init(this);

        initView();
        testBadgeView();
        testRadioButton();
        testRecyclerView();
    }

    private void initView() {
        mTestBv = findViewById(R.id.bv_main_test);

        mTestBtv = findViewById(R.id.btv_main_test);

        mNormalBiv = findViewById(R.id.biv_main_normal);
        mRoundedBiv = findViewById(R.id.biv_main_rounded);
        mCircleBiv = findViewById(R.id.biv_main_circle);

        mTestBll = findViewById(R.id.bll_main_test);
        mTestBrl = findViewById(R.id.brl_main_test);
        mTestBfl = findViewById(R.id.bfl_main_test);

        mRecyclerView = findViewById(R.id.recyclerview);

        mTabRg = findViewById(R.id.rg_main_tab);
        mHomeBrb = findViewById(R.id.brb_main_home);
        mMessageBrb = findViewById(R.id.brb_main_message);
        mDiscoverBrb = findViewById(R.id.brb_main_discover);
        mMeBrb = findViewById(R.id.brb_main_me);

        mChatBfab = findViewById(R.id.bfab_main_chat);
    }

    private void testBadgeView() {
        mTestBv.showTextBadge("9");
        mTestBv.getBadgeViewHelper().setBadgeTextSizeSp(15);
        mTestBv.getBadgeViewHelper().setBadgePaddingDp(8);
        mTestBv.getBadgeViewHelper().setBadgeTextColorInt(Color.parseColor("#FF0000"));
        mTestBv.getBadgeViewHelper().setBadgeBgColorInt(Color.parseColor("#00FF00"));
        mTestBv.getBadgeViewHelper().setDragable(true);
        mTestBv.getBadgeViewHelper().setBadgePaddingDp(7);
        mTestBv.getBadgeViewHelper().setBadgeBorderWidthDp(2);
        mTestBv.getBadgeViewHelper().setBadgeBorderColorInt(Color.parseColor("#0000FF"));

        mTestBtv.showCirclePointBadge();

        mNormalBiv.showCirclePointBadge();

        Bitmap avatarBadgeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_vip);

        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(),
                BitmapFactory.decodeResource(getResources(), R.mipmap.avator));
        roundedDrawable.getPaint().setAntiAlias(true);
        roundedDrawable.setCornerRadius(30);
        mRoundedBiv.setImageDrawable(roundedDrawable);
        mRoundedBiv.showDrawableBadge(avatarBadgeBitmap);

        Bitmap avatarBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avator);
        RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(getResources(), avatarBitmap);
        circleDrawable.getPaint().setAntiAlias(true);
        circleDrawable.setCornerRadius(Math.max(avatarBitmap.getWidth(), avatarBitmap.getHeight()) / 2.0f);
        mCircleBiv.setImageDrawable(circleDrawable);
        mCircleBiv.showDrawableBadge(avatarBadgeBitmap);

        mTestBll.showDrawableBadge(avatarBadgeBitmap);
        mTestBrl.showTextBadge("LoveAndroid");
        mTestBfl.showTextBadge("8");

        mChatBfab.showTextBadge("8");
        mChatBfab.setDragDismissDelegage(new BGADragDismissDelegate() {
            @Override
            public void onDismiss(BGABadgeable badgeable) {
                ToastUtil.show("清空聊天消息");
            }
        });

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
    }

    private void testRadioButton() {
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
                ToastUtil.show("消息单选按钮徽章拖动消失");
            }
        });

        mTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.brb_main_home:
                        ToastUtil.show("首页");
                        break;
                    case R.id.brb_main_message:
                        ToastUtil.show("消息");
                        break;
                    case R.id.brb_main_discover:
                        ToastUtil.show("发现");
                        break;
                    case R.id.brb_main_me:
                        ToastUtil.show("我");
                        break;
                }
            }
        });
    }

    private void testRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMessageAdapter = new MessageAdapter(mRecyclerView);
        mMessageAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup viewGroup, View view, int position) {
                ToastUtil.show("点击了条目 " + mMessageAdapter.getItem(position).title);
            }
        });
        mRecyclerView.setAdapter(mMessageAdapter);
        mMessageAdapter.setData(MessageModel.getTestData());
    }
}