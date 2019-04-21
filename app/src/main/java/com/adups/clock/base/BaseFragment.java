package com.adups.clock.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adups.clock.message.CustomMessageInfo;
import com.adups.clock.message.EventBusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ccl on 2019/4/17 11:26
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected T mPresenter;
    private Unbinder mUnBinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(view);
        initEventAndData(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (isRegisterEventBus()) {
            EventBusUtil.unRegister(this);
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroyView();
    }

    /**
     * 初始化数据
     */
    private void initData(View view) {
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        mPresenter = getPresenter();
        mUnBinder = ButterKnife.bind(this, view);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract int getLayoutId();

    protected abstract T getPresenter();

    protected abstract void initEventAndData(Bundle savedInstanceState);

    @Nullable
    @Override
    public Context getContext() {
        return getActivity();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsgMainThread(CustomMessageInfo customMessageInfo) {
        onNotifyMainThread(customMessageInfo);
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
        onNotifyStickyMsgMainThread(customMessageInfo);
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

    /* protected void requestPermissions(Context context, boolean showOpenSettingDialog, PermissionCallback permissionCallback, String... permissions) {
        PermissionUtils.requestPermissions(context, showOpenSettingDialog, permissionCallback, permissions);
    }*/
}
