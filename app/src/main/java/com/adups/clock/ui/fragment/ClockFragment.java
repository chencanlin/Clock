package com.adups.clock.ui.fragment;

import android.os.Bundle;

import com.adups.clock.R;
import com.adups.clock.base.BaseFragment;
import com.adups.clock.base.BasePresenter;

/**
 * Created by ccl on 2019/4/17 11:38
 */
public class ClockFragment extends BaseFragment {

    public static ClockFragment newInstance() {
        return new ClockFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_clock;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }
}
