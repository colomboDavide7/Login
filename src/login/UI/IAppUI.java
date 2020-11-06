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
public interface IAppUI {
    
    JButton getLogoutButton();
    
    ILoginPanel getLoginPanel();
    
    ITitlePanel getTitlePanel();
    
    ISignupPanel getSignupPanel();
    
    void appendTimerPanel(ITimerPanel p);
    
    void appendLoginPanel();
    
    void clearLoginForm();
    
}
