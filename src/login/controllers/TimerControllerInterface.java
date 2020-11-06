/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controllers;

import login.UI.ITimerPanel;
import login.system.ITimer;

/**
 *
 * @author davidecolombo
 */
public interface TimerControllerInterface {
    
    void timerExpired();
    
    void setTime(int hours, int minutes, int seconds);
    
    void startTimer(ITimer t);
    
    ITimerPanel getTimerPanel();
    
}
