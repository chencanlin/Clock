package com.adups.clock.adapter;

import android.support.annotation.Nullable;

import com.adups.clock.R;
import com.adups.clock.bean.StopwatchDataBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class StopwatchDataAdapter extends BaseQuickAdapter<StopwatchDataBean, BaseViewHolder> {
    public StopwatchDataAdapter(int layoutResId, @Nullable List<StopwatchDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StopwatchDataBean item) {
        helper.setText(R.id.tv_number, String.valueOf(item.number));
        helper.setText(R.id.tv_interval, String.valueOf(item.interval));
        helper.setText(R.id.tv_duration, String.valueOf(item.duration));
    }
}
