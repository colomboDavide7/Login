/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controllers;

import login.UI.ITimerPanel;

/**
 *
 * @author davidecolombo
 */
public interface UIControllerInterface {
    
    void appendLoginPanel();
    
    void appendTimerPanel(ITimerPanel p);
    
    void setTimerController(TimerControllerInterface c);
    
}
