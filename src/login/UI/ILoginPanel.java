/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JTextArea;
import login.system.UserProperty;

/**
 *
 * @author davidecolombo
 */
public interface ILoginPanel extends IResetUIFields {
    
    JButton getLoginButton();
    
    String getUsernameField();
    
    String getPasswordField();
    
    JTextArea getErrorArea();
    
    @Override
    void highlightMissing(List<UserProperty> missing);
    
    @Override
    void clearForm();
    
}
