package com.adups.clock.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adups.clock.R;
import com.adups.clock.utils.DensityUtils;


/**
 * Created by ccl on 2019/4/22 19:34
 */
public class ClockVPDotView extends LinearLayout {

    private int mNubers;
    private ViewPager mViewPager;

    public ClockVPDotView(Context context) {
        this(context, null);
    }

    public ClockVPDotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockVPDotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void bindViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null) {
            int count = adapter.getCount();
            for (int i = 0; i < count; i++) {
                TextView textView = new TextView(getContext());
                textView.setBackground(getResources().getDrawable(R.drawable.selector_clock_dot_bg));
                textView.setId(i);
                textView.setSelected(viewPager.getCurrentItem() == i);
                LayoutParams layoutParams = new LayoutParams(DensityUtils.dp2px(getContext(), 6), DensityUtils.dp2px(getContext(), 6));
                if (i > 0) {
                    layoutParams.leftMargin = DensityUtils.dp2px(getContext(), 10);
                }
                addView(textView, layoutParams);
            }
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    setDotSelected(i);
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    public void setDotSelected(int position) {
        if (mViewPager != null && mViewPager.getAdapter() != null) {
            int count = mViewPager.getAdapter().getCount();
            if (position >= count) {
                return;
            }
            for (int i = 0; i < count; i++) {
                findViewById(i).setSelected(i == position);
            }
        }
    }
}
