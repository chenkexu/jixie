package com.qmwl.zyjx.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.MainActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.AppManager;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/9/13.
 */

public class SelecterLanguageDialog extends Dialog implements RadioGroup.OnCheckedChangeListener {
    private String title;
    private Activity activity;

    public SelecterLanguageDialog(@NonNull Context context) {
        super(context);
    }

    public SelecterLanguageDialog(@NonNull Context context, @StyleRes int themeResId, Activity activity) {
        super(context, themeResId);
        this.activity = activity;
    }

    protected SelecterLanguageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecter_language);
        initView();
    }

    void initView() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.selecter_language_container);
        if (MyApplication.getIntance().isChina) {
            radioGroup.check(R.id.selecter_language_radiozhongwen);
        } else {
            radioGroup.check(R.id.selecter_language_radioyingwen);
        }
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.selecter_language_radiozhongwen:
                Locale.setDefault(Locale.CHINA);
                Configuration config = group.getContext().getResources().getConfiguration();
                config.locale = Locale.CHINA;
                group.getContext().getResources().updateConfiguration(config, group.getContext().getResources().getDisplayMetrics());
                MyApplication.getIntance().isChina = true;
                SharedUtils.putLanguage("zh", group.getContext());
                //接口变为中文
                Contact.httpaddress = Contact.zh_http;
                Contact.resetHttpAddress();
                startMain();
                break;
            case R.id.selecter_language_radioyingwen:
                Locale.setDefault(Locale.ENGLISH);
                Configuration config1 = group.getContext().getResources().getConfiguration();
                config1.locale = Locale.ENGLISH;
                group.getContext().getResources().updateConfiguration(config1, group.getContext().getResources().getDisplayMetrics());
                MyApplication.getIntance().isChina = false;
                SharedUtils.putLanguage("en", group.getContext());
                //接口变为英文
                Contact.httpaddress = Contact.en_http;
                Contact.resetHttpAddress();
                startMain();
                break;
        }
        dismiss();
    }

    void startMain() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        activity.finish();
        AppManager.getAppManager().finishAllActivity();
        getContext().startActivity(intent);
    }

}
