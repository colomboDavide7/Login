/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.system;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author davidecolombo
 */
public class SystemTimer extends Thread implements ITimer {
    private final int ZERO_TIME          = 0;
    private final int ONE_SECOND         = 1000;
    private final int SECONDS_TO_HOURS   = 3600;
    private final int MINUTES_TO_HOURS   = 60;
    private final int SECONDS_TO_MINUTES = 60;
    
    private int hours   = ZERO_TIME;
    private int minutes = ZERO_TIME;
    private int seconds = ZERO_TIME;
    
    public SystemTimer(int hours, int minutes, int seconds){
        this.hours   = hours;
        convertMinutes(minutes);
        convertSeconds(seconds);
    }
    
    public SystemTimer(int minutes, int seconds){
        convertMinutes(minutes);
        convertSeconds(seconds);
    }
    
    public SystemTimer(int seconds){
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
    
}
