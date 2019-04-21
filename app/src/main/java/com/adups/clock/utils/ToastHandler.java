package com.adups.clock.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.adups.clock.ClockApplication;

/**
 * Created by ccl on 2019/4/17 11:23
 */
public class ToastHandler {
    private static Toast m_toast;

    public static void showMessage(int resId) {
        showMessage(resId, Toast.LENGTH_SHORT);
    }

    public static void showMessage(String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(int resId, int duration) {
        String msg = ClockApplication.getInstance().getString(resId);
        showMessage(msg, duration);
    }

    @SuppressLint("ShowToast")
    public static void showMessage(String msg, int duration) {
        try {
            if (m_toast != null) {
                m_toast.cancel();
//                m_toast.setGravity(Gravity.CENTER, 0, 0);
            }
            m_toast = Toast.makeText(ClockApplication.getInstance().getApplicationContext(), msg, duration);
            m_toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
