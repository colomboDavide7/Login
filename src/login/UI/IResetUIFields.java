/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.util.List;
import login.system.UserProperty;

/**
 *
 * @author davidecolombo
 */
public interface IResetUIFields {
    
    void highlightMissing(List<UserProperty> missing);
    
    void clearForm();
    
}
