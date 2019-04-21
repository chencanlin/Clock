package com.adups.clock.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adups.clock.R;
import com.adups.clock.utils.ToastHandler;
import com.airbnb.lottie.LottieAnimationView;

import java.lang.ref.SoftReference;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class TimerView extends LinearLayout {

    @BindView(R.id.animation_view)
    LottieAnimationView mLottieAnimationView;
    @BindView(R.id.ll_timer_select)
    LinearLayout mLlTimerSelect;
    @BindView(R.id.np_hour)
    NumberPickerView mNPHour;
    @BindView(R.id.np_minute)
    NumberPickerView mNPMinute;
    @BindView(R.id.np_second)
    NumberPickerView mNPSecond;
    @BindView(R.id.ll_timer)
    LinearLayout mLlTimer;
    @BindView(R.id.tv_hour)
    TextView mTvHour;
    @BindView(R.id.tv_minute)
    TextView mTvMinute;
    @BindView(R.id.tv_second)
    TextView mTvSecond;

    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_start)
    Button mBtnStart;

    private int hour;
    private int minute;
    private int second;
    private DecimalFormat mFormat = new DecimalFormat("00");
    private TimerHandler mTimerHandler;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private boolean flag = false;
    private boolean isReset = true;

    public TimerView(Context context) {
        this(context, null);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_timer_view, this, true);
        ButterKnife.bind(this);
        resetNPValue();
    }

    private void resetNPValue(){
        mNPHour.setDisplayedValues(getContext().getResources().getStringArray(R.array.hour_array));
        mNPHour.setMaxValue(23);
        mNPHour.setMinValue(0);
        mNPMinute.setDisplayedValues(getContext().getResources().getStringArray(R.array.minute_and_second_array));
        mNPMinute.setMaxValue(59);
        mNPMinute.setMinValue(0);
        mNPSecond.setDisplayedValues(getContext().getResources().getStringArray(R.array.minute_and_second_array));
        mNPSecond.setMaxValue(59);
        mNPSecond.setMinValue(0);
    }

    @OnClick(R.id.btn_reset)
    public void reset(View view) {
        boolean enabled = mBtnReset.isEnabled();
        mBtnReset.setEnabled(!enabled);
        if (enabled) {
            resetStatus();
        }
    }

    private void resetStatus() {
        isReset = true;
        stopTimeKeeping();
        hour = 0;
        second = 0;
        minute = 0;
        setTimeText();
        mBtnStart.setSelected(false);
        mLlTimerSelect.setVisibility(View.VISIBLE);
        resetNPValue();
        mLlTimer.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_start)
    public void start(View view) {
        if (isReset) {
            int hour = mNPHour.getValue() - mNPHour.getMinValue();
            int minute = mNPMinute.getValue() - mNPMinute.getMinValue();
            int second = mNPSecond.getValue() - mNPSecond.getMinValue();
            if(hour == 0 && minute == 0 && second == 0){
                ToastHandler.showMessage("请选择定时器");
                return;
            }
            mLlTimerSelect.setVisibility(View.GONE);
            mLlTimer.setVisibility(View.VISIBLE);
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            setTimeText();
        }
        boolean selected = mBtnStart.isSelected();
        mBtnStart.setSelected(!selected);
        if (!selected) {
            startTimeKeeping();
            mBtnReset.setEnabled(true);
        } else {
            stopTimeKeeping();
        }
    }

    private void startTimeKeeping() {
        flag = true;
        if (mTimerHandler == null) {
            mTimerHandler = new TimerHandler(getContext());
        }
        if (mTimer == null) {
            mTimer = new Timer();
        } else {
            mTimer.purge();
        }
        if (mMyTimerTask == null) {
            mMyTimerTask = new MyTimerTask(getContext());
        }
        mTimer.schedule(mMyTimerTask, 1000, 1000);
        startTimeKeepingAnimation();
        isReset = false;
    }

    private void startTimeKeepingAnimation() {
        mLottieAnimationView.setSpeed(2f);
        mLottieAnimationView.resumeAnimation();
    }

    private void stopTimeKeepingAnimation() {
        if (isReset) {
            mLottieAnimationView.cancelAnimation();
        } else {
            mLottieAnimationView.pauseAnimation();
        }
    }

    private void stopTimeKeeping() {
        flag = false;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mMyTimerTask != null) {
            mMyTimerTask.cancel();
            mMyTimerTask = null;
        }
        if(mTimerHandler != null){
            mTimerHandler.removeCallbacksAndMessages(null);
        }
        stopTimeKeepingAnimation();
    }

    private void setTime() {
        if (flag) {
            if (second == 0) {
                if (minute > 0) {
                    minute--;
                    second = 59;
                } else {
                    if (hour > 0) {
                        hour--;
                        minute = 59;
                    } else {
                        isReset = true;
                    }
                }
            } else {
                second--;
            }
            setTimeText();
            if (isReset) {
                resetStatus();
            }
        }
    }

    private void setTimeText() {
        mTvHour.setText(mFormat.format(hour));
        mTvMinute.setText(mFormat.format(minute));
        mTvSecond.setText(mFormat.format(second));
    }

    private class MyTimerTask extends TimerTask {
        private SoftReference<Context> mContextSoftReference;

        public MyTimerTask(Context context) {
            mContextSoftReference = new SoftReference<>(context);
        }

        @Override
        public void run() {
            if (mContextSoftReference != null && mContextSoftReference.get() != null) {
                if(flag) {
                    mTimerHandler.sendEmptyMessage(22);
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    public class TimerHandler extends Handler {
        private SoftReference<Context> mContextSoftReference;

        public TimerHandler(Context context) {
            mContextSoftReference = new SoftReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 22) {
                setTime();
            }
        }
    }
}
