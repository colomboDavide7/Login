/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

import login.system.UserProperty;

/**
 *
 * @author davidecolombo
 */
public interface IUser extends IComparable, IUserDate {
    
    IUser addProperty(UserProperty key, String value);
    
    String getProperty(UserProperty key);

    String createRecord();
    
}
