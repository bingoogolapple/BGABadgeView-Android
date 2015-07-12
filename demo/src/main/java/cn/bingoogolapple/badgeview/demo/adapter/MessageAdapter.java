package cn.bingoogolapple.badgeview.demo.adapter;

import android.content.Context;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import cn.bingoogolapple.badgeview.demo.R;
import cn.bingoogolapple.badgeview.demo.model.MessageModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/12 上午11:39
 * 描述:
 */
public class MessageAdapter extends BGARecyclerViewAdapter<MessageModel> {
    public MessageAdapter(Context context) {
        super(context, R.layout.item_message);
    }

    @Override
    protected void fillData(BGAViewHolderHelper holderHelper, int position, MessageModel message) {
        holderHelper.setText(R.id.tv_item_message_title, message.title);
        holderHelper.setText(R.id.tv_item_message_detail, message.detail);
        BGABadgeLinearLayout rootView = (BGABadgeLinearLayout) holderHelper.getConvertView();
        rootView.showTextBadge("" + message.count);
    }
}
