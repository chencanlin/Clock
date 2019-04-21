package com.adups.clock.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adups.clock.R;
import com.adups.clock.adapter.AlarmRvAdapter;
import com.adups.clock.adapter.StopwatchDataAdapter;
import com.adups.clock.base.BaseFragment;
import com.adups.clock.base.BasePresenter;
import com.adups.clock.message.CustomMessageInfo;
import com.adups.clock.message.MessageCode;
import com.adups.clock.ui.alarm.Alarm;
import com.adups.clock.ui.alarm.data.AlarmCursor;
import com.adups.clock.ui.alarm.data.AlarmsTableManager;
import com.adups.clock.ui.alarm.data.AsyncAlarmsTableUpdateHandler;
import com.adups.clock.ui.alarm.data.ScrollHandler;
import com.adups.clock.ui.alarm.misc.AlarmController;
import com.adups.clock.ui.widget.AlarmPickerDialog;
import com.adups.clock.ui.widget.RecyclerViewDivider;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ccl on 2019/4/17 11:38
 */
public class AlarmClockFragment extends BaseFragment implements ScrollHandler {

    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;
    @BindView(R.id.rv_alarm_data)
    RecyclerView mRvAlarmData;
    private View mSnackbarAnchor;
    private AlarmController mAlarmController;
    private AsyncAlarmsTableUpdateHandler mAsyncUpdateHandler;

    private List<Alarm> alarmList = new ArrayList<>();
    private AlarmRvAdapter alarmRvAdapter;
    private AlarmsTableManager alarmsTableManager;

    public static AlarmClockFragment newInstance() {
        return new AlarmClockFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_alarm_clock;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mSnackbarAnchor = getView().findViewById(R.id.main_content);
        mAlarmController = new AlarmController(getContext(), mSnackbarAnchor);
        mAsyncUpdateHandler = new AsyncAlarmsTableUpdateHandler(getContext(),
                mSnackbarAnchor, this, mAlarmController);
        alarmsTableManager = new AlarmsTableManager(getActivity());
        AlarmCursor alarmCursor = alarmsTableManager.queryItems();
        while (alarmCursor.moveToNext()){
            alarmList.add(alarmCursor.getItem());
        }
        alarmCursor.close();
        alarmRvAdapter = new AlarmRvAdapter(R.layout.layout_alarm_rv_item, alarmList);
        alarmRvAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerViewDivider recyclerViewDivider = new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL, R.drawable.shape_divider_vertical_default);
        mRvAlarmData.addItemDecoration(recyclerViewDivider);
        mRvAlarmData.setLayoutManager(linearLayoutManager);
        mRvAlarmData.setAdapter(alarmRvAdapter);

        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmPickerDialog alarmPickerDialog = new AlarmPickerDialog(getContext());
                alarmPickerDialog.setAlarmUpdateHandler(mAsyncUpdateHandler);
                alarmPickerDialog.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyAlarmList();
    }

    @Override
    public void setScrollToStableId(long id) {

    }

    @Override
    public void scrollToPosition(int position) {

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onNotifyMainThread(CustomMessageInfo customMessageInfo) {
        switch (customMessageInfo.getMessageCode()){
            case MessageCode.SET_ALARM_SUCCEED:
                notifyAlarmList();
                break;
        }
    }

    private void notifyAlarmList(){
        if(alarmsTableManager == null){
            alarmsTableManager = new AlarmsTableManager(getActivity());
        }
        AlarmCursor alarmCursor = alarmsTableManager.queryItems();
        alarmList.clear();
        while (alarmCursor.moveToNext()){
            alarmList.add(alarmCursor.getItem());
        }
        alarmRvAdapter.notifyDataSetChanged();
    }
}
