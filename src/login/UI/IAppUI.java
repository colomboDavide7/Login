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
    
    JButton getSignupButton();
    
    JButton getLogoutButton();
    
    ILoginPanel getLoginPanel();
    
}