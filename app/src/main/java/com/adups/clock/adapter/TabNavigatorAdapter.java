package com.adups.clock.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adups.clock.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

/**
 * Created by ccl on 2019/4/17 19:32
 */
public class TabNavigatorAdapter extends CommonNavigatorAdapter {

    private final ViewPager mViewPager;
    private final int[] mImageArray;
    private final String[] mTextArray;

    public TabNavigatorAdapter(ViewPager viewPager, int[] imageArray, String[] textArray) {
        mViewPager = viewPager;
        mImageArray = imageArray;
        mTextArray = textArray;
    }

    @Override
    public int getCount() {
        return mImageArray == null ? 0 : mImageArray.length;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int i) {
        CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

        // load custom layout
        View customLayout = LayoutInflater.from(context).inflate(R.layout.tablayout_item, null);
        final ImageView titleImg = customLayout.findViewById(R.id.item_image);
        final TextView titleText = customLayout.findViewById(R.id.item_text);
        titleImg.setImageResource(mImageArray[i]);
        titleText.setText(mTextArray[i]);
        commonPagerTitleView.setContentView(customLayout);

        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

            @Override
            public void onSelected(int index, int totalCount) {
                titleText.setSelected(true);
                titleImg.setSelected(true);
            }

            @Override
            public void onDeselected(int index, int totalCount) {
                titleText.setSelected(false);
                titleImg.setSelected(false);
            }

            @Override
            public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
//                titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
//                titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
            }

            @Override
            public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
//                titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
//                titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
            }
        });

        commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(i);
            }
        });

        return commonPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }
}
