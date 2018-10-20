package com.qmwl.zyjx.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.InvoiceActivity;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.bean.DingDanBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by wy on 2017/11/21.
 * 确定取消dialog
 */
public class InvoiceDialog extends Dialog {
    @BindView(R.id.tv_elect_invoice)
    TextView tvElectInvoice;
    @BindView(R.id.tv_paper_invoice)
    TextView tvPaperInvoice;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private Context context;


    private boolean isPaper = false;
    private DingDanBean item;



    @OnClick({R.id.tv_elect_invoice, R.id.tv_paper_invoice, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_elect_invoice:
                tvElectInvoice.setBackgroundResource(R.drawable.bac_rec_border);
                tvElectInvoice.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                tvPaperInvoice.setBackgroundResource(R.drawable.round_border);
                tvPaperInvoice.setTextColor(context.getResources().getColor(R.color.ccbcbcb));
                isPaper = false;
                break;
            case R.id.tv_paper_invoice:
                tvPaperInvoice.setBackgroundResource(R.drawable.bac_rec_border);
                tvPaperInvoice.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                tvElectInvoice.setBackgroundResource(R.drawable.round_border);
                tvElectInvoice.setTextColor(context.getResources().getColor(R.color.ccbcbcb));
                isPaper = true;
                break;
            case R.id.tv_next:
                Intent intent = new Intent(context, InvoiceActivity.class);
                if (isPaper) {
                    intent.putExtra(Constant.invoice_type,2);
                }else{
                    intent.putExtra(Constant.invoice_type,1);
                }
                intent.putExtra(Constant.order, item);
                context.startActivity(intent);
                dismiss();
                break;
        }
    }




    public interface ClickListenerInterface {
        void next();
    }

    /**
     * @param context
     */
    public InvoiceDialog(Context context, DingDanBean item) {
        super(context, R.style.dialog);
        this.context = context;
        this.item = item;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.invoice_dialog, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }




}
