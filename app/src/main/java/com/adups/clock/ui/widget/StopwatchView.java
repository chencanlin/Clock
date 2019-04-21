package com.adups.clock.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adups.clock.R;
import com.adups.clock.adapter.StopwatchDataAdapter;
import com.adups.clock.bean.StopwatchDataBean;
import com.adups.clock.utils.AnimationUtil;
import com.adups.clock.utils.DensityUtils;
import com.adups.clock.utils.ScreenUtil;
import com.airbnb.lottie.LottieAnimationView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.lang.ref.SoftReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccl on 2019/4/18 11:09
 */
public class StopwatchView extends LinearLayout {

    @BindView(R.id.fl_stopwatch)
    FrameLayout mFlStopwatch;
    @BindView(R.id.animation_view)
    LottieAnimationView mLottieAnimationView;
    @BindView(R.id.tv_ms)
    TextView mTvMs;
    @BindView(R.id.tv_second)
    TextView mTvSecond;
    @BindView(R.id.ll_button)
    LinearLayout mLlBtn;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_record)
    Button mBtnRecord;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;

    private int minute;
    private int second;
    private int ms;
    private int number;
    private DecimalFormat mFormat = new DecimalFormat("00");
    private StopwatchHandler mStopwatchHandler;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private boolean flag = false;
    private boolean isReset = true;
    private List<StopwatchDataBean> stopwatchDataBeanList = new ArrayList<>();
    private StopwatchDataAdapter stopwatchDataAdapter;

    public StopwatchView(Context context) {
        this(context, null);
    }

    public StopwatchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StopwatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_stopwatch_view, this, true);
        ButterKnife.bind(this);
        stopwatchDataAdapter = new StopwatchDataAdapter(R.layout.layout_stopwatch_data_rv_item, stopwatchDataBeanList);
        stopwatchDataAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        RecyclerViewDivider recyclerViewDivider = new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL, R.drawable.shape_divider_vertical_default);
        mRvData.addItemDecoration(recyclerViewDivider);
        mRvData.setLayoutManager(linearLayoutManager);
        mRvData.setAdapter(stopwatchDataAdapter);
        setTimeText();
    }

    @OnClick(R.id.btn_reset)
    public void reset(View view) {
        boolean enabled = mBtnReset.isEnabled();
        mBtnReset.setEnabled(!enabled);
        if (enabled) {
            resetTime();
        }
    }

    private void resetTime() {
        stopwatchDataBeanList.clear();
        stopwatchDataAdapter.notifyDataSetChanged();
        isReset = true;
        stopTimeKeeping();
        ms = 0;
        second = 0;
        minute = 0;
        setTimeText();
        mBtnStart.setSelected(false);
    }

    @OnClick(R.id.btn_record)
    public void record(View view) {
        int ms = this.ms;
        int second = this.second;
        int minute = this.minute;
        StopwatchDataBean lastStopwatchDataBean = new StopwatchDataBean();
        if (stopwatchDataBeanList.size() > 0) {
            lastStopwatchDataBean = stopwatchDataBeanList.get(stopwatchDataBeanList.size() - 1);
        }
        StopwatchDataBean stopwatchDataBean = new StopwatchDataBean();
        stopwatchDataBean.number = ++number;
        int intervalMinute = minute - lastStopwatchDataBean.minute;
        int intervalSecond = second - lastStopwatchDataBean.second;
        if (intervalSecond < 0) {
            intervalMinute = intervalMinute - 1;
            intervalSecond = 60 + intervalSecond;
        }
        int intervalMs = ms - lastStopwatchDataBean.ms;
        if (intervalMs < 0) {
            intervalSecond = intervalSecond - 1;
            intervalMs = 100 + intervalMs;
        }
        stopwatchDataBean.interval = getResources().getString(R.string.stopwatch_interval, mFormat.format(intervalMinute), mFormat.format(intervalSecond), mFormat.format(intervalMs));
        stopwatchDataBean.duration = getResources().getString(R.string.stopwatch_duration, mFormat.format(minute), mFormat.format(second), mFormat.format(ms));
        stopwatchDataBean.minute = minute;
        stopwatchDataBean.second = second;
        stopwatchDataBean.ms = ms;
        stopwatchDataBeanList.add(stopwatchDataBean);
        stopwatchDataAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_start)
    public void start(View view) {
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
        if (isReset) {
            AnimationUtil.startTranslateYAnimation(getContext(), mBtnStart, 0, ScreenUtil.getScreenHeight(getContext()) - ScreenUtil.getViewInScreen(mBtnStart)[1] - mBtnStart.getMeasuredHeight() - DensityUtils.dp2px(getContext(), 20));
            AnimationUtil.startTranslateYAnimation(getContext(), mBtnReset, 0, ScreenUtil.getScreenHeight(getContext()) - ScreenUtil.getViewInScreen(mBtnReset)[1] - mBtnReset.getMeasuredHeight() - DensityUtils.dp2px(getContext(), 20));
            AnimationUtil.startTranslateYAnimation(getContext(), mBtnRecord, 0, ScreenUtil.getScreenHeight(getContext()) - ScreenUtil.getViewInScreen(mBtnRecord)[1] - mBtnRecord.getMeasuredHeight() - DensityUtils.dp2px(getContext(), 20));
        }
        flag = true;
        if (mStopwatchHandler == null) {
            mStopwatchHandler = new StopwatchHandler(getContext());
        }
        if (mTimer == null) {
            mTimer = new Timer();
        } else {
            mTimer.purge();
        }
        if (mMyTimerTask == null) {
            mMyTimerTask = new MyTimerTask(getContext());
        }
        mTimer.schedule(mMyTimerTask, 10, 10);
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
            AnimationUtil.startTranslateYAnimation(getContext(), mBtnStart, ScreenUtil.getViewInScreen(mBtnStart)[1] - ScreenUtil.getViewInScreen(mLlBtn)[1] - DensityUtils.dp2px(getContext(), 20), 0);
            AnimationUtil.startTranslateYAnimation(getContext(), mBtnReset, ScreenUtil.getViewInScreen(mBtnReset)[1] - ScreenUtil.getViewInScreen(mLlBtn)[1] - DensityUtils.dp2px(getContext(), 20), 0);
            AnimationUtil.startTranslateYAnimation(getContext(), mBtnRecord, ScreenUtil.getViewInScreen(mBtnRecord)[1] - ScreenUtil.getViewInScreen(mLlBtn)[1] - DensityUtils.dp2px(getContext(), 20), 0);
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
        stopTimeKeepingAnimation();
    }

    private void setTime() {
        if (flag) {
            ms++;
            if (ms == 100) {
                ms = 0;
                second++;
                if (second == 60) {
                    second = 0;
                    minute++;
                }
            }
            setTimeText();
        }
    }

    private void setTimeText() {
        mTvSecond.setText(mFormat.format(ms));
        mTvMs.setText(getResources().getString(R.string.stopwatch_minute_second, mFormat.format(minute), mFormat.format(second)));
    }

    private class MyTimerTask extends TimerTask {
        private SoftReference<Context> mContextSoftReference;

        public MyTimerTask(Context context) {
            mContextSoftReference = new SoftReference<>(context);
        }

        @Override
        public void run() {
            if (mContextSoftReference != null && mContextSoftReference.get() != null) {
                mStopwatchHandler.sendEmptyMessage(22);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    public class StopwatchHandler extends Handler {
        private SoftReference<Context> mContextSoftReference;

        public StopwatchHandler(Context context) {
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
