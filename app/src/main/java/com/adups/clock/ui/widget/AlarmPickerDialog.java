package com.adups.clock.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.TimePicker;

import com.adups.clock.R;
import com.adups.clock.message.EventBusUtil;
import com.adups.clock.message.MessageCode;
import com.adups.clock.ui.alarm.Alarm;
import com.adups.clock.ui.alarm.data.AsyncAlarmsTableUpdateHandler;
import com.adups.clock.ui.alarm.data.ScrollHandler;
import com.adups.clock.ui.alarm.misc.AlarmController;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccl on 2019/4/19 14:18
 */
public class AlarmPickerDialog extends Dialog{

    private AsyncAlarmsTableUpdateHandler mAsyncUpdateHandler;
    private AlarmController mAlarmController;
    private View mSnackbarAnchor;

    @BindView(R.id.time_picker)
    TimePicker mTimePicker;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    private AsyncAlarmsTableUpdateHandler alarmUpdateHandler;

    public AlarmPickerDialog(Context context) {
        this(context, R.style.BottomDialogTransparentStyle);
    }

    public AlarmPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_alarm_picker_dialog, null);
        setContentView(inflate);
        ButterKnife.bind(this);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        if (window != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getWindow().setGravity(Gravity.CENTER);
        }
    }

    @OnClick(R.id.tv_save)
    public void saveAlarm(View view){

        Alarm alarm = Alarm.builder()
                .hour(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M? mTimePicker.getHour():mTimePicker.getCurrentHour())
                .minutes(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M? mTimePicker.getMinute():mTimePicker.getCurrentMinute())
                .vibrates(true)
                .build();
        alarm.setEnabled(true);
        alarmUpdateHandler.asyncInsert(alarm);
        dismiss();
    }

    @OnClick(R.id.tv_cancel)
    public void cancel(View view){
        dismiss();
    }

    public void setAlarmUpdateHandler(AsyncAlarmsTableUpdateHandler alarmUpdateHandler){
        this.alarmUpdateHandler = alarmUpdateHandler;
    }
}
