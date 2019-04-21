package com.adups.clock.message;

/**
 * Created by ccl on 2019/4/17 10:23
 */

public interface OnNotifyListener {

    //     （为了规范接口名，实现该接口后还需要添加EventBus注解）
    void onNotify(CustomMessageInfo customMessageInfo);
}
