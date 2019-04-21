package com.adups.clock.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.adups.clock.R;
import com.adups.clock.adapter.MainFragmentPagerAdapter;
import com.adups.clock.adapter.TabNavigatorAdapter;
import com.adups.clock.base.BaseAppCompatActivity;
import com.adups.clock.ui.fragment.AlarmClockFragment;
import com.adups.clock.ui.fragment.ClockFragment;
import com.adups.clock.ui.fragment.StopWatchFragment;
import com.adups.clock.ui.fragment.TimerFragment;
import com.adups.clock.ui.main.presenter.MainActivityPresenter;
import com.adups.clock.ui.main.view.MainView;
import com.adups.clock.utils.ToastHandler;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseAppCompatActivity<MainActivityPresenter> implements MainView {

    public static final int    PAGE_ALARMS          = 0;
    public static final int    PAGE_TIMERS          = 1;
    public static final int    PAGE_STOPWATCH       = 2;
    public static final int    REQUEST_THEME_CHANGE = 5;
    public static final String EXTRA_SHOW_PAGE      = "com.philliphsu.clock2.extra.SHOW_PAGE";
    public static final String ACTION_SCROLL_TO_STABLE_ID = "com.philliphsu.clock2.list.action.SCROLL_TO_STABLE_ID";
    public static final String EXTRA_SCROLL_TO_STABLE_ID = "com.philliphsu.clock2.list.extra.SCROLL_TO_STABLE_ID";

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_slide_menu)
    ImageView mIvSlideMenu;
    @BindView(R.id.tab)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private int[] arrayImage = {R.drawable.selector_tab_alarm_iv, R.drawable.selector_tab_clock_iv, R.drawable.selector_tab_timer_iv, R.drawable.selector_tab_stop_watch_iv};
    private boolean mBExit = false;
    private Handler mHandler = new Handler();

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainActivityPresenter getPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initFragments();
        setSupportActionBar(mToolbar);
        mIvSlideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
//                MenuActivity.start(MainActivity.this);
            }
        });
        MainFragmentPagerAdapter mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mainFragmentPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new TabNavigatorAdapter(mViewPager, arrayImage, getResources().getStringArray(R.array.tab_title)));
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private void initFragments() {
        mFragmentList.add(AlarmClockFragment.newInstance());
        mFragmentList.add(ClockFragment.newInstance());
        mFragmentList.add(TimerFragment.newInstance());
        mFragmentList.add(StopWatchFragment.newInstance());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (!mBExit) {
                ToastHandler.showMessage(R.string.main_exit);
                mBExit = true;
                mHandler.postDelayed(new MyExitRunnable(MainActivity.this), 2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private static class MyExitRunnable implements Runnable {
        private WeakReference<Context> activityWeakReference;

        private MyExitRunnable(MainActivity mainActivity) {
            activityWeakReference = new WeakReference<Context>(mainActivity);
        }

        @Override
        public void run() {
            if (activityWeakReference != null && activityWeakReference.get() != null) {
                ((MainActivity) activityWeakReference.get()).mBExit = false;
                activityWeakReference.clear();
            }
        }
    }
}
