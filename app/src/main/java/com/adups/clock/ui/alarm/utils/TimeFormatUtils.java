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

package com.adups.clock.ui.alarm.utils;

import android.content.Context;

import com.adups.clock.R;

import java.util.Calendar;
import java.util.Date;

import static android.text.format.DateFormat.getTimeFormat;

/**
 * Created by Phillip Hsu on 6/3/2016.
 */
public final class TimeFormatUtils {

    public static String getMonthAbbreviationText(Context context, int month) {
        String[] monthAbbreviationArray = context.getResources().getStringArray(R.array.month_abbreviation_text_array);
        if (month > 12 || month <= 0) {
            return monthAbbreviationArray[0];
        } else {
            return monthAbbreviationArray[month - 1];
        }
    }

    public static String getMonthText(Context context, int month) {
        String[] monthArray = context.getResources().getStringArray(R.array.month_text_array);
        if (month > 12 || month <= 0) {
            return monthArray[0];
        } else {
            return monthArray[month - 1];
        }
    }

    public static String getDayAbbreviationText(Context context, int day) {
        String[] dayAbbreviationArray = context.getResources().getStringArray(R.array.day_abbreviation_text_array);
        if (day > 7 || day <= 0) {
            return dayAbbreviationArray[0];
        } else {
            return dayAbbreviationArray[day - 1];
        }
    }

    public static String getDayText(Context context, int day) {
        String[] dayArray = context.getResources().getStringArray(R.array.day_text_array);
        if (day > 7 || day <= 0) {
            return dayArray[0];
        } else {
            return dayArray[day - 1];
        }
    }


    private TimeFormatUtils() {
    }

    public static String formatTime(Context context, long millis) {
        return getTimeFormat(context).format(new Date(millis));
    }

    public static String formatTime(Context context, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        return formatTime(context, cal.getTimeInMillis());
    }
}
