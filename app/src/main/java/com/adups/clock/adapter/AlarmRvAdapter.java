package com.adups.clock.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ToggleButton;

import com.adups.clock.R;
import com.adups.clock.bean.StopwatchDataBean;
import com.adups.clock.ui.alarm.Alarm;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

public class AlarmRvAdapter extends BaseQuickAdapter<Alarm, BaseViewHolder> {

    private DecimalFormat mFormat = new DecimalFormat("00");

    public AlarmRvAdapter(int layoutResId, @Nullable List<Alarm> data) {
        super(layoutResId, data);
    }

    public AlarmRvAdapter(@Nullable List<Alarm> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Alarm item) {
        helper.setText(R.id.tv_time, mContext.getString(R.string.stopwatch_minute_second, mFormat.format(item.hour()), mFormat.format(item.minutes())));
        ((ToggleButton) helper.getView(R.id.tb_enable)).setChecked(item.isEnabled());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
