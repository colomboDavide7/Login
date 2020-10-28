/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author davidecolombo
 */
public interface ILoginPanel {
    
    JButton getLoginButton();
    
    String getUsernameField();
    
    char[] getPasswordField();
    
    JTextArea getErrorArea();
    
}
