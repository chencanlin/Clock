package com.adups.clock.utils;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ScreenUtil {
    public static int getScreenHeight(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        return defaultDisplay.getHeight();
    }

    public static int[] getViewInScreen(View view){
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        return position;
    }
}
