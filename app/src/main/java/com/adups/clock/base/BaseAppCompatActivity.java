package com.adups.clock.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.adups.clock.message.CustomMessageInfo;
import com.adups.clock.message.EventBusUtil;
import com.adups.clock.ui.widget.LoadingDialog;
import com.adups.clock.utils.BarUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ccl on 2019/4/17 10:23
 */
public abstract class BaseAppCompatActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView, LoadingDialog.OnKeyDownListener {

    protected Activity mContext;
    private Unbinder mUnBinder;
    protected T mPresenter;
    protected View baseContentView;
    private LoadingDialog mLoadingDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        setContentView(getLayout());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BarUtils.setStatusBarAlpha(this, 0);
        }
        initData();
        initEventAndData(savedInstanceState);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract int getLayout();

    protected abstract T getPresenter();

    protected abstract void initEventAndData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unRegister(this);
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsgMainThread(CustomMessageInfo customMessageInfo) {
        if (customMessageInfo != null) {
            onNotifyMainThread(customMessageInfo);
        }
    }

    /**
     * 重写此方法在主线程接收事件
     *
     * @param customMessageInfo 接收的事件
     * @author ccl
     */
    protected void onNotifyMainThread(CustomMessageInfo customMessageInfo) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyMsgMainThread(CustomMessageInfo customMessageInfo) {
        if (customMessageInfo != null) {
            onNotifyStickyMsgMainThread(customMessageInfo);
        }
    }

    /**
     * 重写此方法在主线程接收粘性事件
     *
     * @param customMessageInfo 接收的事件
     * @author ccl
     */
    protected void onNotifyStickyMsgMainThread(CustomMessageInfo customMessageInfo) {

    }

    /**
     * 是否需要注册事件监听
     *
     * @author ccl
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.showLoadingDialog(this, this);
        } else {
            mLoadingDialog.setMessage("");
            mLoadingDialog.setOnKeyDownListener(this);
            mLoadingDialog.show();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void showLoading(int strId) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.showLoadingDialog(this, strId, this);
        } else {
            mLoadingDialog.setMessage(strId);
            mLoadingDialog.setOnKeyDownListener(this);
            mLoadingDialog.show();
        }
    }

    public void showLoading(String text) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.showLoadingDialog(this, text, this);
        } else {
            mLoadingDialog.setMessage(text);
            mLoadingDialog.setOnKeyDownListener(this);
            mLoadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onLoadingDialogKeyDown(int keyCode, KeyEvent event) {
        onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}