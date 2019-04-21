package com.adups.clock.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adups.clock.R;

/**
 * Created by ccl on 2019/4/17 10:50
 */
public class LoadingDialog extends Dialog {

    private OnKeyDownListener mOnKeyDownListener;

    public LoadingDialog(Context context) {
        super(context, R.style.BottomDialogTransparentStyle);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private final Context context;
        String text;
        private OnKeyDownListener mOnKeyDownListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String text) {
            this.text = text;
            return this;
        }

        public Builder setMessage(int textId) {
            if (textId > 0) {
                text = context.getResources().getString(textId);
            }
            return this;
        }

        public Builder setOnKeyDownListener(OnKeyDownListener onKeyDownListener) {
            mOnKeyDownListener = onKeyDownListener;
            return this;
        }

        public LoadingDialog create() {
            LoadingDialog mLoadingDialog = new LoadingDialog(context);
            View inflate = LayoutInflater.from(context).inflate(R.layout.layout_common_mask, null);
            mLoadingDialog.setContentView(inflate);
            mLoadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.getWindow().setGravity(Gravity.CENTER);
            TextView tvLoading = mLoadingDialog.findViewById(R.id.tv);
            if (tvLoading != null) {
                if (!TextUtils.isEmpty(text)) {
                    tvLoading.setText(text);
                    tvLoading.setVisibility(View.VISIBLE);
                } else {
                    tvLoading.setVisibility(View.GONE);
                }
            }
            mLoadingDialog.setOnKeyDownListener(mOnKeyDownListener);
            return mLoadingDialog;
        }
    }

    public void setMessage(int messageId) {
        setMessage(getContext().getResources().getString(messageId));
    }

    public void setMessage(String message) {
        TextView tvLoading = findViewById(R.id.tv);
        if (!TextUtils.isEmpty(message)) {
            tvLoading.setText(message);
            tvLoading.setVisibility(View.VISIBLE);
        } else {
            tvLoading.setVisibility(View.GONE);
        }
    }

    public void setOnKeyDownListener(OnKeyDownListener onKeyDownListener) {
        mOnKeyDownListener = onKeyDownListener;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (mOnKeyDownListener != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                mOnKeyDownListener.onLoadingDialogKeyDown(keyCode, event);
                dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface OnKeyDownListener {
        void onLoadingDialogKeyDown(int keyCode, KeyEvent event);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mOnKeyDownListener = null;
    }

    /*
     * 显示loading
     * */
    public static LoadingDialog showLoadingDialog(Context context) {
        LoadingDialog loadingDialog = new Builder(context).create();
        loadingDialog.show();
        return loadingDialog;
    }

    public static LoadingDialog showLoadingDialog(Context context, String text) {
        LoadingDialog loadingDialog = new Builder(context).setMessage(text).create();
        loadingDialog.show();
        return loadingDialog;
    }

    public static LoadingDialog showLoadingDialog(Context context, int textId) {
        LoadingDialog loadingDialog = new Builder(context).setMessage(textId).create();
        loadingDialog.show();
        return loadingDialog;
    }

    public static LoadingDialog showLoadingDialog(Context context, OnKeyDownListener onKeyDownListener) {
        LoadingDialog loadingDialog = new Builder(context).setOnKeyDownListener(onKeyDownListener).create();
        loadingDialog.show();
        return loadingDialog;
    }

    public static LoadingDialog showLoadingDialog(Context context, String text, OnKeyDownListener onKeyDownListener) {
        LoadingDialog loadingDialog = new Builder(context).setMessage(text).setOnKeyDownListener(onKeyDownListener).create();
        loadingDialog.show();
        return loadingDialog;
    }

    public static LoadingDialog showLoadingDialog(Context context, int textId, OnKeyDownListener onKeyDownListener) {
        LoadingDialog loadingDialog = new Builder(context).setMessage(textId).setOnKeyDownListener(onKeyDownListener).create();
        loadingDialog.show();
        return loadingDialog;
    }

}
