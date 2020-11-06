/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controllers;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import login.UI.ITimerPanel;
import login.system.ITimer;

/**
 *
 * @author davidecolombo
 */
public class TimerController implements TimerControllerInterface {
    
    private ITimerPanel timerPanel;
    private UIControllerInterface uiControl;
    
    public TimerController(ITimerPanel p, UIControllerInterface c){
        this.timerPanel = p;
        this.uiControl = c;
        initialize();
    }
    
    private void initialize(){
        this.addButtonAction();
    }
    
    private void addButtonAction(){
        JButton timerExpired = this.timerPanel.getTimerExpiredButton();
        timerExpired.addActionListener((ActionEvent e) -> {
            uiControl.appendLoginPanel();
            timerExpired.setEnabled(false);
        });
    }
    
    @Override
    public void startTimer(ITimer t) {
        this.timerPanel.setStartTime(
                t.getStartHours(), 
                t.getStartMinutes(), 
                t.getStartSeconds());
        t.setTimerController(this);
        t.startCountdown();
    }

    @Override
    public ITimerPanel getTimerPanel() {
        return this.timerPanel;
    }

    @Override
    public void setTime(int hours, int minutes, int seconds) {
        this.timerPanel.setHours(hours);
        this.timerPanel.setMinutes(minutes);
        this.timerPanel.setSeconds(seconds);
    }
    
    @Override
    public void timerExpired(){
        this.timerPanel.timerExpired();
    }
    
}
