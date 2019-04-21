package com.adups.clock.ui.fragment;

import android.os.Bundle;

import com.adups.clock.R;
import com.adups.clock.base.BaseFragment;
import com.adups.clock.base.BasePresenter;

/**
 * Created by ccl on 2019/4/17 11:38
 */
public class StopWatchFragment extends BaseFragment {

    public static StopWatchFragment newInstance() {
        return new StopWatchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stopwatch;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }
}
