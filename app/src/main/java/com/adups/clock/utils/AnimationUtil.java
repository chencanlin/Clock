package com.adups.clock.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

public class AnimationUtil {

    public static void startTranslateYAnimation(Context context , View target, float ... value){
        ObjectAnimator x = ObjectAnimator.ofFloat(target, "TranslationY", value);
        x.setDuration(500);
        x.start();
    }
}
