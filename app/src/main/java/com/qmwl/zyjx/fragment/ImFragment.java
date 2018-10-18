package com.qmwl.zyjx.fragment;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.SharedUtils;

/**
 * Created by User on 2017/10/26.
 */

public class ImFragment extends EaseChatFragment {
    @Override
    protected void sendMessage(EMMessage message) {
        if (message != null) {
            message.setAttribute(Contact.HX_USER_NICKNAME, SharedUtils.getString(SharedUtils.USER_NICKNAME, getActivity()));
            message.setAttribute(Contact.HX_USER_IMAGE, SharedUtils.getString(SharedUtils.USER_IMAGE, getActivity()));
            message.setAttribute(Contact.HX_USER_ID, "zyjx" + MyApplication.getIntance().userBean.getUid());
//            message.setAttribute(Contact.HX_USER_ID, "123456");
        }
        super.sendMessage(message);

    }
}

