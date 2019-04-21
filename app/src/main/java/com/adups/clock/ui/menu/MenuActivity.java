package com.adups.clock.ui.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.adups.clock.R;
import com.adups.clock.base.BaseAppCompatActivity;
import com.adups.clock.base.BasePresenter;

/**
 * Created by ccl on 2019/4/18 09:50
 */
public class MenuActivity extends BaseAppCompatActivity<BasePresenter> {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MenuActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.common_activity_finish_slide_in, 0);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_menu;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        init();
    }

    private void init() {

    }
}
