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
public interface ITimer {
    
    void setTimerController(TimerControllerInterface c);
    
    boolean matchStartTime(int time, TimeUnit u);
    
    int getStartHours();
    
    int getStartMinutes();
    
    int getStartSeconds();
    
    void startCountdown();
    
}
