/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import login.tools.UserProperty;

/**
 *
 * @author davidecolombo
 */
public interface ISignupPanel {
    
    JButton getSignupButton();
    
    JButton getClearButton();
    
    JTextArea getErrorCommunicationField();
    
    JTextComponent getPropertyField(UserProperty p);
            
    Map<UserProperty, String> getInsertedProperties();
    
    void highlightMissing(List<UserProperty> missing);
    
    void clearForm();
    
}
