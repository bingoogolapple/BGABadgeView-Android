package cn.bingoogolapple.badgeview.demo.adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import cn.bingoogolapple.badgeview.BGABadgeable;
import cn.bingoogolapple.badgeview.BGADragDismissDelegate;
import cn.bingoogolapple.badgeview.demo.R;
import cn.bingoogolapple.badgeview.demo.model.MessageModel;
import cn.bingoogolapple.badgeview.demo.util.ToastUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/12 上午11:39
 * 描述:
 */
public class MessageAdapter extends BGARecyclerViewAdapter<MessageModel> {
    public MessageAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_message);
    }

    @Override
    protected void fillData(BGAViewHolderHelper holderHelper, final int position, final MessageModel message) {
        holderHelper.setText(R.id.tv_item_message_title, message.title);
        holderHelper.setText(R.id.tv_item_message_detail, message.detail);

        BGABadgeLinearLayout rootView = (BGABadgeLinearLayout) holderHelper.getConvertView();
        if (message.newMsgCount > 0) {
            rootView.showTextBadge("" + message.newMsgCount);
            rootView.setDragDismissDelegate(new BGADragDismissDelegate() {
                @Override
                public void onDismiss(BGABadgeable badgeable) {
                    message.newMsgCount = 0;
                    ToastUtil.show(message.title + "的徽章消失");
                }
            });
        } else {
            rootView.hiddenBadge();
        }
    }
}