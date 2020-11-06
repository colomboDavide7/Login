/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.system;

import java.util.concurrent.TimeUnit;
import login.controllers.TimerControllerInterface;

/**
 *
 * @author davidecolombo
 */
public class SystemTimer extends Thread implements ITimer {
    
    public static ITimer createTimerByLevels(TimerLevel l){
        switch(l){
            case NO_TIMER:
                return createLevelOneTimer();
            case TIMER_LEVEL_ONE:
                return createLevelTwoTimer();
            case TIMER_LEVEL_TWO:
                return createLevelThreeTimer();
            default:
                return createTestTimer(0, 0, 15);
        }
    }
    
    public static ITimer createLevelOneTimer(){
        return new SystemTimer(60); // seconds
    }
    
    public static ITimer createLevelTwoTimer(){
        return new SystemTimer(5, 0); // minutes, seconds
    }
            
    public static ITimer createLevelThreeTimer(){
        return new SystemTimer(15, 0); // minutes, seconds
    }
    
    public static ITimer createTestTimer(int hours, int minutes, int seconds){
        return new SystemTimer(hours, minutes, seconds);
    }
    
// ================================================================================
    
    private final int ZERO_TIME          = 0;
    private final int ONE_SECOND         = 1000;
    private final int SECONDS_TO_HOURS   = 3600;
    private final int MINUTES_TO_HOURS   = 60;
    private final int SECONDS_TO_MINUTES = 60;
    
    private int hours   = ZERO_TIME;
    private int minutes = ZERO_TIME;
    private int seconds = ZERO_TIME;
    private TimerControllerInterface timerController;
    
    private SystemTimer(int hours, int minutes, int seconds){
        this.hours   = hours;
        convertMinutes(minutes);
        convertSeconds(seconds);
    }
    
    private SystemTimer(int minutes, int seconds){
        convertMinutes(minutes);
        convertSeconds(seconds);
    }
    
    private SystemTimer(int seconds){
        convertSeconds(seconds);
    }
    
    private void convertMinutes(int minutes){
        this.minutes += minutes;
        
        if(this.minutes >= MINUTES_TO_HOURS){
            this.hours   += (int) Math.floor(this.minutes / MINUTES_TO_HOURS);
            this.minutes = this.minutes % MINUTES_TO_HOURS;
        }
    }
    
    private void convertSeconds(int seconds){
        this.seconds += seconds;
        
        if(this.seconds >= SECONDS_TO_HOURS){
            this.hours   += (int) Math.floor(this.seconds / SECONDS_TO_HOURS);
            convertMinutes((int) (Math.floor(this.seconds % SECONDS_TO_HOURS) / SECONDS_TO_MINUTES));
            this.seconds = (int) (Math.floor(this.seconds % SECONDS_TO_HOURS) % SECONDS_TO_MINUTES);
        }else if(this.seconds >= SECONDS_TO_MINUTES){
            this.hours   += this.ZERO_TIME;
            convertMinutes((int) Math.floor(this.seconds / SECONDS_TO_MINUTES));
            this.seconds = (int) Math.floor(this.seconds % SECONDS_TO_MINUTES);
        }
    }
    
    @Override
    public void run(){
        while(this.hours != 0 || this.minutes != 0 || this.seconds != 0)
            timerTick();
        this.timerController.timerExpired();
    }
    
    private void timerTick(){
        try{
            Thread.sleep(1000);  // ms
            if(seconds != 0){
                --seconds;
            }else{
                if(minutes != 0){
                    --minutes;
                    seconds = SECONDS_TO_MINUTES-1;
                }else{
                    if(hours != 0){
                        --hours;
                        minutes = SECONDS_TO_MINUTES-1;
                    }
                }
            }       
            // Setting time on timer Panel
            this.timerController.setTime(this.hours, this.minutes, this.seconds);
        }catch(InterruptedException ex){
            System.err.println("Timer has been interrupted\n");
            System.exit(-1);
        }
    }
    
    @Override
    public boolean matchStartTime(int time, TimeUnit u) {
        switch(u){
            case HOURS:
                return this.hours == time;
            case MINUTES:
                return this.minutes == time;
            case SECONDS:
                return this.seconds == time;
            default:
                return false;
        }
    }

    @Override
    public int getStartHours() {
        return this.hours;
    }

    @Override
    public int getStartMinutes() {
        return this.minutes;
    }

    @Override
    public int getStartSeconds() {
        return this.seconds;
    }

    @Override
    public void startCountdown() {
        this.start();
    }

    @Override
    public void setTimerController(TimerControllerInterface c) {
        this.timerController = c;
    }
    
}
