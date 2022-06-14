package com.test.persontest.widget;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.test.persontest.R;


/**
 * Created by leihao
 * supercoach
 */

public class LoadingDialog extends Dialog {

    TextView mTitle;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialogStyle);
        View v = LayoutInflater.from(context).inflate(R.layout.widget_dialog_loading, null);
        mTitle = v.findViewById(R.id.dialog_loading_title);
        setContentView(v);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            mTitle.setVisibility(View.GONE);
            return;
        }
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(title);
    }
}
