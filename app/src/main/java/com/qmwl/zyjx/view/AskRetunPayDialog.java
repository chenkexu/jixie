package com.qmwl.zyjx.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.WoDeDingDanActivity;
import com.qmwl.zyjx.adapter.ReturnPayAdapter;
import com.qmwl.zyjx.bean.CancelOrderBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @desc: 申请dialog
 */
public class AskRetunPayDialog extends DialogFragment {

    RecyclerView mRvRetunPay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_askreturn_pay, container, false);
        mRvRetunPay = (RecyclerView)view.findViewById(R.id.rv_reson_returnpay);
        CancelOrderBean mCancelBean=(CancelOrderBean)getArguments().getSerializable("data");
        final ReturnPayAdapter mAdapter=new ReturnPayAdapter(mCancelBean.getNiu_index_response(),getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvRetunPay.setLayoutManager(linearLayoutManager);
        mRvRetunPay.setAdapter(mAdapter);
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkPosition=mAdapter.getCheckedPosition();
                Log.d("huangrui","选中的位置"+checkPosition);
                if (checkPosition!=-1){
                    /*AndroidNetworking.get(Contact.tuikuan + "?orderId=" + item.getOrder_id() + "&goods_id=" + item.getShopList().get(0).getGoods_id())
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (JsonUtils.isSuccess(response)) {
                                            WoDeDingDanActivity.refreshData(context);
                                            new CommomDialog(context, R.style.dialog, context.getString(R.string.tuikuanchenggong), new CommomDialog.OnCloseListener() {
                                                @Override
                                                public void onClick(Dialog dialog, boolean confirm) {
                                                    dialog.dismiss();
                                                }
                                            }).setTitle(context.getString(R.string.tishi)).setHideCancelButton().show();
                                        } else {
                                            showTishiDialog(context, R.string.tuikuanshibai);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        showTishiDialog(context, R.string.tuikuanshibai);
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    showTishiDialog(context, R.string.quxiaodingdanshibai);
                                }
                            });*/
                }
            }
        });
        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置显示位置
        window.setGravity(Gravity.BOTTOM);
        //设置动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.common_pop_dialog);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
  /*  @OnClick({R.id.user_personal_pop_take_photo, R.id.user_personal_pop_pick_photo,R.id.user_personal_pop_cancel})
    protected void onClick(View v){
        switch (v.getId()){
            case R.id.user_personal_pop_take_photo:
                dismiss();
                PictureSelectorUtil.openCameraByChangeAvatar(getActivity());
                break;
            case R.id.user_personal_pop_pick_photo:
                // 隐藏
                dismiss();
                PictureSelectorUtil.openGalleryUseByChangeAvatar(getActivity());
                break;
            case R.id.user_personal_pop_cancel:
                // 隐藏
                dismiss();
                break;
        }
    }*/
}
