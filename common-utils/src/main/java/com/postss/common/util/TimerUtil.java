package com.postss.common.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器工具类
 * @author jwsun
 * @date   2016年11月21日 下午4:20:57
 */
public abstract class TimerUtil {
    /**Timer是一种定时器工具,用来在一个后台线程计划执行指定任务.它可以计划执行一个任务一次或反复多次.**/
    private Timer timer;
    /**TimerTask一个抽象类,它的子类代表一个可以被Timer计划的任务.**/
    private TimerTask timerTask;
    /**定时器名称**/
    private String timerName = null;

    /**任务方法**/
    public abstract void runVoid();

    public TimerUtil() {
        super();
    }

    public TimerUtil(String timerName) {
        super();
        this.timerName = timerName;
    }

    /**
     * 开始定时任务
     * @author jwsun
     * @date 2016年11月21日 下午4:21:54 
     * @param delay 开始时间
     * @param period 每次任务相隔时间
     */
    public void start(long delay, long period) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerName != null) {
            timer = new Timer(timerName);
        } else {
            timer = new Timer();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runVoid();
            }
        };
        timer.schedule(timerTask, delay, period);
    }

    /**
     * 停止定时任务
     * @author jwsun
     * @date 2016年11月21日 下午4:22:42
     */
    public void stop() {
        timer.cancel();
        timer = null;
    }

    /**
     * 是否正在执行定时任务
     * @author jwsun
     * @date 2016年11月21日 下午4:22:52 
     * @return
     */
    public Boolean isRun() {
        if (timer == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 定时任务名称
     * @author jwsun
     * @date 2016年11月21日 下午4:23:05 
     * @param timerName
     */
    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

}
