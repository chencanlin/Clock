package com.adups.clock.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adups.clock.R;
import com.adups.clock.ui.widget.AdupsClockView;

/**
 * Created by ccl on 2019/4/22 17:09
 */
public class ClockVPAdapter extends PagerAdapter {


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View clockView = LayoutInflater.from(container.getContext()).inflate(R.layout.view_adups_clock_view, container, false);
        ((AdupsClockView) clockView).setClockMode(position);
        ((AdupsClockView) clockView).start();
        container.addView(clockView);
        return clockView;
    }
}
