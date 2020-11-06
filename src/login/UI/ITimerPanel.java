/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import javax.swing.JButton;

/**
 *
 * @author davidecolombo
 */
public interface ITimerPanel {
    
    JButton getTimerExpiredButton();
    
    void setStartTime(int hours, int minutes, int seconds);
    
    void setHours(int hours);
    
    void setMinutes(int minutes);
    
    void setSeconds(int seconds);
    
    void timerExpired();
    
}
