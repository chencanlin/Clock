package com.adups.clock.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.adups.clock.R;
import com.adups.clock.adapter.ClockVPAdapter;
import com.adups.clock.base.BaseFragment;
import com.adups.clock.base.BasePresenter;
import com.adups.clock.ui.widget.ClockVPDotView;

import butterknife.BindView;

/**
 * Created by ccl on 2019/4/17 11:38
 */
public class ClockFragment extends BaseFragment {

    @BindView(R.id.vp_clock)
    ViewPager mVpClock;
    @BindView(R.id.clock_dot_view)
    ClockVPDotView mClockVPDotView;

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
        mVpClock.setAdapter(new ClockVPAdapter());
        mClockVPDotView.bindViewPager(mVpClock);
    }
}
