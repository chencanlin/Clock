/*
 * Copyright 2017 Phillip Hsu
 *
 * This file is part of ClockPlus.
 *
 * ClockPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ClockPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ClockPlus.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.adups.clock.ui.alarm.ringtone;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.view.ViewGroup;

import com.adups.clock.R;
import com.adups.clock.base.BasePresenter;
import com.adups.clock.constants.NotificationConstant;
import com.adups.clock.ui.alarm.Alarm;
import com.adups.clock.ui.alarm.misc.AlarmController;
import com.adups.clock.ui.alarm.ringtone.playback.AlarmRingtoneService;
import com.adups.clock.ui.alarm.ringtone.playback.RingtoneService;
import com.adups.clock.ui.alarm.utils.TimeFormatUtils;

import static android.content.Context.NOTIFICATION_SERVICE;


public class AlarmActivity extends RingtoneActivity<Alarm> {
    private static final String TAG = "AlarmActivity";

    private AlarmController mAlarmController;
    private NotificationManager mNotificationManager;


    @Override
    protected int menuResId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlarmController = new AlarmController(this, null);
        // TODO: If the upcoming alarm notification isn't present, verify other notifications aren't affected.
        // This could be the case if we're starting a new instance of this activity after leaving the first launch.
        mAlarmController.removeUpcomingAlarmNotification(getRingingObject());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void finish() {
        super.finish();
        // If the presently ringing alarm is about to be superseded by a successive alarm,
        // this, unfortunately, will cancel the missed alarm notification for the presently
        // ringing alarm.
        //
        // A workaround is to override onNewIntent() and post the missed alarm notification again,
        // AFTER calling through to its base implementation, because it calls finish().
        mNotificationManager.cancel(TAG, getRingingObject().getIntId());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // -------------- TOneverDO: precede super ---------------
        // Even though the base implementation calls finish() on this instance and starts a new
        // instance, this instance will still be alive with all of its member state intact at
        // this point. So this notification will still refer to the Alarm that was just missed.
        postMissedAlarmNote();
    }

    @Override
    protected Class<? extends RingtoneService> getRingtoneServiceClass() {
        return AlarmRingtoneService.class;
    }

    @Override
    protected CharSequence getHeaderTitle() {
        return getRingingObject().label();
    }

    @Override
    protected void getHeaderContent(ViewGroup parent) {
        // TODO: Consider applying size span on the am/pm label
        getLayoutInflater().inflate(R.layout.content_header_alarm_activity, parent, true);
    }

    @Override
    protected int getAutoSilencedText() {
        return R.string.alarm_auto_silenced_text;
    }

    @Override
    protected int getLeftButtonText() {
        return R.string.snooze;
    }

    @Override
    protected int getRightButtonText() {
        return R.string.dismiss;
    }

    @Override
    protected int getLeftButtonDrawable() {
        return R.drawable.ic_snooze_48dp;
    }

    @Override
    protected int getRightButtonDrawable() {
        return R.drawable.ic_dismiss_alarm_48dp;
    }

    @Override
    protected void onLeftButtonClick() {
        mAlarmController.snoozeAlarm(getRingingObject());
        // Can't call dismiss() because we don't want to also call cancelAlarm()! Why? For example,
        // we don't want the alarm, if it has no recurrence, to be turned off right now.
        stopAndFinish();
    }

    @Override
    protected void onRightButtonClick() {
        // TODO do we really need to cancel the intent and alarm?
        mAlarmController.cancelAlarm(getRingingObject(), false, true);
        stopAndFinish();
    }

    @Override
    protected Parcelable.Creator<Alarm> getParcelableCreator() {
        return Alarm.CREATOR;
    }

    // TODO: Consider changing the return type to Notification, and move the actual
    // task of notifying to the base class.
    @Override
    protected void showAutoSilenced() {
        super.showAutoSilenced();
        postMissedAlarmNote();
    }

    private void postMissedAlarmNote() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NotificationConstant.CHANNEL_ID, "LOGIPACE", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        String alarmTime = TimeFormatUtils.formatTime(this,
                getRingingObject().hour(), getRingingObject().minutes());
        Notification note = new NotificationCompat.Builder(this, NotificationConstant.CHANNEL_ID)
                .setContentTitle(getString(R.string.missed_alarm))
                .setContentText(alarmTime)
                .setSmallIcon(R.drawable.ic_alarm_24dp)
                .build();
        mNotificationManager.notify(TAG, getRingingObject().getIntId(), note);
    }
}
