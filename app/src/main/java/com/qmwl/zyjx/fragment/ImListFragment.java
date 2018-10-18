package com.qmwl.zyjx.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.ImActivity;

/**
 * Created by User on 2017/10/25.
 */

public class ImListFragment extends EaseConversationListFragment implements EaseConversationListFragment.EaseConversationListItemClickListener {
    public  static final String CONVER_SATION_LIST_CATION = "com.gh.lianxiren_shuaxin";
    ConversationListBroastReciver reciver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reciver = new ConversationListBroastReciver();
        IntentFilter intentFilter = new IntentFilter(CONVER_SATION_LIST_CATION);
        getActivity().registerReceiver(reciver,intentFilter);
        this.setConversationListItemClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reciver!=null && getActivity()!=null){
            getActivity().unregisterReceiver(reciver);
        }
    }

    @Override
    public void onListItemClicked(EMConversation conversation) {
        Intent intent = new Intent(getActivity(),ImActivity.class);
        intent.putExtra(ImActivity.IM_ID,conversation.conversationId());
        startActivity(intent);
    }

    class ConversationListBroastReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
                refresh();
        }
    }
}
