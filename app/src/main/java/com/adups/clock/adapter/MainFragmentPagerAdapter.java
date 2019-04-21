package com.adups.clock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adups.clock.ui.fragment.AlarmClockFragment;
import com.adups.clock.ui.fragment.ClockFragment;
import com.adups.clock.ui.fragment.StopWatchFragment;
import com.adups.clock.ui.fragment.TimerFragment;

import java.util.List;

/**
 * Created by ccl on 2019/4/17 16:08
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public MainFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = mFragmentList.get(i);
        if (fragment == null || fragment.getView() == null) {
            if (i == 0) {
                fragment = new AlarmClockFragment();
            } else if (i == 1) {
                fragment = new ClockFragment();
            } else if (i == 2) {
                fragment = new TimerFragment();
            } else if (i == 3) {
                fragment = new StopWatchFragment();
            }
            mFragmentList.remove(i);
            mFragmentList.add(i, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }
}
