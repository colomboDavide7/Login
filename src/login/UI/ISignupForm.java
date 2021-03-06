/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.util.List;
import java.util.Map;
import javax.swing.text.JTextComponent;
import login.system.UserProperty;

/**
 *
 * @author davidecolombo
 */
public interface ISignupForm extends IResetUIFields {
    
    JTextComponent getPropertyField(UserProperty p);
    
    Map<UserProperty, String> getInsertedProperties();
    
    @Override
    void highlightMissing(List<UserProperty> missing);
    
    @Override
    void clearForm();
    
}
