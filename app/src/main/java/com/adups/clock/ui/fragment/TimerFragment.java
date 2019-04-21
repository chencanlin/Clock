package com.adups.clock.ui.fragment;

import android.os.Bundle;

import com.adups.clock.R;
import com.adups.clock.base.BaseFragment;
import com.adups.clock.base.BasePresenter;

/**
 * Created by ccl on 2019/4/17 11:38
 */
public class TimerFragment extends BaseFragment {

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_timer;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }
}
