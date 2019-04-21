package com.adups.clock.bean;

import lombok.Data;

@Data
public class StopwatchDataBean {
    public int number;
    public String interval;
    public int ms;
    public int second;
    public int minute;
    public String duration;
}
